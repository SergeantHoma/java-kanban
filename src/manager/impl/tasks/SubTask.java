package manager.impl.tasks;

import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;

public class SubTask extends Task {
    private EpicTask epicTask;

    public SubTask(String name, String description, EpicTask epicTask) {
        super(name, description);
        setEpicTask(epicTask);
        epicTask.subTaskList.add(this);
    }

    private SubTask(String name, String description,EpicTask epicTask,Status status) {
        super(name, description);
        setStatus(status);
        setEpicTask(epicTask);
    }

    public SubTask update(String name,String description,EpicTask epicTask, Status status){
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
        return TypeOfTask.SUB_TASK;
    }

    public void setEpicTask(EpicTask epicTask) {
        this.epicTask = epicTask;
    }

    @Override
    public String toString() {
        return "Tasks.SubTask{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", idTask=" + this.getIdTask() +
                ", epicTask=" + epicTask.getName() +
                ", status=" + this.getStatus() +
                '}';
    }
}
