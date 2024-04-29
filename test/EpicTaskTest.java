import manager.abstractClass.Managers;
import manager.impl.enums.Status;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SubTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTaskTest {
    manager.interfaces.TaskManager taskManager = Managers.getDefault();
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

        assertEquals("changedName",taskManager.findTaskById(epicTask.getIdTask()).getName());
        assertEquals("changedDescription",taskManager.findTaskById(epicTask.getIdTask()).getDescription());
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
                "ChangedTestForEpic_1", Status.DONE);


        assertEquals(Status.IN_PROGRESS,taskManager.findTaskById(subTaskForChanges_1.
                getEpicTask().getIdTask()).getStatus());

        taskManager.updateSubTask(subTaskForChanges_2,"ChangedTestForEpic_2",
                "ChangedTestForEpic_2",Status.DONE);

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
        assertNull(taskManager.findTaskById(subTaskForDelete_1.getIdTask()));
        assertNull(taskManager.findTaskById(subTaskForDelete_2.getIdTask()));
    }

    @Test
    void shouldMakeSubTasksWithStatusNEW(){
        SubTask subTask1 = new SubTask ("testForEpic_1",
                "testForEpic_1",epicTask);
        SubTask subTask2 = new SubTask("testForEpic_2",
                "testForEpic_2",epicTask);
        taskManager.createNewSubTask(subTask1);
        taskManager.createNewSubTask(subTask2);
        assertEquals(Status.NEW,taskManager.findTaskById(epicTask.getIdTask()).getStatus());
    }

    @Test
    void shouldMakeSubTasksWithStatusDONE(){
        SubTask subTask1 = new SubTask ("testForEpic_1",
                "testForEpic_1",epicTask);
        SubTask subTask2 = new SubTask("testForEpic_2",
                "testForEpic_2",epicTask);
        taskManager.createNewSubTask(subTask1);
        taskManager.createNewSubTask(subTask2);
        taskManager.updateSubTask(subTask1,"testForEpic_1","testForEpic_1",Status.DONE);
        taskManager.updateSubTask(subTask2,"testForEpic_2","testForEpic_2",Status.DONE);
        assertEquals(Status.DONE,taskManager.findTaskById(epicTask.getIdTask()).getStatus());
    }

    @Test
    void shouldMakeSubTasksWithStatusDONEAndNEW() {
        SubTask subTask1 = new SubTask("testForEpic_1",
                "testForEpic_1", epicTask);
        SubTask subTask2 = new SubTask("testForEpic_2",
                "testForEpic_2", epicTask);
        taskManager.updateSubTask(subTask1,"testForEpic_1","testForEpic_1",Status.DONE);
        assertEquals(Status.IN_PROGRESS,taskManager.findTaskById(epicTask.getIdTask()).getStatus());
    }

    @Test
    void shouldMakeSubTasksWithStatusINPROGRESS(){
        SubTask subTask1 = new SubTask ("testForEpic_1",
                "testForEpic_1",epicTask);
        SubTask subTask2 = new SubTask("testForEpic_2",
                "testForEpic_2",epicTask);
        taskManager.createNewSubTask(subTask1);
        taskManager.createNewSubTask(subTask2);
        taskManager.updateSubTask(subTask1,"testForEpic_1","testForEpic_1",Status.IN_PROGRESS);
        taskManager.updateSubTask(subTask2,"testForEpic_2","testForEpic_2",Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS,taskManager.findTaskById(epicTask.getIdTask()).getStatus());
    }

}
