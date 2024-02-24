package manager.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    @Test
    void shouldMakeHistoryAndTaskManager(){
        assertNotNull(TaskManager.getDefault());
        assertNotNull(TaskManager.getDefaultHistory());
    }

}