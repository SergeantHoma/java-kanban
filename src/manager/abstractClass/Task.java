package manager.abstractClass;

import manager.impl.Status;
import manager.impl.TypeOfTask;

public abstract class Task {
    private String name;
    private String description;

    private final int  idTask;
    protected Status status = Status.NEW;

    protected Task(String name,String description,int idTask){
        this.name = name;
        this.description = description;
        this.idTask = idTask;
    }

    public abstract TypeOfTask getType();

    public Status getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
                '}';
    }
}
