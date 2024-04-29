import manager.abstractClass.Task;
import manager.abstractClass.Managers;
import manager.impl.enums.Status;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SingleTask;
import manager.impl.tasks.SubTask;
import manager.interfaces.TaskManager;


import static manager.impl.enums.TypeOfTask.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        SingleTask singleTask1 = new SingleTask("Single task1","ST1");
        singleTask1.setDuration(68);
        singleTask1.setStartTime("22.02.2023 22:22");
        taskManager.createNewSingleTask(singleTask1);

        SingleTask singleTask2 = new SingleTask("Single task2","ST2");
        singleTask2.setDuration(12);
        singleTask2.setStartTime("22.02.2023 22:22");
        taskManager.createNewSingleTask(singleTask2);

        System.out.println(singleTask2);

        EpicTask eT1 = new EpicTask("Epic task 1","1 subtask");
        taskManager.creatNewEpicTask(eT1);

        SubTask sT1e1 = new SubTask("Sub task 1","1_1 subtask", eT1);
        sT1e1.setDuration(6);
        sT1e1.setStartTime("22.02.2023 22:01");
        taskManager.createNewSubTask(sT1e1);
        SubTask sT2e1 = new SubTask("Sub task 2","1_2 subtask", eT1);
        sT2e1.setDuration(7);
        sT2e1.setStartTime("23.02.2023 22:01");
        taskManager.createNewSubTask(sT2e1);

        System.out.println(taskManager.getPrioritizedTasks());

        taskManager.updateSingleTask(singleTask2,"s","ex",Status.IN_PROGRESS);
        taskManager.updateSubTask(sT2e1,"ds","dsa", Status.IN_PROGRESS);

        System.out.println(taskManager.getPrioritizedTasks());
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
