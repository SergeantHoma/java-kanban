package manager.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    static manager.interfaces.TaskManager taskManager = TaskManager.getDefault();
    public static EpicTask epicTaskTest;
    public static SubTask subTaskTest;

    @BeforeAll
    static void makeEpicAndSubTasks(){
        epicTaskTest = taskManager.creatNewEpicTask("EpicTask", "EpicTaskDescription");
        subTaskTest = taskManager.createNewSubTask("SubTask", "SubTaskForEpicTaskTest", epicTaskTest);
    }

    @Test
    void shouldMakeSubTaskWithEpicTask() {
        assertNotNull(subTaskTest);
    }

    @Test
    void shouldChangeSubTask(){
        subTaskTest = taskManager.updateSubTask(subTaskTest,"SubTaskChangedName",
                "SubTaskForEpicTaskTestChanged",epicTaskTest,Status.IN_PROGRESS);
        assertEquals("SubTaskChangedName",subTaskTest.getName());
        assertEquals("SubTaskForEpicTaskTestChanged",subTaskTest.getDescription());
        assertEquals(Status.IN_PROGRESS,subTaskTest.getStatus());
    }

    @Test
    void shouldMakeTwoSubTasksWithOneIdEquals() {
        SubTask subTaskToCompare = new SubTask("SubTaskChangedName","SubTaskForEpicTaskTestChanged",
                subTaskTest.getEpicTask(),subTaskTest.getIdTask());
        subTaskToCompare = taskManager.updateSubTask(subTaskTest,"SubTaskChangedName",
                "SubTaskForEpicTaskTestChanged",epicTaskTest,Status.IN_PROGRESS);
        assertEquals(subTaskToCompare.toString(),subTaskTest.toString());
    }
}