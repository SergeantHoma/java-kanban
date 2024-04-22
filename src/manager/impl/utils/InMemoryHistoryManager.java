package manager.impl.utils;

import manager.abstractClass.Task;
import manager.interfaces.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList customLinkedList;
    private final Map<Integer, Node> map;


    public InMemoryHistoryManager() {
        this.customLinkedList = new CustomLinkedList();
        this.map = new HashMap<>();
    }

    @Override
    public void addHistoryId(Task task) {
         if (map.containsKey(task.getIdTask())) {
            customLinkedList.removeNode(map.get(task.getIdTask()));
            map.put(task.getIdTask(), customLinkedList.linkLast(task));
        } else if ((customLinkedList.head == customLinkedList.tail) && customLinkedList.head != null) {
            Node newNode = customLinkedList.linkLast(task);
            Node newHeadNode = new Node(null,newNode.prev.task,newNode);
            map.put(newHeadNode.task.getIdTask(),newHeadNode);
            map.put(task.getIdTask(), newNode);
        } else {
            Node newNode = customLinkedList.linkLast(task);
            map.put(task.getIdTask(), newNode);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return customLinkedList.getTasks();
    }

    @Override
    public void remove(int id) {
        Node nodeToRemove = map.get(id);

        if (nodeToRemove == null) {
            return;
        }
        map.remove(id);
        customLinkedList.removeNode(nodeToRemove);
    }
}