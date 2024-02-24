import manager.abstractClass.Task;
import manager.impl.*;
import manager.impl.TaskManager;

import static manager.impl.TypeOfTask.*;

public class Main {
    public static void main(String[] args) {
        manager.interfaces.TaskManager taskManager = TaskManager.getDefault();

        SingleTask singleTask1 = taskManager.createNewSingleTask("Single task1","ST1");

        EpicTask eT1 = taskManager.creatNewEpicTask("Epic task 1","1 subtask" );
        EpicTask eT2 = taskManager.creatNewEpicTask("Epic task 2","2 subtask" );

        SubTask sT1_1 = taskManager.createNewSubTask("Sub task 1","1_1 subtask", eT1);
        SubTask sT1_2 = taskManager.createNewSubTask("Sub task 2","1_2 subtask", eT1);
        SubTask sT1_3 = taskManager.createNewSubTask("Sub task 3","2_1 subtask", eT2);
        sT1_2 = taskManager.updateSubTask(sT1_2,"1_3 subtask","2_1 subtask", eT1,Status.DONE);

        printAllTasks(taskManager);
    }


    private static void printAllTasks(manager.interfaces.TaskManager taskManager) {

        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTask()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : taskManager.getAllTaskByType(EPIC_TASK)) {
            System.out.println(epic);

        }
        System.out.println("Подзадачи:");
        for (Task subtask : taskManager.getAllTaskByType(SINGLE_TASK)) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}
