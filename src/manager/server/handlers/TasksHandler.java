package manager.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.abstractClass.Task;
import manager.impl.enums.Endpoint;
import manager.impl.enums.TypeOfTask;
import manager.impl.tasks.SingleTask;
import manager.interfaces.TaskManager;
import manager.server.TaskGson;

import java.io.IOException;
import java.util.regex.Pattern;


public class TasksHandler extends BaseHandler implements HttpHandler {

    private TaskManager manager;

    public TasksHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_TASKS -> handleGetTasks(exchange);
            case GET_TASK -> handleGetTask(exchange);
            case POST_TASK -> handlePostTask(exchange);
            case DELETE_TASK -> handleDeleteTasks(exchange);
            default -> writeResponse(exchange, "Такого эндпоинта нет", 404);
        }
    }

    public Endpoint getEndpoint(String path, String method) {
        try {
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/tasks/\\d+$", path)) {
                        return Endpoint.GET_TASK;
                    } else if (Pattern.matches("^/tasks$", path)) {
                        return Endpoint.GET_TASKS;
                    } else {
                        return Endpoint.UNKNOWN;
                    }
                }
                case "POST": {
                    if (Pattern.matches("^/tasks$", path)) {
                        return Endpoint.POST_TASK;
                    }
                }
                case "DELETE": {
                    if (Pattern.matches("^/tasks/\\d+$", path)) {
                        return Endpoint.DELETE_TASK;
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

    private void handleGetTasks(HttpExchange exchange) throws IOException {
        writeResponse(exchange, TaskGson.GSON.toJson(manager.getAllTaskByType(TypeOfTask.TASK)), 200);
    }

    private void handleGetTask(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] urlPath = url.split("/");

        int id = parsePathId(urlPath[2]);

        if (manager.findTaskById(id) == null) {
            writeResponse(exchange, "Запрашиваемая задача не существует", 404);
        } else if (id == -1) {
            writeResponse(exchange, "Некорректный номер запрашиваемой задачи", 404);
        } else {
            Task task = manager.findTaskById(id);
            writeResponse(exchange, TaskGson.GSON.toJson(task), 200);
        }
    }

    private void handleDeleteTasks(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] urlPath = url.split("/");

        int id = parsePathId(urlPath[2]);

        if (manager.findTaskById(id) == null) {
            writeResponse(exchange, "Удаляемая задача не существует", 404);
        } else if (id == -1) {
            writeResponse(exchange, "Некорректный номер удаляемой задачи", 404);
        } else {
            manager.deleteTaskById(id);
            writeResponse(exchange, "Задача " + id + " удалена", 200);
        }
    }

    private void handlePostTask(HttpExchange exchange) throws IOException {
        String body = readBody(exchange);
        SingleTask task;

        task = TaskGson.GSON.fromJson(body, SingleTask.class);

        if (manager.findTaskById(task.getIdTask()) == null) {
            manager.createNewSingleTask(task);
            writeResponse(exchange, "Задача создана", 201);
        } else {
            manager.updateSingleTask(task, task.getName(), task.getDescription(),task.getStatus());
            writeResponse(exchange, "Задача обновлена", 201);
        }
    }
}