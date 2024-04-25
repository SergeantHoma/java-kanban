import manager.abstractClass.Managers;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SubTask;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    static manager.interfaces.TaskManager taskManager = Managers.getDefault();
    public static EpicTask epicTaskTest;
    public static SubTask subTaskTest;

    @BeforeAll
    static void makeEpicAndSubTasks(){
        epicTaskTest = new EpicTask ("EpicTask", "EpicTaskDescription");
        taskManager.creatNewEpicTask(epicTaskTest);
        subTaskTest = new SubTask ("SubTask", "SubTaskForEpicTaskTest", epicTaskTest);
        taskManager.createNewSubTask(subTaskTest);
        assertNotNull(taskManager.getAllTaskByType(TypeOfTask.EPIC));
        assertNotNull(taskManager.getAllTaskByType(TypeOfTask.SUBTASK));
    }

    @Test
    void shouldMakeSubTaskWithEpicTask() {
        assertNotNull(subTaskTest);
    }

    @Test
    void shouldChangeSubTask(){
        taskManager.updateSubTask(subTaskTest,"SubTaskChangedName",
                "SubTaskForEpicTaskTestChanged",Status.IN_PROGRESS);
        assertEquals("SubTaskChangedName",taskManager.findTaskById(subTaskTest.getIdTask()).getName());
        assertEquals("SubTaskForEpicTaskTestChanged",taskManager.findTaskById(subTaskTest.getIdTask())
                .getDescription());
        assertEquals(Status.IN_PROGRESS,taskManager.findTaskById(subTaskTest.getIdTask()).getStatus());
    }

    @Test
    void shouldMakeTwoSubTasksWithOneIdEquals() {
        SubTask subTaskToCompare = new SubTask ("SubTask", "SubTaskForEpicTaskTest", epicTaskTest);
        taskManager.createNewSubTask(subTaskToCompare);

        SubTask subTask = new SubTask ("SubTask", "SubTaskForEpicTaskTest", epicTaskTest);
        subTask.setIdTask(subTaskToCompare.getIdTask());

        assertEquals(subTaskToCompare.toString(),subTask.toString());
    }
}
