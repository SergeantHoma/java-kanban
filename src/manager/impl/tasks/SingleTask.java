package manager.impl.tasks;

import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;

import java.time.LocalDateTime;
import java.util.Optional;

public class SingleTask extends Task {
    public SingleTask(String name, String description) {
        super(name, description);
        duration = Optional.empty();
    }

    private SingleTask(String name, String description,Status status) {
        super(name, description);
        this.status = status;
    }

    @Override
    public TypeOfTask getType() {
        return TypeOfTask.TASK;
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
        if (duration.isEmpty()) {
            value += ",";
        } else {
            value += getDuration().toMinutesPart() + ",";
        }
        if (startTime.isEmpty()) {
            value += "0";
            return value;
        }
        LocalDateTime localDateTime = startTime.get();
        return value + localDateTime.format(DATE_TIME_FORMATTER);
    }
}
