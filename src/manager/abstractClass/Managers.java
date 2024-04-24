package manager.abstractClass;

import manager.impl.utils.FileBackedTaskManager;
import manager.impl.utils.InMemoryHistoryManager;
import manager.interfaces.HistoryManager;
import manager.interfaces.TaskManager;

import java.io.File;

public abstract class Managers {

    public static TaskManager getDefault() {
        return getFileBackedTaskManager();
    }

    public static FileBackedTaskManager getFileBackedTaskManager() {
        return new FileBackedTaskManager(getDefaultHistory(),new File("resource/fileForBackup.csv"));
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
