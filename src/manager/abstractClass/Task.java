package manager.abstractClass;

import manager.impl.Status;
import manager.impl.TypeOfTask;

import java.util.Objects;

public abstract class Task {
    private String name;
    private String description;
    private int idTask;
    private Status status = Status.NEW;

    public Task(String name,String description,int id,Status status){
        setName(name);
        setDescription(description);
        setIdTask(id);
        setStatus(status);
    }
    public Task(String name,String description){
        setName(name);
        setDescription(description);
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public abstract TypeOfTask getType();

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getIdTask() {
        return idTask;
    }

    @Override
    public String toString() {
        return "AbstractClass.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", idTask=" + idTask +
                ", status=" + status +
                '}';
    }
}
