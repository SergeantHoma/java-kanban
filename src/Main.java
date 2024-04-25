import manager.abstractClass.Task;
import manager.abstractClass.Managers;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SingleTask;
import manager.impl.tasks.SubTask;
import manager.interfaces.TaskManager;


import static manager.impl.enums.TypeOfTask.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        SingleTask singleTask1 = new SingleTask("Single task1","ST1");
        taskManager.createNewSingleTask(singleTask1);

        EpicTask eT1 = new EpicTask("Epic task 1","1 subtask");
        taskManager.creatNewEpicTask(eT1);

        EpicTask eT2 = new EpicTask("Epic task 2","2 subtask");
        taskManager.creatNewEpicTask(eT2);

        SubTask sT1e1 = new SubTask("Sub task 1","1_1 subtask", eT1);
        taskManager.createNewSubTask(sT1e1);
        SubTask sT2e1 = new SubTask("Sub task 2","1_2 subtask", eT1);
        taskManager.createNewSubTask(sT2e1);
        SubTask sT1e2 = new SubTask("Sub task 3","2_1 subtask", eT2);
        taskManager.createNewSubTask(sT1e2);
        taskManager.findTaskById(1);
        printAllTasks(taskManager);
    }


    private static void printAllTasks(manager.interfaces.TaskManager taskManager) {
        System.out.println("Задачи:");
        for (Task task : taskManager.getAllTaskByType(TASK)) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : taskManager.getAllTaskByType(EPIC)) {
            System.out.println(epic);

        }
        System.out.println("Подзадачи:");
        for (Task subtask : taskManager.getAllTaskByType(SUBTASK)) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }
    }
}
