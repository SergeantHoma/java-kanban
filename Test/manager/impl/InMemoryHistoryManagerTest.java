package manager.impl;

import manager.abstractClass.Managers;
import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.tasks.SingleTask;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    manager.interfaces.TaskManager taskManager;
    @BeforeEach
    void beforeAll(){
        taskManager = Managers.getDefault();
    }

    @Test
    void shouldAddNewTaskInHistoryManager() {
        SingleTask singleTask = new SingleTask ("Single task1","TestST1");
        taskManager.createNewSingleTask(singleTask);

        taskManager.findTaskById(singleTask.getIdTask());
        ArrayList<Task> history =  taskManager.getHistory();
        assertNotNull(history, "Задачи не возвращаются.");
        assertEquals(1, history.size(), "Неверное количество задач.");
    }

   @Test
    void shouldContainOldAndChangedDataInHistoryManager(){
        SingleTask singleTask = new SingleTask ("Single task1","TestST1");
        taskManager.createNewSingleTask(singleTask);
        taskManager.findTaskById(singleTask.getIdTask());

        taskManager.updateSingleTask(singleTask,"testNew","testNew", Status.IN_PROGRESS);
        taskManager.findTaskById(singleTask.getIdTask());

        ArrayList<Task> arrayList = taskManager.getHistory();

                assertNotEquals(arrayList.get(1).getStatus(), arrayList.get(0).getStatus());
                assertNotEquals(arrayList.get(1).getDescription(),arrayList.get(0).getDescription());
                assertNotEquals(arrayList.get(1).getName(),arrayList.get(0).getName());
        }

    }


