package manager.abstractClass;

import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Optional;

public abstract class Task {
    protected String name;
    protected String description;
    protected int idTask;

    protected Status status = Status.NEW;

    protected Optional<Duration> duration = Optional.empty();
    protected Optional<LocalDateTime> startTime = Optional.empty();
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

    public Duration getDuration() {
        return duration.orElse(null);
    }

    public void setDuration(Integer minutes) {
        if (minutes == null) {
            this.duration = Optional.empty();
            return;
        }
        this.duration = Optional.of(Duration.ofMinutes(minutes));
    }


    public void setStartTime(String text) {
        if (text == null) {
            this.startTime = Optional.empty();
            return;
        } else if (text.equals("null")) {
            this.startTime = Optional.empty();
            return;
        }
        this.startTime = Optional.of(LocalDateTime.parse(text, DATE_TIME_FORMATTER));
    }

    public LocalDateTime getStartTime() {
        return startTime.orElse(null);
    }

    public LocalDateTime getEndTime() {
        return startTime.map(localDateTime -> localDateTime.plus(duration.get())).orElse(null);
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
