package manager.impl;

import manager.abstractClass.Task;

public class SingleTask extends Task {

    public SingleTask(String name, String description) {
        super(name, description);
    }

    //Конструкт для создания копии обхекта в класс InMemoryHistoryManager
    public SingleTask(SingleTask singleTask){
        super(singleTask.getName(), singleTask.getDescription(),
                singleTask.getIdTask(),singleTask.getStatus());
    }

    @Override
    public TypeOfTask getType() {
        return TypeOfTask.SINGLE_TASK;
    }

    protected void update(String name,String description, Status status){
        setName(name);
        setDescription(description);
        setStatus(status);
    }

    @Override
    protected void setIdTask(int idTask) {
        super.setIdTask(idTask);
    }

    @Override
    public String toString() {
        return "Tasks.SingleTask{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", idTask=" + this.getIdTask() +
                ", status=" + this.getStatus() +
                '}';
    }
}
