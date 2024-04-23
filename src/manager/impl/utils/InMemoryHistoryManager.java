package manager.impl.utils;

import manager.abstractClass.Task;
import manager.interfaces.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private Node head;
    private Node tail;

    private final Map<Integer, Node> map;


    public InMemoryHistoryManager() {
        head = null;
        tail = null;
        this.map = new HashMap<>();
    }

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }

        int id = task.getIdTask();
        Node rem = map.get(id);
        if (rem != null)
            removeNode(rem);

        Node last = linkLast(task);
        map.put(id, last);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        Node nodeToRemove = map.get(id);

        if (nodeToRemove == null) {
            return;
        }
        map.remove(id);
        removeNode(nodeToRemove);
    }

    private Node linkLast(Task task) {
        final Node node = new Node(tail, task,  null);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        return node;
    }

    private ArrayList<Task> getTasks() {
        ArrayList<Task> toReturn = new ArrayList<>();

        Node current = head;
        while (current != null) {
            toReturn.add(current.task);
            current = current.next;
        }

        return toReturn;
    }

    private void removeNode(Node nodeToRemove) {
        //prev - до нее, next - после
        if (nodeToRemove.next == null && nodeToRemove.prev == null) {
            //Один элемент в списке
            tail = null;
            head = null;
        } else if (nodeToRemove.prev == null) {
            //голова
            head = nodeToRemove.next;

            nodeToRemove.next.prev = null;
            nodeToRemove.next = null;

        } else if (nodeToRemove.next == null) {
            //хвост
            tail = nodeToRemove.prev;

            nodeToRemove.prev.next = null;
            nodeToRemove.prev = null;

        } else {
            //тело
            nodeToRemove.prev.next = nodeToRemove.next;
            nodeToRemove.next.prev = nodeToRemove.prev;

            nodeToRemove.next = null;
            nodeToRemove.prev = null;
        }
    }
}