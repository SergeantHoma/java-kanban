package manager.impl;

import manager.abstractClass.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    manager.interfaces.TaskManager taskManager;
    @BeforeEach
    void beforeAll(){
        taskManager = TaskManager.getDefault();
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
        SingleTask singleTaskToCompare = new SingleTask(singleTask);
        System.out.println(taskManager.findTaskById(singleTask.getIdTask()));

        taskManager.updateSingleTask(singleTask,"testNew","testNew",Status.IN_PROGRESS);
        System.out.println(taskManager.findTaskById(singleTask.getIdTask()));

        ArrayList<Task> arrayList = taskManager.getHistory();

                assertEquals(singleTaskToCompare.getStatus(), arrayList.get(0).getStatus());
                assertEquals(singleTaskToCompare.getDescription(),arrayList.get(0).getDescription());
                assertEquals(singleTaskToCompare.getName(),arrayList.get(0).getName());
                assertEquals(singleTask.getStatus(),arrayList.get(1).getStatus());
                assertEquals(singleTask.getDescription(),arrayList.get(1).getDescription());
                assertEquals(singleTask.getName(),arrayList.get(1).getName());
        }

    }


