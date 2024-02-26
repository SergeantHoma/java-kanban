package manager.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    static manager.interfaces.TaskManager taskManager = TaskManager.getDefault();
    public static EpicTask epicTaskTest;
    public static SubTask subTaskTest;

    @BeforeAll
    static void makeEpicAndSubTasks(){
        epicTaskTest = new EpicTask ("EpicTask", "EpicTaskDescription");
        taskManager.creatNewEpicTask(epicTaskTest);
        subTaskTest = new SubTask ("SubTask", "SubTaskForEpicTaskTest", epicTaskTest);
        taskManager.createNewSubTask(subTaskTest);
        assertNotNull(taskManager.getAllTaskByType(TypeOfTask.EPIC_TASK));
        assertNotNull(taskManager.getAllTaskByType(TypeOfTask.SUB_TASK));
    }

    @Test
    void shouldMakeSubTaskWithEpicTask() {
        assertNotNull(subTaskTest);
    }

    @Test
    void shouldChangeSubTask(){
        taskManager.updateSubTask(subTaskTest,"SubTaskChangedName",
                "SubTaskForEpicTaskTestChanged",epicTaskTest,Status.IN_PROGRESS);
        assertEquals("SubTaskChangedName",subTaskTest.getName());
        assertEquals("SubTaskForEpicTaskTestChanged",subTaskTest.getDescription());
        assertEquals(Status.IN_PROGRESS,subTaskTest.getStatus());
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
