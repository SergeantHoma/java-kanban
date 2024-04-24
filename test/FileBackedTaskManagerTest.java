import manager.abstractClass.Managers;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SingleTask;
import manager.impl.tasks.SubTask;
import manager.impl.utils.FileBackedTaskManager;
import manager.impl.utils.InMemoryHistoryManager;
import manager.interfaces.TaskManager;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
public class FileBackedTaskManagerTest {

    @Test
    void loadAnEmptyFile() {
        TaskManager managerWithEmptyFile = FileBackedTaskManager
                .loadFromFile(new File("resource/empty.csv"));
        assertEquals(0, managerWithEmptyFile.getAllTask().size());
    }

    @Test
    void saveAnEmptyFile() throws IOException {
        TaskManager taskManager = new FileBackedTaskManager(new InMemoryHistoryManager(),
                new File("resource/empty.csv"));

        File file = new File("resource/empty.csv");
        String dataFromFile = Files.readString(file.toPath());
        String[] splitData = dataFromFile.split("\n");
        ArrayList<String> check = new ArrayList<>(Arrays.asList("TASK", "EPIC", "SUBTASK"));
        int fileTaskSize = 0;
        for (String value : splitData) {
            String[] data = value.split(",");
            if (data.length > 1) {
                if (check.contains(data[1])) {
                    fileTaskSize++;
                }
            }
        }
        assertEquals(fileTaskSize, taskManager.getAllTask().size());
    }

    @Test
    void loadFilledFile() {
        TaskManager managerWithFilledFile = FileBackedTaskManager
                .loadFromFile(new File("resource/filledFile.csv"));
        assertNotNull(managerWithFilledFile.getAllTask());
    }

    @Test
    void saveMultipleFiles() throws IOException {
        TaskManager taskManager = new FileBackedTaskManager(new InMemoryHistoryManager(),
                new File("resource/test.csv"));

        SingleTask singleTask1 = new SingleTask("Single task1","ST1");
        taskManager.createNewSingleTask(singleTask1);

        EpicTask eT1 = new EpicTask("Epic task 1","1 subtask");
        taskManager.creatNewEpicTask(eT1);

        SubTask sT1e1 = new SubTask("Sub task 1","1_1 subtask", eT1);
        taskManager.createNewSubTask(sT1e1);

        File file = new File("resource/test.csv");
        String dataFromFile = Files.readString(file.toPath());
        String[] splitData = dataFromFile.split("\n");
        ArrayList<String> check = new ArrayList<>(Arrays.asList("TASK", "EPIC", "SUBTASK"));
        int fileTaskSize = 0;
        for (String value : splitData) {
            String[] data = value.split(",");
            if (data.length > 1) {
                if (check.contains(data[1])) {
                    fileTaskSize++;
                }
            }
        }
        assertEquals(fileTaskSize, taskManager.getAllTask().size());
    }
}
