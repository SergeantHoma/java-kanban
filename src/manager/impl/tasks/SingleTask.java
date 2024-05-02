package manager.impl.tasks;

import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;

public class SingleTask extends Task {

    protected TypeOfTask type = TypeOfTask.TASK;

    public SingleTask(String name, String description) {
        super(name, description);
    }

    private SingleTask(String name, String description,Status status) {
        super(name, description);
        this.status = status;
    }

    @Override
    public TypeOfTask getType() {
        return type;
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
        String value = getIdTask() + "," + getType() +  "," + getName() + ","
                + getDescription() + "," + getStatus() + ",";
        if (duration == null) {
            value += ",";
        } else {
            value += getDuration() + ",";
        }
        if (startTime == null) {
            value += "0";
            return value;
        } else {
            return value + startTime.format(DATE_TIME_FORMATTER);
        }
    }
}
