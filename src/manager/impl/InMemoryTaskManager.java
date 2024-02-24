package manager.impl;

import manager.abstractClass.Task;
import manager.interfaces.HistoryManager;
import manager.interfaces.TaskManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private static int numberOfIdTask;
    private final HashMap<Integer,Task> tasks;
    public final HistoryManager inMemoryHistoryManager;

    public InMemoryTaskManager(HistoryManager inMemoryHistoryManager){
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
    public void addHistoryId(Task task){
        inMemoryHistoryManager.addHistoryId(task);
    }



    @Override
    public SingleTask createNewSingleTask(String name,String description){
        incrementId();
        SingleTask singleTask = new SingleTask(name,description,numberOfIdTask);
        saveNewTask(singleTask);
        return singleTask;
    }

    @Override
    public SubTask createNewSubTask(String name, String description, EpicTask epicTask){
        incrementId();
        SubTask subTask = new SubTask(name,description,epicTask,numberOfIdTask);
        saveNewTask(subTask);
        isEpicDone(epicTask);
        return subTask ;
    }

    @Override
    public EpicTask creatNewEpicTask(String name, String description){
        incrementId();
        EpicTask epicTask = new EpicTask(name,description,numberOfIdTask);
        saveNewTask(epicTask);
        return epicTask;
    }

    @Override
    public void saveNewTask(Task task){
        tasks.put(task.getIdTask(),task);
    }

    @Override
    public void isEpicDone(EpicTask epicTask){
        int statusDone = 0;
        int statusInProgress = 0;
        if (epicTask.subTaskList.isEmpty()){
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
                tasks.put(epicTask.getIdTask(), epicTask);
            } else if (statusDone == epicTask.subTaskList.size()) {
                epicTask = epicTask.setStatus(Status.DONE);
                tasks.put(epicTask.getIdTask(), epicTask);
            } else if (statusDone > 0 || statusInProgress > 0) {
                epicTask = epicTask.setStatus(Status.IN_PROGRESS);
                tasks.put(epicTask.getIdTask(), epicTask);
            }
        }
    }

    @Override
    public SingleTask updateSingleTask(Task task,String name,String description, Status status){
        task = ((SingleTask) task).update(name,description,status);
        tasks.put(task.getIdTask(),task);
        return (SingleTask) task;
    }

    @Override
    public SubTask updateSubTask(Task task, String name,String description, EpicTask epicTask, Status status){
        if (((SubTask) task).getEpicTask().equals(epicTask) ){
            task = ((SubTask) task).update(name,description,epicTask,status);
            tasks.put(task.getIdTask(),task);
            isEpicDone(((SubTask) task).getEpicTask());
        } else {
            Integer numberOfIndexTask = -1;
            for (Integer i: ((SubTask) task).getEpicTask().subTaskList) {
                if (i ==  task.getIdTask()){
                    numberOfIndexTask = i;
                }
            }
            ((SubTask) task).getEpicTask().subTaskList.remove(numberOfIndexTask);
            isEpicDone(((SubTask) task).getEpicTask());
            task = ((SubTask) task).update(name,description,epicTask,status);
            epicTask.subTaskList.add(task.getIdTask());
            isEpicDone(epicTask);
            tasks.put(task.getIdTask(),task);
        }
        return (SubTask) task;
    }

    @Override
    public EpicTask updateEpicTask(Task task,String name,String description){
        task = ((EpicTask) task).update(name,description);
        tasks.put(task.getIdTask(),task);
        isEpicDone((EpicTask)task);
        return (EpicTask) task;
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
        return returnTasks;
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
        return returnTasksByType;
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
            for (Integer integer: task.subTaskList ) {
                tasks.remove(integer);
            }
            tasks.remove(id);
        } else if (tasks.get(id).getType() == TypeOfTask.SUB_TASK){
            SubTask task = (SubTask) tasks.get(id);
            EpicTask epicTask = task.getEpicTask();
            tasks.remove(id);
            epicTask.subTaskList.remove(id);
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
