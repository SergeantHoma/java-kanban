package manager.impl;

import java.util.ArrayList;
import manager.abstractClass.Task;

public class EpicTask extends Task {
    protected ArrayList<SubTask> subTaskList;

    public EpicTask(String name,String description){
        super(name,description);
        this.subTaskList = new ArrayList<>();
    }

    //Конструкт для создания копии обхекта в класс InMemoryHistoryManager
    public EpicTask(EpicTask epicTask){
        super(epicTask.getName(), epicTask.getDescription(),
                epicTask.getIdTask(),epicTask.getStatus());
        subTaskList = epicTask.getSubTaskList();
    }

    protected void update(String name,String description){
        setName(name);
        setDescription(description);
    }

    @Override
    protected void setIdTask(int idTask) {
        super.setIdTask(idTask);
    }

    @Override
    protected void setStatus(Status status) {
        super.setStatus(status);
    }

    public ArrayList<SubTask> getSubTaskList() {
        return subTaskList;
    }

    @Override
    public TypeOfTask getType() {
        return TypeOfTask.EPIC_TASK;
    }

    @Override
    public String toString() {
        return "Tasks.EpicTask{ name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", idTask=" + this.getIdTask() + '\'' +
                ", subTaskList=" + subTaskList +
                ", status=" + this.getStatus() + '\'' +
                '}';
    }
}
