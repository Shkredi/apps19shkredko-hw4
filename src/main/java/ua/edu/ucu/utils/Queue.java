package ua.edu.ucu.utils;

public class Queue {

    private ImmutableLinkedList list;

    public Queue() {
        this.list = new ImmutableLinkedList();
    }

    // Returns the object at the beginning of the Queue without removing it
    public Object peek() {
        if (this.isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        return this.list.getFirst();
    }

    // Removes and returns the object at the beginning of the Queue.
    public Object dequeue() {
        if (this.isEmpty()) {
            throw new IndexOutOfBoundsException();
        }
        Object e = this.list.getFirst();
        this.list = this.list.removeFirst();
        return e;
    }

    // Adds an object to the end of the Queue.
    public void enqueue(Object e) {
        this.list = this.list.addLast(e);
    }

    public boolean isEmpty() {
        return this.list.size() == 0;
    }

}
