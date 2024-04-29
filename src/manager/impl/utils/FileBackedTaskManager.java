package manager.impl.utils;

import exceptions.ManagerSaveException;
import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;
import manager.impl.tasks.*;
import manager.interfaces.HistoryManager;
import manager.interfaces.TaskManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;
    private static final String MASSAGE_OF_EMPTY_HISTORY = "История пуста";

    public FileBackedTaskManager(HistoryManager inMemoryHistoryManager, File file) {
        super(inMemoryHistoryManager);
        this.file = file;
    }

    private void save() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))) {
            List<Task> dataToSave = new ArrayList<>(getAllTask());
            bufferedWriter.write(createDataToSave(dataToSave, getHistory()));
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка сохранения файла,\n" + e.getMessage());
        }
    }

    public static TaskManager loadFromFile(File file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(new InMemoryHistoryManager(), file);
        try {
            String dataFromFile = Files.readString(file.toPath());
            String[] splitData = dataFromFile.split("\n");

            for (int i = 1; i < splitData.length - 2; i++) {
                Task task = fromString(splitData[i], fileBackedTaskManager);
                if (task instanceof EpicTask) {
                    fileBackedTaskManager.creatNewEpicTask((EpicTask) task);
                } else if (task instanceof SubTask) {
                    fileBackedTaskManager.createNewSubTask((SubTask) task);
                } else {
                    fileBackedTaskManager.createNewSingleTask((SingleTask) task);
                }
            }
            List<Integer> history = historyFromString(splitData[splitData.length - 1]);
            if (history.isEmpty()) {
                return fileBackedTaskManager;
            }
            for (int id : history) {
                if (fileBackedTaskManager.inMemoryHistoryManager != null) {
                    fileBackedTaskManager.inMemoryHistoryManager.add(fileBackedTaskManager.findTaskById(id));
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка загрузки файла\n");
        }
        return fileBackedTaskManager;
    }

    public static Task fromString(String value, FileBackedTaskManager fileBackedTaskManager) {
        String[] values = value.split(",");
        int id = Integer.parseInt(values[0]);
        String type = values[1];
        String name = values[2];
        String description = values[3];
        Status status = Status.valueOf(values[4]);
        String epicId = null;
        Duration duration = Duration.ZERO;
        if (values.length == 6) {
            epicId = values[5];
        }
        if (!values[5].equals("")) {
            duration = Duration.ofMinutes(Integer.parseInt(values[5]));
        }
        LocalDateTime startTime = null;
        if (!values[6].equals("0")) {
            startTime = LocalDateTime.parse(values[6]);
        }
        switch (type) {
            case "SUBTASK": {
                SubTask subtask = new SubTask(name, description, (EpicTask) fileBackedTaskManager.findTaskById(Integer.parseInt(epicId)));
                subtask.setIdTask(id);
                subtask.setStatus(status);
                subtask.setDuration(duration.toMinutesPart());
                subtask.setStartTime(startTime.toString());
                return subtask;
            }
            case "EPIC": {
                EpicTask epicTask = new EpicTask(name, description);
                epicTask.setIdTask(id);
                epicTask.setStatus(status);
                epicTask.setDuration(duration.toMinutesPart());
                epicTask.setStartTime(startTime.toString());
                return epicTask;
            }
            case "TASK": {
                SingleTask singleTask = new SingleTask(name, description);
                singleTask.setIdTask(id);
                singleTask.setStatus(status);
                singleTask.setDuration(duration.toMinutesPart());
                singleTask.setStartTime(String.valueOf(startTime));
                return singleTask;
            }
            default: {
                throw new RuntimeException("Неправильный формат записи");
            }
        }
    }

    public static String createDataToSave(List<Task> tasks, List<Task> history) {
        StringBuilder dataToSave = new StringBuilder();
        dataToSave.append("id,type,name,description,status,duration,startTime,epicId\n");

        for (Task task : tasks) {
            dataToSave.append(task.toString()).append("\n");
        }

        dataToSave.append("\n");
        if (history.isEmpty())
            dataToSave.append(MASSAGE_OF_EMPTY_HISTORY);
        else {
            dataToSave.append(historyToString(history));
        }
        return dataToSave.toString();
    }

    private static String historyToString(List<Task> history) {
        StringBuilder historyDataToSave = new StringBuilder();
        for (Task task : history) {
            historyDataToSave.append(task.getIdTask()).append(",");
        }
        return historyDataToSave.toString();
    }

    static List<Integer> historyFromString(String value) {
        String regex = "^\\d+(,\\d+)*,$";
        List<Integer> data = new ArrayList<>();
        if (value.equals(MASSAGE_OF_EMPTY_HISTORY) || value.isBlank()) {
            return data;
        }
        if (!value.matches(regex)) {
            return data;
        }
        for (String id : value.split(",")) {
            data.add(Integer.parseInt(id));
        }
        return data;
    }

    @Override
    public void createNewSingleTask(SingleTask task) {
        super.createNewSingleTask(task);
        save();
    }

    @Override
    public void creatNewEpicTask(EpicTask epic) {
        super.creatNewEpicTask(epic);
        save();
    }

    @Override
    public void createNewSubTask(SubTask subtask) {
        super.createNewSubTask(subtask);
        save();
    }

    @Override
    public void saveNewTask(Task task) {
        super.saveNewTask(task);
        save();
    }

    @Override
    public void isEpicDone(EpicTask epicTask) {
        super.isEpicDone(epicTask);
        save();
    }

    @Override
    public void updateSingleTask(SingleTask singleTask, String name, String description, Status status) {
        super.updateSingleTask(singleTask,name,description,status);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask, String name,String description, Status status) {
        super.updateSubTask(subTask,name,description,status);
        save();
    }

    @Override
    public void updateEpicTask(EpicTask epicTask, String name, String description) {
        super.updateEpicTask(epicTask,name,description);
        save();
    }

    @Override
    public void deleteAllTaskByType(TypeOfTask typeOfTask) {
        super.deleteAllTaskByType(typeOfTask);
        save();
    }

    @Override
    public void deleteTaskById(Integer id) {
        super.deleteTaskById(id);
        save();
    }

    @Override
    public Task findTaskById(Integer id) {
        Task result = super.findTaskById(id);
        save();
        return result;
    }
}
