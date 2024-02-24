package manager.interfaces;

import manager.abstractClass.Task;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface HistoryManager {
    void addHistoryId(Task task);

    ArrayList<Task> getHistory();

}
