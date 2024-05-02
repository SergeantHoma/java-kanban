package manager.impl.tasks;

import manager.abstractClass.Task;
import manager.impl.enums.TypeOfTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

public class EpicTask extends Task {
    protected TypeOfTask type = TypeOfTask.EPIC;
    private ArrayList<SubTask> subTaskList;
    private LocalDateTime endTime;

    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public EpicTask(String name,String description) {
        super(name,description);
        this.subTaskList = new ArrayList<>();
    }

    private EpicTask(String name, String description,ArrayList arrayList) {
        super(name, description);
        this.subTaskList = arrayList;
    }

    public EpicTask update(String name,String description,ArrayList arrayList) {
        return new EpicTask(
                name,
                description,
                arrayList
        );
    }

    @Override
    public String getEndTime() {
        Optional<SubTask> subTaskWithLastStartTime = subTaskList.stream()
                .max(SubTask::compareTo);
        if (subTaskWithLastStartTime.isEmpty()) {
            return "null";
        }
        this.endTime = LocalDateTime.parse(subTaskWithLastStartTime.get().getEndTime(), DATE_TIME_FORMATTER);
        return endTime.toString();
    }

    @Override
    public Integer getDuration() {
        int result = 0;
        for (SubTask s: subTaskList
             ) {
            if (s.getDuration() != null) {
                result += s.getDuration();
            }
        }
        setDuration(result);
        return duration.toMinutesPart();
    }

    @Override
    public String getStartTime() {
        Optional<SubTask> subTaskWithLastStartTime = subTaskList.stream()
                .min(SubTask::compareTo);
        if (subTaskWithLastStartTime.isEmpty()) {
            return "null";
        }
        this.startTime = LocalDateTime.parse(subTaskWithLastStartTime.get().getStartTime(), DATE_TIME_FORMATTER);
        return startTime.toString();
    }


    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ArrayList<SubTask> getSubTaskList() {
        return new ArrayList<>(subTaskList);
    }

    public void addSubTask(SubTask subTask) {
        subTaskList.add(subTask);
    }

    public void removeSubTask(SubTask subTask) {
        subTaskList.remove(subTask);
    }

    @Override
    public TypeOfTask getType() {
        return type;
    }

    @Override
    public String toString() {
        String value = getIdTask() + "," + getType() +  "," + getName() + ","
                + getDescription() + "," + getStatus() + ",";
        if (duration == null) {
            value += null + ",";
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
