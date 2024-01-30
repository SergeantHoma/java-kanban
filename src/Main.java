import tasks.*;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        System.out.println("Создание тасков:");

        SingleTask sT1 = taskManager.createNewSingleTask("Single task", "N1");
        SingleTask sT2 = taskManager.createNewSingleTask("Single task", "N2");


        EpicTask eT1 = taskManager.creatNewEpicTask("Epic task 1","2 subtask" );
        SubTask subT1_1 = taskManager.createNewSubTask("Sub task 1","1_1 epic", eT1);
        SubTask subT1_2 = taskManager.createNewSubTask("Sub task 2","1_2 epic", eT1);

        EpicTask eT2 = taskManager.creatNewEpicTask("Epic task 2","1 subtask" );
        SubTask subT2_1 = taskManager.createNewSubTask("Sub task 2","2_1 epic",eT2);

        System.out.println(taskManager.getAllTask());

        System.out.println("\nОбновление тасков:");

        taskManager.updateSingleTask(sT1,"Default","default", Status.IN_PROGRESS);
        taskManager.updateSubTask(subT1_1,"2","test",eT1,Status.IN_PROGRESS);

        taskManager.updateSubTask(subT2_1,"2","test",eT2,Status.DONE);
        System.out.println(taskManager.getAllTaskByType(TypeOfTask.EPIC_TASK));

        System.out.println("\nУдаление сабтаска, с изменением в епик таске:");

        taskManager.updateSubTask(subT1_1,"2","test",eT1,Status.DONE);
        taskManager.deleteTaskById(5);
        System.out.println(taskManager.getAllTask());



    }
}
