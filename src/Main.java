import manager.abstractClass.Task;
import manager.impl.*;
import manager.impl.TaskManager;

import static manager.impl.TypeOfTask.*;

public class Main {
    public static void main(String[] args) {
        manager.interfaces.TaskManager taskManager = TaskManager.getDefault();

        SingleTask singleTask1 = new SingleTask("Single task1","ST1");
        taskManager.createNewSingleTask(singleTask1);

        EpicTask eT1 = new EpicTask ("Epic task 1","1 subtask" );
        taskManager.creatNewEpicTask(eT1);

        EpicTask eT2 = new EpicTask ("Epic task 2","2 subtask" );
        taskManager.creatNewEpicTask(eT2);

        SubTask sT1_1 = new SubTask ("Sub task 1","1_1 subtask", eT1);
        taskManager.createNewSubTask(sT1_1);
        SubTask sT1_2 = new SubTask("Sub task 2","1_2 subtask", eT1);
        taskManager.createNewSubTask(sT1_2);
        SubTask sT2_1 = new SubTask("Sub task 3","2_1 subtask", eT2);
        taskManager.createNewSubTask(sT2_1);

        taskManager.updateSubTask(sT1_2,"1_3 subtask","2_1 subtask", eT1,Status.DONE);


        System.out.println(taskManager.findTaskById(singleTask1.getIdTask()));
        taskManager.updateSingleTask(singleTask1,"3","4",Status.IN_PROGRESS);
        System.out.println(taskManager.findTaskById(singleTask1.getIdTask()));
        taskManager.updateSingleTask(singleTask1,"1","2",Status.DONE);

        printAllTasks(taskManager);
    }


    private static void printAllTasks(manager.interfaces.TaskManager taskManager) {

        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTaskByType(SINGLE_TASK)) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : taskManager.getAllTaskByType(EPIC_TASK)) {
            System.out.println(epic);

        }
        System.out.println("Подзадачи:");
        for (Task subtask : taskManager.getAllTaskByType(SUB_TASK)) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}
