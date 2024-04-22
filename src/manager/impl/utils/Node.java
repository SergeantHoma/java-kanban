package manager.impl.utils;

import manager.abstractClass.Task;

final class Node{
    final Task task;
    Node prev;
    Node next;

    public Node(Node prev, Task task, Node next) {
        this.prev = prev;
        this.task = task;
        this.next = next;
    }

    public Task getTaskId() {
        return task;
    }
}