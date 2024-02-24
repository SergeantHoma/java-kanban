package manager.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    manager.interfaces.TaskManager taskManager = TaskManager.getDefault();

    @Test
    void shouldMakeSingleTask(){
        SingleTask singleTask = taskManager.createNewSingleTask("TestSingle","TestDescription");
        assertNotNull(singleTask);
    }

    @Test
    void shouldNotConflictBetweenSelfCreatedAndTaskManagersTasks(){
        SingleTask singleTask = taskManager.createNewSingleTask("1","2");
        SingleTask singleTask1 = new SingleTask("1","2",singleTask.getIdTask());

        assertNotNull(taskManager.findTaskById(singleTask.getIdTask()));
    }

    @Test
    void shouldNotChangedDataWhenTaskAddedToHistoryManager(){
        SingleTask singleTask = taskManager.createNewSingleTask("test","test");
        SingleTask singleTaskToCompare = (SingleTask) taskManager.findTaskById(singleTask.getIdTask());

        assertEquals(singleTask.getName(),singleTaskToCompare.getName());
        assertEquals(singleTask.getDescription(),singleTaskToCompare.getDescription());
    }

    @Test
    void  shouldMakeSubAndEpicTaskTask(){
        EpicTask epicTask = taskManager.creatNewEpicTask("TestEpic","TestDescription");
        SubTask subTask = taskManager.createNewSubTask("TestSub","TestDescription",epicTask);
        assertNotNull(epicTask);
        assertNotNull(subTask);
    }

    @Test
    void shouldFindTask(){
        SingleTask singleTask = taskManager.createNewSingleTask("TestSingle","TestDescription");
        EpicTask epicTask = taskManager.creatNewEpicTask("TestEpic","TestDescription");
        SubTask subTask = taskManager.createNewSubTask("TestSub","TestDescription",epicTask);

        System.out.println(taskManager.findTaskById(singleTask.getIdTask()));
        System.out.println(taskManager.findTaskById(epicTask.getIdTask()));
        System.out.println(taskManager.findTaskById(subTask.getIdTask()));
    }

}