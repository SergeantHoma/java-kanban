package manager.abstractClass;

import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;

public abstract class Task {
    protected String name;
    protected String description;
    protected int idTask;

    protected Status status = Status.NEW;
    public Task(String name,String description){
        setName(name);
        setDescription(description);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
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
