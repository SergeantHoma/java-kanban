package manager.impl;

import manager.abstractClass.Task;
import manager.interfaces.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private final ArrayList<Task> arrayList;

    public InMemoryHistoryManager() {
        this.arrayList = new ArrayList<>();
    }

    @Override
    public void addHistoryId(Task task) {
        if (arrayList.size() == 10){
            arrayList.remove(0);
            arrayList.add(task);
        } else
            arrayList.add(task);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return arrayList;
    }
}
