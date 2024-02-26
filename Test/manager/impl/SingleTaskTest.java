package manager.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleTaskTest {
    manager.interfaces.TaskManager taskManager = TaskManager.getDefault();

    @Test
    void shouldMakeSingleTask(){
        SingleTask singleTask = new SingleTask ("test","test");
        taskManager.createNewSingleTask(singleTask);
        assertNotNull(singleTask);
    }

    @Test
    void shouldChangeSingleTask(){
        SingleTask singleTask = new SingleTask ("test","test");
        taskManager.createNewSingleTask(singleTask);
        taskManager.updateSingleTask(singleTask,"changedName","changedDescription",Status.IN_PROGRESS);
        assertEquals("changedName",singleTask.getName());
        assertEquals("changedDescription",singleTask.getDescription());
        assertEquals(Status.IN_PROGRESS,singleTask.getStatus());
    }

    @Test
    void shouldMakeTwoSingleTasksWithOneIdEquals(){
        SingleTask singleTask = new SingleTask ("test","test");
        taskManager.createNewSingleTask(singleTask);
        SingleTask singleTask2 = new SingleTask("test","test");
        singleTask2.setIdTask(singleTask.getIdTask());

        assertEquals(singleTask.toString(),singleTask2.toString(),"Задачи не равны");
    }

}
