package manager.impl.tasks;

import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;

public class SingleTask extends Task {

    public SingleTask(String name, String description) {
        super(name, description);
    }

    private SingleTask(String name, String description,Status status) {
        super(name, description);
        this.status = status;
    }

    @Override
    public TypeOfTask getType() {
        return TypeOfTask.SINGLE_TASK;
    }

    public SingleTask update(String name, String description, Status status) {
        return new SingleTask(
                name,
                description,
                status
                );
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
