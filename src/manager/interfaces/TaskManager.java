package manager.interfaces;

import manager.abstractClass.Task;
import manager.impl.Status;
import manager.impl.TypeOfTask;
import manager.impl.EpicTask;
import manager.impl.SingleTask;
import manager.impl.SubTask;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
     SingleTask createNewSingleTask(String name, String description);

     void addHistoryId(Task task);

     ArrayList<Task> getHistory();

     SubTask createNewSubTask(String name, String description, EpicTask epicTask);

     EpicTask creatNewEpicTask(String name, String description);

     void saveNewTask(Task task);

     void isEpicDone(EpicTask epicTask);

     SingleTask updateSingleTask(Task task,String name,String description, Status status);

     SubTask updateSubTask(Task task, String name,String description, EpicTask epicTask, Status status);

     EpicTask updateEpicTask(Task task,String name,String description);

     List<Task> getAllTask();

     List<Task> getAllTaskByType(TypeOfTask typeOfTask);

     void deleteAllTask();

     void deleteTaskById(Integer id);

     Task findTaskById(Integer id);
}
