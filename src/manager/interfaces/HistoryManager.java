package manager.interfaces;

import manager.abstractClass.Task;
import java.util.ArrayList;

public interface HistoryManager {
    void addHistoryId(Task task);

    ArrayList<Task> getHistory();

}
