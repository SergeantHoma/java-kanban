package manager.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.abstractClass.Task;
import manager.impl.enums.Endpoint;
import manager.impl.enums.TypeOfTask;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SubTask;
import manager.interfaces.TaskManager;
import manager.server.TaskGson;

import java.io.IOException;
import java.util.regex.Pattern;

public class SubtasksHandler extends BaseHandler implements HttpHandler {

    private TaskManager manager;

    public SubtasksHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_SUBTASKS -> handleGetSubtasks(exchange);
            case GET_SUBTASK -> handleGetSubtask(exchange);
            case POST_SUBTASK -> handlePostSubtask(exchange);
            case DELETE_SUBTASK -> handleDeleteSubtask(exchange);
            default -> writeResponse(exchange, "Такого эндпоинта не существует", 404);
        }
    }

    public Endpoint getEndpoint(String path, String method) {
        try {
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/subtasks/\\d+$", path)) {
                        return Endpoint.GET_SUBTASK;
                    } else if (Pattern.matches("^/subtasks$", path)) {
                        return Endpoint.GET_SUBTASKS;
                    } else {
                        return Endpoint.UNKNOWN;
                    }
                }
                case "POST": {
                    if (Pattern.matches("^/subtasks$", path)) {
                        return Endpoint.POST_SUBTASK;
                    }
                }
                case "DELETE": {
                    if (Pattern.matches("^/subtasks/\\d+$", path)) {
                        return Endpoint.DELETE_SUBTASK;
                    }
                }
                default: {
                    return Endpoint.UNKNOWN;
                }
            }
        } catch (Exception e) {
            return Endpoint.UNKNOWN;
        }
    }

    private void handleGetSubtasks(HttpExchange exchange) throws IOException {
        writeResponse(exchange, TaskGson.GSON.toJson(manager.getAllTaskByType(TypeOfTask.SUBTASK)), 200);
    }

    private void handleGetSubtask(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] urlPath = url.split("/");

        int id = parsePathId(urlPath[2]);

        if (manager.findTaskById(id) == null) {
            writeResponse(exchange, "Запрашиваемая подзадача не существует", 404);
        } else if (id == -1) {
            writeResponse(exchange, "Некорректный номер запрашиваемой подзадачи", 404);
        } else {
            Task subtask =  manager.findTaskById(id);
            writeResponse(exchange, TaskGson.GSON.toJson(subtask), 200);
        }
    }

    private void handlePostSubtask(HttpExchange exchange) throws IOException {
        String body = readBody(exchange);
        SubTask subtask;

        subtask = TaskGson.GSON.fromJson(body, SubTask.class);

        if (manager.findTaskById(subtask.getIdTask()) == null) {
            manager.createNewSubTask(subtask);
            writeResponse(exchange, "Подзадача создана", 201);
        } else {
            subtask.setEpicTask((EpicTask) manager.findTaskById(subtask.getIdForSerialization()));

            manager.updateSubTask(subtask,subtask.getName(),subtask.getDescription(),subtask.getStatus());
            writeResponse(exchange, "Подзадача обновлена", 201);
        }
    }

    private void handleDeleteSubtask(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] urlPath = url.split("/");

        int id = parsePathId(urlPath[2]);

        if (manager.findTaskById(id) == null) {
            writeResponse(exchange, "Удаляемая подзадача не существует", 404);
        } else if (id == -1) {
            writeResponse(exchange, "Некорректный номер удаляемой подзадачи", 404);
        } else {
            manager.deleteTaskById(id);
            writeResponse(exchange, "Подзадача " + id + " удалена", 200);
        }
    }
}
