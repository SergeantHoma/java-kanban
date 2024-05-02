import com.google.gson.*;
import manager.abstractClass.Managers;
import manager.abstractClass.Task;
import manager.impl.enums.TypeOfTask;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SingleTask;
import manager.impl.tasks.SubTask;
import manager.interfaces.TaskManager;
import manager.server.HttpTaskServer;
import manager.server.TaskGson;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {

    private TaskManager managers;
    private HttpTaskServer httpTaskServer;
    private HttpClient client;
    private HttpResponse.BodyHandler<String> handler;

    private SingleTask task1;
    private SingleTask task2;
    private EpicTask epic1;
    private EpicTask epic2;

    @BeforeEach
    public void startServer() throws IOException {
        managers = Managers.getDefault();
        client = HttpClient.newHttpClient();
        handler = HttpResponse.BodyHandlers.ofString();

        task1 = new SingleTask("Task1", "SingleTask1");
        task2 = new SingleTask("Task2", "SingleTask2");

        epic1 = new EpicTask("Epic1", "EpicTask1");
        epic2 = new EpicTask("Epic2", "EpicTask2");


        httpTaskServer = new HttpTaskServer(managers);
        httpTaskServer.start();
    }

    @AfterEach
    public void stopServer() {
        httpTaskServer.stop();
    }

    @Test
    void checkGETTasks() throws IOException, InterruptedException {
        managers.createNewSingleTask(task1);
        managers.createNewSingleTask(task2);

        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        assertTrue(jsonElement.isJsonArray());

        JsonArray jsonObject = jsonElement.getAsJsonArray();
        Task[] tasksFromJson = TaskGson.GSON.fromJson(jsonObject, SingleTask[].class);

        assertEquals(200, response.statusCode());
        assertArrayEquals(new SingleTask[]{task1, task2}, tasksFromJson);
    }

    @Test
    void checkGetTaskById() throws IOException, InterruptedException {
        managers.createNewSingleTask(task1);
        int id = task1.getIdTask();

        URI url = URI.create("http://localhost:8080/tasks/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        assertTrue(jsonElement.isJsonObject());

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int idTask = jsonObject.get("idTask").getAsInt();
        String nameTask = jsonObject.get("name").getAsString();
        String descriptionTask = jsonObject.get("description").getAsString();
        String statusTask = jsonObject.get("status").getAsString();

        assertAll("Должен вернуть метоинформацию о задаче",
                () -> assertEquals(task1.getIdTask(), idTask),
                () -> assertEquals(task1.getName(), nameTask),
                () -> assertEquals(task1.getDescription(), descriptionTask),
                () -> assertEquals(task1.getStatus().toString(), statusTask)
        );
    }



    @Test
    void checkDELETETask() throws IOException, InterruptedException {
        managers.createNewSingleTask(task1);
        int id = task1.getIdTask();

        URI url = URI.create("http://localhost:8080/tasks/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals(managers.getAllTask().size(), 0);
    }

    @Test
    void checkGETSubtasks() throws IOException, InterruptedException {
        EpicTask epic3 = new EpicTask("Epic1", "EpicTask1");
        managers.creatNewEpicTask(epic3);
        SubTask subtask1 = new SubTask("Subtask1", "For Epic1",epic3);
        managers.createNewSubTask(subtask1);
        SubTask subtask2 = new SubTask("Subtask1", "For Epic1",epic3);
        managers.createNewSubTask(subtask2);

        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        assertTrue(jsonElement.isJsonArray());

        JsonArray jsonObject = jsonElement.getAsJsonArray();
        SubTask[] subtasksFromJson = TaskGson.GSON.fromJson(jsonObject, SubTask[].class);

        assertEquals(200, response.statusCode());
        assertArrayEquals(new SubTask[]{subtask1, subtask2}, subtasksFromJson);
    }

    @Test
    void checkGETEpics() throws IOException, InterruptedException {
        managers.creatNewEpicTask(epic1);
        managers.creatNewEpicTask(epic2);

        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        assertTrue(jsonElement.isJsonArray());

        JsonArray jsonObject = jsonElement.getAsJsonArray();
        EpicTask[] epicsFromJson = TaskGson.GSON.fromJson(jsonObject, EpicTask[].class);

        assertEquals(200, response.statusCode());
        assertArrayEquals(new EpicTask[]{epic1, epic2}, epicsFromJson);
    }
    @Test
    void checkGetSubtaskById() throws IOException, InterruptedException {
        managers.creatNewEpicTask(epic1);
        SubTask subtask1 = new SubTask("Subtask1", "For Epic1",epic1);
        managers.createNewSubTask(subtask1);

        int id = subtask1.getIdTask();

        URI url = URI.create("http://localhost:8080/subtasks/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        assertTrue(jsonElement.isJsonObject());

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int idTask = jsonObject.get("idTask").getAsInt();
        String nameTask = jsonObject.get("name").getAsString();
        String descriptionTask = jsonObject.get("description").getAsString();
        String typeTask = jsonObject.get("type").getAsString();
        String statusTask = jsonObject.get("status").getAsString();

        assertAll("Должен вернуть метоинформацию о подзадаче",
                () -> assertEquals(subtask1.getIdTask(), idTask),
                () -> assertEquals(subtask1.getName(), nameTask),
                () -> assertEquals(subtask1.getDescription(), descriptionTask),
                () -> assertEquals(subtask1.getType().toString(), typeTask),
                () -> assertEquals(subtask1.getStatus().toString(), statusTask)
        );
    }

    @Test
    void checkDELETESubtask() throws IOException, InterruptedException {
        managers.creatNewEpicTask(epic1);
        SubTask subtask1 = new SubTask("Subtask1", "For Epic1",epic1);
        managers.createNewSubTask(subtask1);

        int id = subtask1.getIdTask();

        URI url = URI.create("http://localhost:8080/subtasks/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals(managers.getAllTaskByType(TypeOfTask.SUBTASK).size(), 0);
    }



    @Test
    void checkGetEpicById() throws IOException, InterruptedException {
        managers.creatNewEpicTask(epic1);
        int id = epic1.getIdTask();

        URI url = URI.create("http://localhost:8080/epics/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        assertTrue(jsonElement.isJsonObject());

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        int idTask = jsonObject.get("idTask").getAsInt();
        String nameTask = jsonObject.get("name").getAsString();
        String descriptionTask = jsonObject.get("description").getAsString();
        String typeTask = jsonObject.get("type").getAsString();
        String statusTask = jsonObject.get("status").getAsString();

        assertAll("Должен вернуть метоинформацию об эпике",
                () -> assertEquals(epic1.getIdTask(), idTask),
                () -> assertEquals(epic1.getName(), nameTask),
                () -> assertEquals(epic1.getDescription(), descriptionTask),
                () -> assertEquals(epic1.getType().toString(), typeTask),
                () -> assertEquals(epic1.getStatus().toString(), statusTask)
        );
    }

    @Test
    void checkPOSTTask() throws IOException, InterruptedException {
        URI uri = URI.create("http://localhost:8080/tasks");
        String json = TaskGson.GSON.toJson(task1);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(body)
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals(managers.getAllTask().size(), 1);
    }

    @Test
    void checkPOSTEpic() throws IOException, InterruptedException {
        URI uri = URI.create("http://localhost:8080/epics");
        String json = TaskGson.GSON.toJson(epic1);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(body)
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals(managers.getAllTaskByType(TypeOfTask.EPIC).size(), 1);
    }

    @Test
    void checkPOSTSubtask() throws IOException, InterruptedException {
        managers.creatNewEpicTask(epic1);
        SubTask subtask1 = new SubTask("test", "test",epic1);
        managers.createNewSubTask(subtask1);
        URI uri = URI.create("http://localhost:8080/subtasks");
        String json = TaskGson.GSON.toJson(subtask1);
        final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(json);
        HttpRequest request = HttpRequest.newBuilder()
                .POST(body)
                .uri(uri)
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(201, response.statusCode());
        assertEquals(managers.getAllTaskByType(TypeOfTask.SUBTASK).size(), 1);
    }

    @Test
    void checkDELETEEpic() throws IOException, InterruptedException {
        managers.creatNewEpicTask(epic1);
        int id = epic1.getIdTask();

        URI url = URI.create("http://localhost:8080/epics/" + id);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());
        assertEquals(managers.getAllTaskByType(TypeOfTask.EPIC).size(), 0);
    }

    @Test
    void checkGETSubtaskFromEpic() throws IOException, InterruptedException {
        managers.creatNewEpicTask(epic1);
        SubTask subtask1 = new SubTask("Subtask1", "For Epic1",epic1);
        managers.createNewSubTask(subtask1);
        SubTask subtask2 = new SubTask("Subtask1", "For Epic1",epic1);
        managers.createNewSubTask(subtask2);

        URI url = URI.create("http://localhost:8080/epics/" + epic1.getIdTask() + "/subtasks");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        assertTrue(jsonElement.isJsonArray());

        JsonArray jsonArray = jsonElement.getAsJsonArray();
        SubTask[] loadedSubtasks = TaskGson.GSON.fromJson(jsonArray, SubTask[].class);
        assertArrayEquals(loadedSubtasks, new SubTask[]{subtask1, subtask2});
    }

    @Test
    void checkGETHistory() throws IOException, InterruptedException {
        managers.createNewSingleTask(task1);
        managers.createNewSingleTask(task2);
        managers.findTaskById(task1.getIdTask());
        managers.findTaskById(task2.getIdTask());

        URI url = URI.create("http://localhost:8080/history");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        JsonArray jsonArray = jsonElement.getAsJsonArray();

        assertEquals(2, jsonArray.size());
    }

    @Test
    void checkGETPrioritizedTasks() throws IOException, InterruptedException {
        task1.setStartTime("22.02.2023 22:00");
        task1.setDuration(30);
        task2.setStartTime("22.02.2023 23:00");
        task2.setDuration(30);
        managers.createNewSingleTask(task1);
        managers.createNewSingleTask(task2);

        URI url = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, handler);
        assertEquals(200, response.statusCode());

        JsonElement jsonElement = JsonParser.parseString(response.body());
        assertTrue(jsonElement.isJsonArray());

        assertEquals(2, jsonElement.getAsJsonArray().size());
    }
}