package manager.impl.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import manager.abstractClass.Task;
import manager.impl.enums.TypeOfTask;

public class EpicTask extends Task {
    private ArrayList<SubTask> subTaskList;
    private LocalDateTime endTime;

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
    public LocalDateTime getEndTime() {
        Optional<SubTask> subTaskWithLastStartTime = subTaskList.stream()
                .min(SubTask::compareTo);
        this.endTime = subTaskWithLastStartTime.get().getEndTime();
        if (endTime == null) {
            return null;
        }
        return endTime;
    }

    @Override
    public Duration getDuration() {
        int result = 0;
        for (SubTask s: subTaskList
             ) {
            result += s.getDuration().toMinutesPart();
        }
        setDuration(result);
        return duration.orElse(null);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public ArrayList<SubTask> getSubTaskList() {
        return new ArrayList<SubTask>(subTaskList);
    }

    public void addSubTask(SubTask subTask) {
        subTaskList.add(subTask);
    }

    public void removeSubTask(SubTask subTask) {
        subTaskList.remove(subTask);
    }

    @Override
    public TypeOfTask getType() {
        return TypeOfTask.EPIC;
    }

    @Override
    public String toString() {
        return  this.getIdTask() + "," +
                this.getType() + "," +
                this.getName() + ',' +
                this.getStatus() + "," +
                this.getDescription();
    }
}
