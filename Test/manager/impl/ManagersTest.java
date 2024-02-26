package manager.impl;

import manager.abstractClass.Managers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void shouldMakeHistoryAndTaskManager(){
        assertNotNull(Managers.getDefault());
        assertNotNull(Managers.getDefaultHistory());
    }

}
