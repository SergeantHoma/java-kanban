package manager.abstractClass;

import manager.impl.utils.InMemoryHistoryManager;
import manager.impl.utils.InMemoryTaskManager;
import manager.interfaces.HistoryManager;
import manager.interfaces.TaskManager;

public abstract class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
