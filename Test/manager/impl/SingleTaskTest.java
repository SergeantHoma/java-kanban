package manager.impl;

import manager.abstractClass.Managers;
import manager.impl.enums.Status;
import manager.impl.tasks.SingleTask;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SingleTaskTest {
    manager.interfaces.TaskManager taskManager = Managers.getDefault();

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
        taskManager.updateSingleTask(singleTask,"changedName","changedDescription", Status.IN_PROGRESS);
        assertEquals("changedName",taskManager.findTaskById(singleTask.getIdTask()).getName());
        assertEquals("changedDescription",taskManager.findTaskById(singleTask.getIdTask()).getDescription());
        assertEquals(Status.IN_PROGRESS,taskManager.findTaskById(singleTask.getIdTask()).getStatus());
    }

    @Test
    void shouldMakeTwoSingleTasksWithOneIdEquals(){
        SingleTask singleTask = new SingleTask ("test","test");
        taskManager.createNewSingleTask(singleTask);
        SingleTask singleTask2 = new SingleTask("test","test");
        singleTask2.setIdTask(singleTask.getIdTask());

        assertEquals(taskManager.findTaskById(singleTask.getIdTask()).toString(),
                singleTask2.toString(),"Задачи не равны");
    }

}
