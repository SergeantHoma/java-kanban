package manager.abstractClass;

import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Task {
    protected String name;
    protected String description;
    protected int idTask;

    protected Status status = Status.NEW;

    protected Duration duration = null;
    protected LocalDateTime startTime = null;
    protected static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


    public Task(String name,String description) {
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

    public Integer getDuration() {
        if (duration == null) {
            return 0;
        } else {
            return duration.toMinutesPart();
        }
    }

    public void setDuration(Integer minutes) {
        if (minutes == null) {
            this.duration = null;
            return;
        }
        this.duration = Duration.ofMinutes(minutes);
    }


    public void setStartTime(String text) {
        if (text == null) {
            this.startTime = null;
            return;
        }  else if (text.equals("null")) {
            this.startTime = null;
            return;
        }
        this.startTime = LocalDateTime.parse(text, DATE_TIME_FORMATTER);
    }

    public String getStartTime() {
        if (startTime == null) {
            return "null";
        } else if (startTime.toString().equals("null")) {
            return "null";
        }
        return startTime.format(DATE_TIME_FORMATTER);
    }

    public String getEndTime() {
        if (startTime == null) {
            return "null";
        }
        return startTime.plusMinutes(duration.toMinutesPart()).format(DATE_TIME_FORMATTER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return idTask == task.idTask && name.equals(task.name) && description.equals(task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, idTask, status);
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
