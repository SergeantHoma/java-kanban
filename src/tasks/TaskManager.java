package tasks;

import abstractClass.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private final HashMap<Integer, Task> tasks;

    public TaskManager() {
        this.tasks = new HashMap<>();
    }

    public SingleTask createNewSingleTask(String name, String description) {
        SingleTask singleTask = new SingleTask(name, description);
        saveNewTask(singleTask);
        return singleTask;
    }


    public SubTask createNewSubTask(String name, String description, EpicTask epicTask) {
        SubTask subTask = new SubTask(name, description, epicTask);
        saveNewTask(subTask);
        isEpicDone(epicTask);
        return subTask;
    }

    public EpicTask creatNewEpicTask(String name, String description) {
        EpicTask epicTask = new EpicTask(name, description);
        saveNewTask(epicTask);
        return epicTask;
    }

    public void saveNewTask(Task task) {
        tasks.put(task.getIdTask(), task);
    }

    public void isEpicDone(EpicTask epicTask) {
        int statusDone = 0;
        int statusInProgress = 0;
        if (epicTask.subTaskList.isEmpty()) {
            epicTask.setStatus(Status.NEW);
        } else {
            for (Integer i : epicTask.subTaskList) {
                if (tasks.get(i).getStatus() == Status.DONE) {
                    statusDone++;
                } else if (tasks.get(i).getStatus() == Status.IN_PROGRESS) {
                    statusInProgress++;
                }
            }
            if (statusDone == 0 && statusInProgress == 0) {
                epicTask = epicTask.setStatus(Status.NEW);
                tasks.replace(epicTask.getIdTask(), epicTask);
            } else if (statusDone == epicTask.subTaskList.size()) {
                epicTask = epicTask.setStatus(Status.DONE);
                tasks.replace(epicTask.getIdTask(), epicTask);
            } else if (statusDone > 0 || statusInProgress > 0) {
                epicTask = epicTask.setStatus(Status.IN_PROGRESS);
                tasks.replace(epicTask.getIdTask(), epicTask);
            }
        }
    }


    public void updateSingleTask(Task task, String name, String description, Status status) {
        task = ((SingleTask) task).update(name, description, status);
        tasks.replace(task.getIdTask(), task);
    }


    public void updateSubTask(Task task, String name, String description, EpicTask epicTask, Status status) {
        if (((SubTask) task).getEpicTask().equals(epicTask)) {
            task = ((SubTask) task).update(name, description, epicTask, status);
            tasks.replace(task.getIdTask(), task);
            isEpicDone(((SubTask) task).getEpicTask());
        } else {
            Integer numberOfIndexTask = -1;
            for (Integer i : ((SubTask) task).getEpicTask().subTaskList) {
                if (i == task.getIdTask()) {
                    numberOfIndexTask = i;
                }
            }
            ((SubTask) task).getEpicTask().subTaskList.remove(numberOfIndexTask);
            isEpicDone(((SubTask) task).getEpicTask());
            task = ((SubTask) task).update(name, description, epicTask, status);
            epicTask.subTaskList.add(task.getIdTask());
            isEpicDone(epicTask);
            tasks.replace(task.getIdTask(), task);
        }
    }

    public void updateEpicTask(Task task, String name, String description) {
        task = ((EpicTask) task).update(name, description);
        tasks.replace(task.getIdTask(), task);
        isEpicDone((EpicTask) task);
    }

    public ArrayList<Task> getAllTask() {
        ArrayList<Task> returnTasks = new ArrayList<>();
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                returnTasks.add(task);
            }
        } else {
            System.out.println("Список пуст.");
        }
        return returnTasks;
    }

    public ArrayList<Task> getAllTaskByType(TypeOfTask typeOfTask) {
        ArrayList<Task> returnTasksByType = new ArrayList<>();
        if (!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                if (task.getType() == typeOfTask) {
                    returnTasksByType.add(task);
                }
            }
        } else{
                System.out.println("Список пуст.");
            }
            return returnTasksByType;
        }



        public void deleteAllTask(){
            tasks.clear();
            System.out.println("Список дел отчищен.");
        }

        public void deleteTaskById (Integer id){
            if (!tasks.containsKey(id)) {
                System.out.println("Задача не найдена");
            } else if (tasks.get(id).getType() == TypeOfTask.EPIC_TASK) {
                EpicTask task = (EpicTask) tasks.get(id);
                for (Integer integer : task.subTaskList) {
                    tasks.remove(integer);
                }
                tasks.remove(id);
            } else if (tasks.get(id).getType() == TypeOfTask.SUB_TASK) {
                SubTask task = (SubTask) tasks.get(id);
                EpicTask epicTask = task.getEpicTask();
                tasks.remove(id);
                epicTask.subTaskList.remove(id);
                isEpicDone(epicTask);
            } else {
                tasks.remove(id);
            }
        }

        public Task findTaskById (Integer id){
            if (tasks.containsKey(id)) {
                System.out.println("Задача: " + id);
                return tasks.get(id);
            } else {
                System.out.println("Задача не найдена");
                return null;
            }
        }
    }

