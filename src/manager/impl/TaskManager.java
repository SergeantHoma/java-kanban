package manager.impl;

import manager.interfaces.HistoryManager;

public abstract class TaskManager {

    public static manager.interfaces.TaskManager getDefault(){
        return new InMemoryTaskManager(getDefaultHistory());
    }

    public static HistoryManager getDefaultHistory(){
        return new InMemoryHistoryManager();
    }
}
