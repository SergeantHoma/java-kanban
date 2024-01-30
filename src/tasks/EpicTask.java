package tasks;

import java.util.ArrayList;
import abstractClass.Task;

public class EpicTask extends Task {
    protected ArrayList<Integer> subTaskList;

    public EpicTask(String name, String description) {
        super(name, description);
        this.subTaskList = new ArrayList<>();
    }

    protected EpicTask (String name, String description, int id, ArrayList<Integer> subTaskList, Status status){
        super(name,description,id);
        this.subTaskList = subTaskList;
        this.status = status;
    }

    protected EpicTask setStatus(Status status){
        return new EpicTask(
                this.getName(),
                this.getDescription(),
                this.getIdTask(),
                this.getSubTaskList(),
                status
        );
    }

    protected EpicTask update(String name,String description){
        return new EpicTask(
                name,
                description,
                this.getIdTask(),
                this.getSubTaskList(),
                this.getStatus()
        );
    }

    public ArrayList<Integer> getSubTaskList() {
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
