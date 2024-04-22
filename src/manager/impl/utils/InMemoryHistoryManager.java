package manager.impl.utils;

import manager.abstractClass.Task;
import manager.interfaces.HistoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {

    private final CustomLinkedList customLinkedList;
    private final Map<Integer, Node> map;


    public InMemoryHistoryManager() {
        this.customLinkedList = new CustomLinkedList();
        this.map = new HashMap<>();
    }

    @Override
    public void addHistoryId(Task task){
        if ((map.containsKey(task.getIdTask())) && (customLinkedList.tail.task.getIdTask() == task.getIdTask())){
            return;
        } else if (map.containsKey(task.getIdTask())){
            customLinkedList.removeNode(map.get(task.getIdTask()));
            map.put(task.getIdTask(), customLinkedList.linkLast(task));
        } else if ((customLinkedList.head == customLinkedList.tail) && customLinkedList.head != null ) {
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

final class CustomLinkedList{
    Node head;
    Node tail;

    public CustomLinkedList() {
        head = null;
        tail = null;
    }

     Node linkLast(Task task){
        if (head == null){
            Node newNode = new Node(null, task, null);
            head = newNode;
            tail = newNode;
            return newNode;
        }
        Node newNode = new Node(tail, task, null);
        tail.next = newNode;
        tail = newNode;
        return newNode;
    }

     ArrayList<Task> getTasks(){
        ArrayList<Task> toReturn = new ArrayList<>();

        Node current = head;
        while (current != null){
            toReturn.add(current.task);
            current = current.next;
        }

        return toReturn;
    }

    void removeNode(Node nodeToRemove){
        //prev - до нее, next - после
        if(nodeToRemove.next == null && nodeToRemove.prev == null){
            //Один элемент в списке
            tail = null;
            head = null;
        } else if(nodeToRemove.prev == null){
            //голова
            head = nodeToRemove.next;

            nodeToRemove.next.prev = null;
            nodeToRemove.next = null;

        }  else if (nodeToRemove.next == null ){
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
