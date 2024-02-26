package manager.impl;

import manager.abstractClass.Task;
import manager.interfaces.HistoryManager;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private final List<Task> arrayList;

    public InMemoryHistoryManager() {
        arrayList = new ArrayList<>();
    }

    @Override
    public void addHistoryId(Task task){
        if (arrayList.size() == 10){
            arrayList.remove(0);
        }
        if(task.getType() == TypeOfTask.SINGLE_TASK){
            SingleTask singleTask = new SingleTask((SingleTask) task);
            arrayList.add(singleTask);
        } else if (task.getType() == TypeOfTask.SUB_TASK){
            SubTask subTask = new SubTask ((SubTask) task);
            arrayList.add(subTask);
        } else {
            EpicTask epicTask = new EpicTask ((EpicTask) task);
            arrayList.add(epicTask);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(arrayList);
    }
}
