package manager.impl.utils;

import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SingleTask;
import manager.impl.tasks.SubTask;
import manager.interfaces.HistoryManager;
import manager.interfaces.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {

    private int numberOfIdTask;
    protected final Map<Integer,Task> tasks;
    protected final HistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager(HistoryManager inMemoryHistoryManager) {
        numberOfIdTask = 0;
        this.tasks = new HashMap<>();
        this.inMemoryHistoryManager = inMemoryHistoryManager;
    }

    private void incrementId() {
        numberOfIdTask++;
    }

    protected void setIDTask(Task task, int id) {
        task.setIdTask(id);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public void remove(int id) {
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public void addHistoryId(Task task)  {
        inMemoryHistoryManager.add(task);
    }

    @Override
    public void createNewSingleTask(SingleTask singleTask) {
        incrementId();
        singleTask.setIdTask(numberOfIdTask);
        saveNewTask(singleTask);
    }

    @Override
    public void creatNewEpicTask(EpicTask epicTask) {
        incrementId();
        epicTask.setIdTask(numberOfIdTask);
        saveNewTask(epicTask);
    }

    @Override
    public void createNewSubTask(SubTask subTask) {
        incrementId();
        subTask.setIdTask(numberOfIdTask);
        saveNewTask(subTask);
        isEpicDone(subTask.getEpicTask());
    }

    @Override
    public void saveNewTask(Task task) {
        tasks.put(numberOfIdTask,task);
    }

    @Override
    public void isEpicDone(EpicTask epicTask) {
        int statusDone = 0;
        int statusInProgress = 0;
        if (epicTask.getSubTaskList().isEmpty()) {
            epicTask.setStatus(Status.NEW);
        } else {
            for (SubTask subTask : epicTask.getSubTaskList()) {
                if (subTask.getStatus() == Status.DONE) {
                    statusDone++;
                } else if (subTask.getStatus() == Status.IN_PROGRESS) {
                    statusInProgress++;
                }
            }
            if (statusDone == 0 && statusInProgress == 0) {
                epicTask.setStatus(Status.NEW);
                tasks.put(epicTask.getIdTask(), epicTask);
            } else if (statusDone == epicTask.getSubTaskList().size()) {
                epicTask.setStatus(Status.DONE);
                tasks.put(epicTask.getIdTask(), epicTask);
            } else if (statusDone > 0 || statusInProgress > 0) {
                epicTask.setStatus(Status.IN_PROGRESS);
                tasks.put(epicTask.getIdTask(), epicTask);
            }
        }
    }

    @Override
    public void updateSingleTask(SingleTask singleTask,String name,String description, Status status) {
        SingleTask newSingleTask = singleTask.update(name,description,status);
        newSingleTask.setIdTask(singleTask.getIdTask());
        tasks.put(newSingleTask.getIdTask(), newSingleTask);
    }

    @Override
    public void updateSubTask(SubTask subTask, String name,String description, Status status) {
            SubTask newSubTask = subTask.update(name,description,subTask.getEpicTask(),status);
            newSubTask.setIdTask(subTask.getIdTask());
            tasks.put(newSubTask.getIdTask(),newSubTask);
            newSubTask.getEpicTask().removeSubTask(subTask);
            newSubTask.getEpicTask().addSubTask(newSubTask);
            isEpicDone(newSubTask.getEpicTask());
    }

    @Override
    public void updateEpicTask(EpicTask epicTask, String name, String description) {
        EpicTask newEpicTask = epicTask.update(name,description,epicTask.getSubTaskList());
        newEpicTask.setIdTask(epicTask.getIdTask());
        for (SubTask subTask : epicTask.getSubTaskList()) {
            subTask.setEpicTask(newEpicTask);
        }
        tasks.put(newEpicTask.getIdTask(),newEpicTask);
    }

    @Override
    public ArrayList<Task> getAllTask() {
        ArrayList<Task> returnTasks;
        if (!tasks.isEmpty()) {
            returnTasks = new ArrayList<>(tasks.values());
        } else {
            return new ArrayList<>();
        }
        return new ArrayList<>(returnTasks);
    }

    @Override
    public ArrayList<Task> getAllTaskByType(TypeOfTask typeOfTask) {
        ArrayList<Task> returnTasksByType = new ArrayList<>();
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                if (task.getType() == typeOfTask) {
                    returnTasksByType.add(task);
                }
            }
        } else {
            return new ArrayList<>();
        }
        return new ArrayList<>(returnTasksByType);
    }

    @Override
    public void deleteAllTaskByType(TypeOfTask typeOfTask) {
        ArrayList<Task> arrayListToDelete = new ArrayList<>();
        if (typeOfTask == TypeOfTask.TASK) {
            for (Task task: tasks.values()) {
                if (task.getType() == TypeOfTask.TASK) {
                    arrayListToDelete.add(task);
                }
            }
            for (Task task: arrayListToDelete) {
                deleteTaskById(task.getIdTask());
            }
        } else if (typeOfTask == TypeOfTask.SUBTASK) {
            for (Task task: tasks.values()) {
                if (task.getType() == TypeOfTask.SUBTASK) {
                    arrayListToDelete.add(task);
                }
            }
            for (Task task: arrayListToDelete) {
                SubTask subTask = (SubTask) tasks.get(task.getIdTask());
                EpicTask epicTask = subTask.getEpicTask();
                deleteTaskById(task.getIdTask());
                isEpicDone(epicTask);
            }
        } else if (typeOfTask == TypeOfTask.EPIC) {
            for (Task task : tasks.values()) {
                if (task.getType() == TypeOfTask.EPIC) {
                    arrayListToDelete.add(task);
                }
            }
            for (Task task : arrayListToDelete) {
                deleteTaskById(task.getIdTask());
            }
        }

    }

    @Override
    public void deleteTaskById(Integer id) {
        if (!tasks.containsKey(id)) {
            System.out.println("Задача не найдена");
        } else if (tasks.get(id).getType() == TypeOfTask.EPIC) {
            EpicTask epicTask = (EpicTask) tasks.get(id);
            remove(id);
            for (SubTask subTask: epicTask.getSubTaskList()) {
                tasks.remove(subTask.getIdTask());
                remove(subTask.getIdTask());
            }
            tasks.remove(id);
        } else if (tasks.get(id).getType() == TypeOfTask.SUBTASK) {
            SubTask subTask = (SubTask) tasks.get(id);
            EpicTask epicTask = subTask.getEpicTask();
            tasks.remove(id);
            remove(id);
            epicTask.removeSubTask(subTask);
            isEpicDone(epicTask);
        } else {
            remove(id);
            tasks.remove(id);
        }
    }

    @Override
    public Task findTaskById(Integer id) {
        if (tasks.containsKey(id)) {
            inMemoryHistoryManager.add(tasks.get(id));
            return tasks.get(id);
        } else {
            return null;
        }
    }
}
