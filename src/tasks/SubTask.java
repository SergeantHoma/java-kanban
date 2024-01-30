package tasks;

import abstractClass.Task;

public class SubTask extends Task {
    private EpicTask epicTask;

    public SubTask(String name, String description, EpicTask epicTask) {
        super(name, description);
        this.epicTask = epicTask;
        epicTask.subTaskList.add(this.getIdTask());
    }

    private SubTask(String name,String description, EpicTask epicTask, int id, Status status){
        super(name,description,id);
        this.epicTask = epicTask;
        this.status = status;
    }

    protected SubTask update(String name,String description,EpicTask epicTask, Status status){
        return new SubTask(
                name,
                description,
                epicTask,
                this.getIdTask(),
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
