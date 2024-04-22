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
    private final Map<Integer,Task> tasks;
    public final HistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager(HistoryManager inMemoryHistoryManager){
        numberOfIdTask = 0;
        this.tasks = new HashMap<>();
        this.inMemoryHistoryManager = inMemoryHistoryManager;
    }

    private void incrementId(){
        numberOfIdTask++;
    }

    @Override
    public ArrayList<Task> getHistory(){
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public void remove(int id){
        inMemoryHistoryManager.remove(id);
    }

    @Override
    public void addHistoryId(Task task)  {
        inMemoryHistoryManager.addHistoryId(task);
    }

    @Override
    public void createNewSingleTask(SingleTask singleTask){
        incrementId();
        singleTask.setIdTask(numberOfIdTask);
        saveNewTask(singleTask);
    }

    @Override
    public void creatNewEpicTask(EpicTask epicTask){
        incrementId();
        epicTask.setIdTask(numberOfIdTask);
        saveNewTask(epicTask);
    }

    @Override
    public void createNewSubTask(SubTask subTask){
        incrementId();
        subTask.setIdTask(numberOfIdTask);
        saveNewTask(subTask);
        isEpicDone(subTask.getEpicTask());
    }

    @Override
    public void saveNewTask(Task task){
        tasks.put(numberOfIdTask,task);
    }

    @Override
    public void isEpicDone(EpicTask epicTask){
        int statusDone = 0;
        int statusInProgress = 0;
        if (epicTask.subTaskList.isEmpty()){
            epicTask.setStatus(Status.NEW);
        } else {
            for (SubTask subTask : epicTask.subTaskList) {
                if (subTask.getStatus() == Status.DONE) {
                    statusDone++;
                } else if (subTask.getStatus() == Status.IN_PROGRESS) {
                    statusInProgress++;
                }
            }
            if (statusDone == 0 && statusInProgress == 0) {
                epicTask.setStatus(Status.NEW);
                tasks.put(epicTask.getIdTask(), epicTask);
            } else if (statusDone == epicTask.subTaskList.size()) {
                epicTask.setStatus(Status.DONE);
                tasks.put(epicTask.getIdTask(), epicTask);
            } else if (statusDone > 0 || statusInProgress > 0) {
                epicTask.setStatus(Status.IN_PROGRESS);
                tasks.put(epicTask.getIdTask(), epicTask);
            }
        }
    }

    @Override
    public void updateSingleTask(SingleTask singleTask,String name,String description, Status status){
        SingleTask newSingleTask = singleTask.update(name,description,status);
        newSingleTask.setIdTask(singleTask.getIdTask());
        tasks.put(newSingleTask.getIdTask(), newSingleTask);
    }

    @Override
    public void updateSubTask(SubTask subTask, String name,String description, Status status){
            SubTask newSubTask = subTask.update(name,description,subTask.getEpicTask(),status);
            newSubTask.setIdTask(subTask.getIdTask());
            tasks.put(newSubTask.getIdTask(),newSubTask);
            newSubTask.getEpicTask().subTaskList.remove(subTask);
            newSubTask.getEpicTask().subTaskList.add(newSubTask);
            isEpicDone(newSubTask.getEpicTask());
    }

    @Override
    public void updateEpicTask(EpicTask epicTask, String name, String description){
        EpicTask newEpicTask = epicTask.update(name,description,epicTask.getSubTaskList());
        newEpicTask.setIdTask(epicTask.getIdTask());
        for (SubTask subTask : epicTask.subTaskList) {
            subTask.setEpicTask(newEpicTask);
        }
        tasks.put(newEpicTask.getIdTask(),newEpicTask);
    }

    @Override
    public ArrayList<Task> getAllTask(){
        ArrayList<Task> returnTasks = new ArrayList<>();
        if(!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                returnTasks.add(task);
            }
        } else {
            return new ArrayList<>();
        }
        return new ArrayList<>(returnTasks);
    }

    @Override
    public ArrayList<Task> getAllTaskByType(TypeOfTask typeOfTask){
        ArrayList<Task> returnTasksByType = new ArrayList<>();
        if(!tasks.isEmpty()) {
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
    public void deleteAllTaskByType(TypeOfTask typeOfTask){
        ArrayList<Task> arrayListToDelete = new ArrayList<>();
        if(typeOfTask == TypeOfTask.SINGLE_TASK){
            for (Task task: tasks.values()){
                if(task.getType() == TypeOfTask.SINGLE_TASK){
                    arrayListToDelete.add(task);
                }
            }
            for (Task task: arrayListToDelete) {
                deleteTaskById(task.getIdTask());
            }
        } else if (typeOfTask == TypeOfTask.SUB_TASK){
            for (Task task: tasks.values()){
                if(task.getType() == TypeOfTask.SUB_TASK){
                    arrayListToDelete.add(task);
                }
            }
            for (Task task: arrayListToDelete) {
                SubTask subTask = (SubTask) tasks.get(task.getIdTask());
                EpicTask epicTask = subTask.getEpicTask();
                deleteTaskById(task.getIdTask());
                isEpicDone(epicTask);
            }
        } else if (typeOfTask == TypeOfTask.EPIC_TASK) {
            for (Task task : tasks.values()) {
                if (task.getType() == TypeOfTask.EPIC_TASK) {
                    arrayListToDelete.add(task);
                }
            }
            for (Task task : arrayListToDelete) {
                deleteTaskById(task.getIdTask());
            }
        }

    }

    @Override
    public void deleteTaskById(Integer id){
        if (!tasks.containsKey(id)) {
            System.out.println("Задача не найдена");
        } else if (tasks.get(id).getType() == TypeOfTask.EPIC_TASK){
            EpicTask epicTask = (EpicTask) tasks.get(id);
            remove(id);
            for (SubTask subTask: epicTask.subTaskList ) {
                tasks.remove(subTask.getIdTask());
                remove(subTask.getIdTask());
            }
            tasks.remove(id);
        } else if (tasks.get(id).getType() == TypeOfTask.SUB_TASK){
            SubTask subTask = (SubTask) tasks.get(id);
            EpicTask epicTask = subTask.getEpicTask();
            tasks.remove(id);
            remove(id);
            epicTask.subTaskList.remove(subTask);
            isEpicDone(epicTask);
        } else {
            remove(id);
            tasks.remove(id);
        }
    }

    @Override
    public Task findTaskById(Integer id){
        if (tasks.containsKey(id)) {
            inMemoryHistoryManager.addHistoryId(tasks.get(id));
            return tasks.get(id);
        } else {
            return null;
        }
    }
}
