package manager.impl.utils;

import manager.abstractClass.Task;

import java.util.ArrayList;

class CustomLinkedList {
    Node head;
    Node tail;

    public CustomLinkedList() {
        head = null;
        tail = null;
    }

    Node linkLast(Task task) {
        if (head == null) {
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

    ArrayList<Task> getTasks() {
        ArrayList<Task> toReturn = new ArrayList<>();

        Node current = head;
        while (current != null) {
            toReturn.add(current.task);
            current = current.next;
        }

        return toReturn;
    }

    void removeNode(Node nodeToRemove) {
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
