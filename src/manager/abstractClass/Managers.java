package manager.abstractClass;

import manager.impl.utils.FileBackedTaskManager;
import manager.impl.utils.InMemoryHistoryManager;
import manager.impl.utils.InMemoryTaskManager;
import manager.interfaces.HistoryManager;
import manager.interfaces.TaskManager;

import java.io.File;

public abstract class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static TaskManager getFileBackedTaskManager(String file) {
        return new FileBackedTaskManager(getDefaultHistory(),new File(file));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
