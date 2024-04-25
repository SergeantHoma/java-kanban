import manager.abstractClass.Managers;
import manager.abstractClass.Task;
import manager.impl.enums.TypeOfTask;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SingleTask;
import manager.impl.tasks.SubTask;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryManagersTest {
    manager.interfaces.TaskManager taskManager = Managers.getDefault();

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

        assertEquals(singleTask.getName(),taskManager.findTaskById(singleTask.getIdTask()).getName());
        assertEquals(singleTask.getDescription(),taskManager.findTaskById(singleTask.getIdTask()).getDescription());
        assertEquals(singleTask.getType(),taskManager.findTaskById(singleTask.getIdTask()).getType());
        assertEquals(singleTask.getStatus(),taskManager.findTaskById(singleTask.getIdTask()).getStatus());

        assertEquals(epicTask.getName(),taskManager.findTaskById(epicTask.getIdTask()).getName());
        assertEquals(epicTask.getDescription(),taskManager.findTaskById(epicTask.getIdTask()).getDescription());
        assertEquals(epicTask.getType(),taskManager.findTaskById(epicTask.getIdTask()).getType());
        assertEquals(epicTask.getStatus(),taskManager.findTaskById(epicTask.getIdTask()).getStatus());

        assertEquals(subTask.getName(),taskManager.findTaskById(subTask.getIdTask()).getName());
        assertEquals(subTask.getDescription(),taskManager.findTaskById(subTask.getIdTask()).getDescription());
        assertEquals(subTask.getType(),taskManager.findTaskById(subTask.getIdTask()).getType());
        assertEquals(subTask.getStatus(),taskManager.findTaskById(subTask.getIdTask()).getStatus());
    }

    @Test
    void shouldDeleteSingleTasks(){
        SingleTask singleTask = new SingleTask ("TestSingle","TestDescription");
        taskManager.createNewSingleTask(singleTask);
        SingleTask singleTaskTwo = new SingleTask ("TestSingle","TestDescription");
        taskManager.createNewSingleTask(singleTaskTwo);

        taskManager.deleteAllTaskByType(TypeOfTask.TASK);
        ArrayList emptyArrayList = new ArrayList();
        assertEquals(emptyArrayList,taskManager.getAllTaskByType(TypeOfTask.TASK));
    }

    @Test
    void shouldDeleteSubTasks(){
        EpicTask epicTask = new EpicTask ("TestEpic","TestDescription");
        taskManager.creatNewEpicTask(epicTask);
        EpicTask epicTaskTwo = new EpicTask ("TestEpic","TestDescription");
        taskManager.creatNewEpicTask(epicTaskTwo);
        SubTask subTask = new SubTask ("TestSub","TestDescription",epicTask);
        taskManager.createNewSubTask(subTask);
        SubTask subTaskTwo = new SubTask ("TestSubTwo","TestDescriptionTwo",epicTask);
        taskManager.createNewSubTask(subTaskTwo);
        SubTask subTaskForSecondEpicTask = new SubTask ("TestSub","TestDescription",epicTaskTwo);
        taskManager.createNewSubTask(subTaskForSecondEpicTask);

        List<Task> epicTaskArrayList = taskManager.getAllTaskByType(TypeOfTask.EPIC);

        taskManager.deleteAllTaskByType(TypeOfTask.SUBTASK);
        ArrayList emptyArrayList = new ArrayList();
        assertEquals(emptyArrayList,taskManager.getAllTaskByType(TypeOfTask.SUBTASK));
        assertEquals(epicTaskArrayList,taskManager.getAllTaskByType(TypeOfTask.EPIC));
    }

    @Test
    void shouldDeleteEpicTasksWithSubTasks(){
        EpicTask epicTask = new EpicTask ("TestEpic","TestDescription");
        taskManager.creatNewEpicTask(epicTask);
        SubTask subTask = new SubTask ("TestSub","TestDescription",epicTask);
        taskManager.createNewSubTask(subTask);
        SubTask subTaskTwo = new SubTask ("TestSubTwo","TestDescriptionTwo",epicTask);
        taskManager.createNewSubTask(subTaskTwo);

        taskManager.deleteAllTaskByType(TypeOfTask.EPIC);
        ArrayList emptyArrayList = new ArrayList();
        assertEquals(emptyArrayList,taskManager.getAllTaskByType(TypeOfTask.SUBTASK));
        assertEquals(emptyArrayList,taskManager.getAllTaskByType(TypeOfTask.EPIC));
    }


}
