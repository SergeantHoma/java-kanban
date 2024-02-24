package manager.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleTaskTest {
    manager.interfaces.TaskManager taskManager = TaskManager.getDefault();

    @Test
    void shouldMakeSingleTask(){
        SingleTask singleTask = taskManager.createNewSingleTask("test","test");
        assertNotNull(singleTask);
    }

    @Test
    void shouldChangeSingleTask(){
        SingleTask singleTaskToChange = taskManager.createNewSingleTask("testToChange","testToChange");
        singleTaskToChange = taskManager.updateSingleTask(singleTaskToChange,"changedName","changedDescription",Status.IN_PROGRESS);
        assertEquals("changedName",singleTaskToChange.getName());
        assertEquals("changedDescription",singleTaskToChange.getDescription());
        assertEquals(Status.IN_PROGRESS,singleTaskToChange.getStatus());
    }

    @Test
    void shouldMakeTwoSingleTasksWithOneIdEquals(){
        SingleTask singleTask1 = taskManager.createNewSingleTask("1","1");
        SingleTask singleTask2 = new SingleTask("1","1",singleTask1.getIdTask());

        assertEquals(singleTask1.toString(),singleTask2.toString(),"Задачи не равны");
    }

}