package manager.impl.tasks;

import java.util.ArrayList;
import manager.abstractClass.Task;
import manager.impl.enums.TypeOfTask;

public class EpicTask extends Task {
    private ArrayList<SubTask> subTaskList;

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
                arrayList);

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
