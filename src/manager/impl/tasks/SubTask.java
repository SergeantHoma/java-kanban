package manager.impl.tasks;

import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;

public class SubTask extends Task implements Comparable<SubTask> {
    protected TypeOfTask type = TypeOfTask.SUBTASK;

    private transient EpicTask epicTask;
    private int idEpicTask;

    public SubTask(String name, String description, EpicTask epicTask) {
        super(name, description);
        setEpicTask(epicTask);
        idEpicTask = epicTask.getIdTask();
        epicTask.addSubTask(this);
    }

    private SubTask(String name, String description,EpicTask epicTask,Status status) {
        super(name, description);
        setStatus(status);
        setEpicTask(epicTask);
        idEpicTask = epicTask.getIdTask();
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
        return type;
    }

    public void setEpicTask(EpicTask epicTask) {
        this.epicTask = epicTask;
    }

    public int getIdForSerialization() {
        return idEpicTask;
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
            return value + "," + getEpicTask().getIdTask();
        } else {
            return value + startTime.format(DATE_TIME_FORMATTER) + "," + getEpicTask().getIdTask();
        }
    }

    @Override
    public int compareTo(SubTask o) {
        return startTime.toString().compareTo(o.getStartTime());
    }
}
