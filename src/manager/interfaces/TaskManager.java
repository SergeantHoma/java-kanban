package manager.interfaces;

import manager.abstractClass.Task;
import manager.impl.enums.Status;
import manager.impl.enums.TypeOfTask;
import manager.impl.tasks.EpicTask;
import manager.impl.tasks.SingleTask;
import manager.impl.tasks.SubTask;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
     void createNewSingleTask(SingleTask singleTask);

     void addHistoryId(Task task) throws CloneNotSupportedException;

     ArrayList<Task> getHistory();

     void createNewSubTask(SubTask subTask);

     void creatNewEpicTask(EpicTask epicTask);

     void saveNewTask(Task task);

     void isEpicDone(EpicTask epicTask);

     void updateSingleTask(SingleTask singleTask,String name,String description, Status status);

     void updateSubTask(SubTask subTask, String name,String description, Status status);

     void updateEpicTask(EpicTask epicTask,String name,String description);

     List<Task> getAllTask();

     List<Task> getAllTaskByType(TypeOfTask typeOfTask);

     void deleteAllTaskByType(TypeOfTask typeOfTask);

     void deleteTaskById(Integer id);

     Task findTaskById(Integer id);
}
