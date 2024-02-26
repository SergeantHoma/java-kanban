package manager.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    manager.interfaces.TaskManager taskManager = TaskManager.getDefault();

    @Test
    void shouldMakeSingleTask(){
        SingleTask singleTask = new SingleTask ("TestSingle","TestDescription");
        taskManager.createNewSingleTask(singleTask);
        assertNotNull(singleTask);
    }

    @Test
    void shouldNotConflictBetweenSelfCreatedAndTaskManagersTasks(){
        SingleTask singleTask = new SingleTask ("TestSingle","TestDescription");
        taskManager.createNewSingleTask(singleTask);
        SingleTask singleTask1 = new SingleTask("1","2");
        singleTask1.setIdTask(singleTask.getIdTask());

        assertNotNull(taskManager.findTaskById(singleTask.getIdTask()));
    }

    @Test
    void shouldNotChangedDataWhenTaskAddedToHistoryManager(){
        SingleTask singleTask = new SingleTask ("TestSingle","TestDescription");
        taskManager.createNewSingleTask(singleTask);
        SingleTask singleTaskToCompare = (SingleTask) taskManager.findTaskById(singleTask.getIdTask());

        assertEquals(singleTask.getName(),singleTaskToCompare.getName());
        assertEquals(singleTask.getDescription(),singleTaskToCompare.getDescription());
    }

    @Test
    void  shouldMakeSubAndEpicTaskTask(){
        EpicTask epicTask = new EpicTask ("TestEpic","TestDescription");
        taskManager.creatNewEpicTask(epicTask);
        SubTask subTask = new SubTask ("TestSub","TestDescription",epicTask);
        taskManager.createNewSubTask(subTask);
        assertNotNull(epicTask);
        assertNotNull(subTask);
    }

    @Test
    void shouldFindTask(){
        SingleTask singleTask = new SingleTask ("TestSingle","TestDescription");
        taskManager.createNewSingleTask(singleTask);
        EpicTask epicTask = new EpicTask ("TestEpic","TestDescription");
        taskManager.creatNewEpicTask(epicTask);
        SubTask subTask = new SubTask ("TestSub","TestDescription",epicTask);
        taskManager.createNewSubTask(subTask);

        System.out.println(taskManager.findTaskById(singleTask.getIdTask()));
        System.out.println(taskManager.findTaskById(epicTask.getIdTask()));
        System.out.println(taskManager.findTaskById(subTask.getIdTask()));
    }

}
