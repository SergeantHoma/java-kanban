import manager.abstractClass.Managers;
import manager.abstractClass.Task;
import manager.impl.enums.Status;
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
}


