package manager.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.impl.enums.Endpoint;
import manager.impl.enums.TypeOfTask;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SubTask;
import manager.interfaces.TaskManager;
import manager.server.TaskGson;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

public class EpicsHandler extends BaseHandler implements HttpHandler {

    private TaskManager manager;

    public EpicsHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Endpoint endpoint = getEndpoint(exchange.getRequestURI().getPath(), exchange.getRequestMethod());

        switch (endpoint) {
            case GET_EPICS -> handleGetEpics(exchange);
            case GET_EPIC -> handleGetEpic(exchange);
            case GET_EPICS_SUBTASK -> handleGetSubtaskFromEpic(exchange);
            case POST_EPIC -> handlePostEpic(exchange);
            case DELETE_EPIC -> handleDeleteEpic(exchange);
            default -> writeResponse(exchange, "Такого эндпоинта нет", 404);
        }
    }

    public Endpoint getEndpoint(String path, String method) {
        try {
            switch (method) {
                case "GET": {
                    if (Pattern.matches("^/epics/\\d+$", path)) {
                        return Endpoint.GET_EPIC;
                    } else if (Pattern.matches("^/epics$", path)) {
                        return Endpoint.GET_EPICS;
                    } else if (Pattern.matches("^/epics/\\d+/subtasks$", path)) {
                        return Endpoint.GET_EPICS_SUBTASK;
                    } else {
                        return Endpoint.UNKNOWN;
                    }
                }
                case "POST": {
                    if (Pattern.matches("^/epics$", path)) {
                        return Endpoint.POST_EPIC;
                    }
                }
                case "DELETE": {
                    if (Pattern.matches("^/epics/\\d+$", path)) {
                        return Endpoint.DELETE_EPIC;
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

    private void handleGetEpics(HttpExchange exchange) throws IOException {
        writeResponse(exchange, TaskGson.GSON.toJson(manager.getAllTaskByType(TypeOfTask.EPIC)), 200);
    }

    private void handleGetEpic(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] urlPath = url.split("/");

        int id = parsePathId(urlPath[2]);

        if (manager.findTaskById(id) == null) {
            writeResponse(exchange, "Запрашиваемый эпик не существует", 404);
        } else if (id == -1) {
            writeResponse(exchange, "Некорректный номер запрашиваемого эпика", 404);
        } else {
            EpicTask epic = (EpicTask) manager.findTaskById(id);
            writeResponse(exchange, TaskGson.GSON.toJson(epic), 200);
        }
    }

    private void handlePostEpic(HttpExchange exchange) throws IOException {
        String body = readBody(exchange);
        EpicTask epic;

        epic = TaskGson.GSON.fromJson(body, EpicTask.class);

        manager.creatNewEpicTask(epic);
        writeResponse(exchange, "Эпик создан", 201);
    }

    private void handleDeleteEpic(HttpExchange exchange) throws IOException {
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

    private void handleGetSubtaskFromEpic(HttpExchange exchange) throws IOException {
        String url = exchange.getRequestURI().toString();
        String[] urlPath = url.split("/");

        int id = parsePathId(urlPath[2]);

        if (manager.findTaskById(id) == null) {
            writeResponse(exchange, "Запрашиваемый эпик не существует", 404);
        } else if (id == -1) {
            writeResponse(exchange, "Некорректный номер запрашиваемого эпика", 404);
        } else {
            EpicTask epicTask = (EpicTask) manager.findTaskById(id);
            Collection<SubTask> subtasksFromEpic =  epicTask.getSubTaskList();
            writeResponse(exchange, TaskGson.GSON.toJson(subtasksFromEpic), 200);
        }
    }
}
