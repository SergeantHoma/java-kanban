package manager.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTest {
    manager.interfaces.TaskManager taskManager = TaskManager.getDefault();
    EpicTask epicTask;

    @BeforeEach
    void makeEpicTask(){
        epicTask = new EpicTask("test","test");
        taskManager.creatNewEpicTask(epicTask);

    }

    @Test
    void shouldMakeEpicTask(){
        assertNotNull(epicTask);
    }

    @Test
    void shouldChangeEpicTask(){
        taskManager.updateEpicTask(epicTask,"changedName","changedDescription");

        assertEquals("changedName",epicTask.getName());
        assertEquals("changedDescription",epicTask.getDescription());
    }

    @Test
    void shouldChangeStatusAlongWithTheChangeOfSubtasksStatus(){
        SubTask subTaskForChanges_1 = new SubTask ("testForEpic_1",
                "testForEpic_1",epicTask);

        SubTask subTaskForChanges_2 = new SubTask ("testForEpic_2",
                "testForEpic_2",epicTask);

        taskManager.createNewSubTask(subTaskForChanges_1);
        taskManager.createNewSubTask(subTaskForChanges_2);

        taskManager.updateSubTask(subTaskForChanges_1,"ChangedTestForEpic_1",
                "ChangedTestForEpic_1",epicTask,Status.DONE);


        assertEquals(Status.IN_PROGRESS,taskManager.findTaskById(subTaskForChanges_1.
                getEpicTask().getIdTask()).getStatus());

        taskManager.updateSubTask(subTaskForChanges_2,"ChangedTestForEpic_2",
                "ChangedTestForEpic_2",epicTask,Status.DONE);

        assertEquals(Status.DONE,taskManager.findTaskById(subTaskForChanges_2.
                getEpicTask().getIdTask()).getStatus());
    }

    @Test
    void shouldDeleteSubTaskWithDeleteEpicTask(){
        SubTask subTaskForDelete_1 = new SubTask ("testForEpic_1",
                "testForEpic_1",epicTask);
        SubTask subTaskForDelete_2 = new SubTask("testForEpic_2",
                "testForEpic_2",epicTask);

        taskManager.createNewSubTask(subTaskForDelete_1);
        taskManager.createNewSubTask(subTaskForDelete_2);

        taskManager.deleteTaskById(epicTask.getIdTask());

        taskManager.findTaskById(epicTask.getIdTask());
        taskManager.findTaskById(subTaskForDelete_1.getIdTask());
        taskManager.findTaskById(subTaskForDelete_2.getIdTask());
    }


}
