import manager.abstractClass.Managers;
import manager.abstractClass.Task;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SingleTask;
import manager.impl.tasks.SubTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    manager.interfaces.TaskManager taskManager;

    @BeforeEach
    void beforeAll() {
        taskManager = Managers.getDefault();
    }

    @Test
    void shouldAddNewTaskInHistoryManager() {
        SingleTask singleTask = new SingleTask("Single task1", "TestST1");
        taskManager.createNewSingleTask(singleTask);

        taskManager.findTaskById(singleTask.getIdTask());
        ArrayList<Task> history = taskManager.getHistory();
        assertNotNull(history, "Задачи не возвращаются.");
        assertEquals(1, history.size(), "Неверное количество задач.");
    }

    @Test
    void shouldDeleteHistoryWithDeleteSingleTask(){
        SingleTask singleTask = new SingleTask("Single task1", "TestST1");
        taskManager.createNewSingleTask(singleTask);

        taskManager.findTaskById(singleTask.getIdTask());
        taskManager.deleteTaskById(singleTask.getIdTask());
        ArrayList<Task> history = taskManager.getHistory();
        assertEquals(0,history.size(),"Неверная работа метода delete с singleTask");
    }

    @Test
    void shouldDeleteHistoryWithDeleteSubTask(){
        EpicTask eT1 = new EpicTask ("Epic task 1","1 subtask" );
        taskManager.creatNewEpicTask(eT1);
        SubTask sT1_1 = new SubTask ("Sub task 1","1_1 subtask", eT1);
        taskManager.createNewSubTask(sT1_1);

        taskManager.findTaskById(eT1.getIdTask());
        taskManager.findTaskById(sT1_1.getIdTask());
        taskManager.deleteTaskById(sT1_1.getIdTask());
        ArrayList<Task> history = taskManager.getHistory();
        assertEquals(1,history.size(),"Неверная работа метода delete с SubTask");
    }

    @Test
    void shouldDeleteHistoryWithDeleteEpicTask(){
        EpicTask eT1 = new EpicTask ("Epic task 1","1 subtask" );
        taskManager.creatNewEpicTask(eT1);
        SubTask sT1_1 = new SubTask ("Sub task 1","1_1 subtask", eT1);
        taskManager.createNewSubTask(sT1_1);

        taskManager.findTaskById(eT1.getIdTask());
        taskManager.findTaskById(sT1_1.getIdTask());
        taskManager.deleteTaskById(eT1.getIdTask());
        ArrayList<Task> history = taskManager.getHistory();
        assertEquals(0,history.size(),"Неверная работа метода delete с EpicTask");
    }

    @Test
    void shouldReturnEmptyHistory(){
        assertTrue(taskManager.getHistory().isEmpty());
    }

    @Test
    void shouldNotCreateTwoTasksInHistoryFromOneObject(){
        SingleTask singleTask = new SingleTask("Single task1", "TestST1");
        taskManager.createNewSingleTask(singleTask);
        taskManager.createNewSingleTask(singleTask);
        taskManager.findTaskById(singleTask.getIdTask());
        assertEquals(1,taskManager.getHistory().size());
    }

    @Test
    void shouldDeleteFirstMiddleAndLastTask(){
        SingleTask singleTask1 = new SingleTask("Single task1", "TestST1");
        SingleTask singleTask2 = new SingleTask("Single task2", "TestST2");
        SingleTask singleTask3 = new SingleTask("Single task3", "TestST3");
        SingleTask singleTask4 = new SingleTask("Single task4", "TestST4");
        SingleTask singleTask5 = new SingleTask("Single task5", "TestST5");
        taskManager.createNewSingleTask(singleTask1);
        taskManager.createNewSingleTask(singleTask2);
        taskManager.createNewSingleTask(singleTask3);
        taskManager.createNewSingleTask(singleTask4);
        taskManager.createNewSingleTask(singleTask5);

        taskManager.deleteTaskById(singleTask1.getIdTask());
        assertEquals(4,taskManager.getAllTask().size());
        taskManager.deleteTaskById(singleTask3.getIdTask());
        assertEquals(3,taskManager.getAllTask().size());
        taskManager.deleteTaskById(singleTask5.getIdTask());
        assertEquals(2,taskManager.getAllTask().size());
    }
}


