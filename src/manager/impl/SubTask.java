package manager.impl;

import manager.abstractClass.Task;

public class SubTask extends Task {
    private EpicTask epicTask;

    public SubTask(String name, String description, EpicTask epicTask) {
        super(name, description);
        setEpicTask(epicTask);
        epicTask.subTaskList.add(this);
    }

    //Конструкт для создания копии обхекта в класс InMemoryHistoryManager
    public SubTask(SubTask subTask){
        super(subTask.getName(), subTask.getDescription(),
                subTask.getIdTask(),subTask.getStatus());
        setEpicTask(subTask.getEpicTask());
    }

    protected void update(String name,String description,EpicTask epicTask, Status status){
        setName(name);
        setDescription(description);
        setEpicTask(epicTask);
        setStatus(status);
    }

    @Override
    protected void setIdTask(int idTask) {
        super.setIdTask(idTask);
    }

    public EpicTask getEpicTask() {
        return epicTask;
    }

    @Override
    public TypeOfTask getType() {
        return TypeOfTask.SUB_TASK;
    }

    public void setEpicTask(EpicTask epicTask) {
        this.epicTask = epicTask;
    }

    @Override
    public String toString() {
        return "Tasks.SubTask{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", idTask=" + this.getIdTask() +
                ", epicTask=" + epicTask.getName() +
                ", status=" + this.getStatus() +
                '}';
    }
}
