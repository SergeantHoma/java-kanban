package manager.impl;

import manager.abstractClass.Task;
import manager.interfaces.HistoryManager;
import manager.interfaces.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private int numberOfIdTask;
    private final HashMap<Integer,Task> tasks;
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
        singleTask.update(name,description,status);
        tasks.put(singleTask.getIdTask(), singleTask);
    }

    @Override
    public void updateSubTask(SubTask subTask, String name,String description, EpicTask epicTask, Status status){
            subTask.update(name,description,epicTask,status);
            tasks.put(subTask.getIdTask(),subTask);
            isEpicDone(subTask.getEpicTask());
    }

    @Override
    public void updateEpicTask(EpicTask epicTask, String name, String description){
        epicTask.update(name,description);
        tasks.put(epicTask.getIdTask(),epicTask);
    }

    @Override
    public ArrayList<Task> getAllTask(){
        ArrayList<Task> returnTasks = new ArrayList<>();
        if(!tasks.isEmpty()) {
            for (Task task : tasks.values()) {
                returnTasks.add(task);
                inMemoryHistoryManager.addHistoryId(task);
            }
        } else {
            System.out.println("Список пуст.");
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

                    inMemoryHistoryManager.addHistoryId(task);
                }
            }
        } else {
            System.out.println("Список пуст.");
        }
        return new ArrayList<>(returnTasksByType);
    }

    @Override
    public void deleteAllTask(){
        tasks.clear();
        System.out.println("Список дел отчищен.");
    }

    @Override
    public void deleteTaskById(Integer id){

        if (!tasks.containsKey(id)) {
            System.out.println("Задача не найдена");
        } else if (tasks.get(id).getType() == TypeOfTask.EPIC_TASK){
            EpicTask task = (EpicTask) tasks.get(id);
            for (SubTask subTask: task.subTaskList ) {
                tasks.remove(subTask.getIdTask());
            }
            tasks.remove(id);
        } else if (tasks.get(id).getType() == TypeOfTask.SUB_TASK){
            SubTask task = (SubTask) tasks.get(id);
            EpicTask epicTask = task.getEpicTask();
            tasks.remove(id);
            epicTask.subTaskList.remove(task);
            isEpicDone(epicTask);
        } else {
            tasks.remove(id);
        }
    }

    @Override
    public Task findTaskById(Integer id){
        if (tasks.containsKey(id)) {
            System.out.println("Задача: " + id);
            inMemoryHistoryManager.addHistoryId(tasks.get(id));
            return tasks.get(id);
        } else {
            System.out.println("Задача не найдена");
            return null;
        }
    }
}
