package manager.impl.tasks;

import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;

import java.time.LocalDateTime;

public class SubTask extends Task implements Comparable<SubTask> {
    private EpicTask epicTask;

    public SubTask(String name, String description, EpicTask epicTask) {
        super(name, description);
        setEpicTask(epicTask);
        epicTask.addSubTask(this);
    }

    private SubTask(String name, String description,EpicTask epicTask,Status status) {
        super(name, description);
        setStatus(status);
        setEpicTask(epicTask);
    }

    public SubTask update(String name,String description,EpicTask epicTask, Status status) {
        return new SubTask(
                name,
                description,
                epicTask,
                status
        );
    }

    public EpicTask getEpicTask() {
        return epicTask;
    }

    @Override
    public TypeOfTask getType() {
        return TypeOfTask.SUBTASK;
    }

    public void setEpicTask(EpicTask epicTask) {
        this.epicTask = epicTask;
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
            value = value + "0";
            return value;
        }
        LocalDateTime localDateTime = startTime.get();
        return value + localDateTime.format(DATE_TIME_FORMATTER) + "," + getEpicTask().getIdTask();
    }

    @Override
    public int compareTo(SubTask o) {
        return startTime.toString().compareTo(o.getStartTime().toString());
    }
}
