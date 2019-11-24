package ua.edu.ucu.utils;

import java.util.Arrays;

public class ImmutableLinkedList implements ImmutableList {

    private static final class Node {
        private Object val;
        private Node prev;
        private Node next;

        private Node(Object val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }

        private Node(Object val) {
            this(val, null, null);
        }

    }

    private int size;
    private Node head;
    private Node tail;

    public ImmutableLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private ImmutableLinkedList(Node head, Node tail, int size) {
        this.head = head;
        this.tail = tail;
        this.size = size;
    }

    public ImmutableLinkedList(Object[] arr) {
        this(null, null, arr.length);
        if (arr.length >= 1) {
            Node node = new Node(arr[0]);
            this.head = node;

            Node curNode = node;
            for (int i = 1; i < arr.length; i++) {
                curNode = new Node(arr[i], node, null);
                node.next = curNode;
                node = curNode;
            }

            this.tail = curNode;
        }
    }

    // робить копію зв'язного списку
    private static ImmutableLinkedList copyOf(ImmutableLinkedList list) { 
        return new ImmutableLinkedList(list.toArray());
    }

    //додає елемент у початок зв'язаного списку
    public ImmutableLinkedList addFirst(Object e) {
        if (this.size == 0) {
            Node node = new Node(e);
            return new ImmutableLinkedList(node, node, 1);
        }
        else {
            ImmutableLinkedList newList = ImmutableLinkedList.copyOf(this);
            newList.head = new Node(e, null, newList.head);
            newList.head.next.prev = newList.head;
            newList.size += 1;
            return newList;
        }
    }

    // додає елемент у кінець зв'язаного списку
    public ImmutableLinkedList addLast(Object e) {
        if (this.size == 0) {
            Node node = new Node(e);
            return new ImmutableLinkedList(node, node, 1);
        }
        else {
            ImmutableLinkedList newList = ImmutableLinkedList.copyOf(this);
            newList.tail = new Node(e, newList.tail, null);
            newList.tail.prev.next = newList.tail;
            newList.size += 1;
            return newList;
        }
    }

    // повератє перший елемнт зв'язного списку
    public Object getFirst() {
        if (this.size == 0) {
            throw new IndexOutOfBoundsException();
        }
        return  this.head.val;
    }

    // повертає останній елемент зв'язного списку
    public Object getLast() {
        if (this.size == 0) {
            throw new IndexOutOfBoundsException();
        }
        return  this.tail.val;
    }

    //видаляє перший елемент
    public ImmutableLinkedList removeFirst() {
        if (this.size == 0) {
            throw new IndexOutOfBoundsException();
        }
        if  (this.size == 1) {
            return new ImmutableLinkedList();
        }
        ImmutableLinkedList newList = ImmutableLinkedList.copyOf(this);
        newList.head = newList.head.next;
        newList.size -= 1;
        return newList;
    }

    //видаляє останній елемент
    public ImmutableLinkedList removeLast() {
        if (this.size == 0) {
            throw new IndexOutOfBoundsException();
        }
        if  (this.size == 1) {
            return new ImmutableLinkedList();
        }
        ImmutableLinkedList newList = ImmutableLinkedList.copyOf(this);
        newList.size -= 1;
        newList.tail = newList.tail.prev;
        return newList;
    }

    //додає елемент у кінець колекції
    @Override
    public ImmutableLinkedList add(Object e) {
        return this.add(this.size, e);
    }

    //додає елемент до колекції за індексом, та кидає виключну ситуацію,
    // якщо індекс виходить за межі колекції
    @Override
    public ImmutableLinkedList add(int index, Object e) {
        return this.addAll(index, new Object[]{e});
    }

    //додає масив елементів у кінець колекції
    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        return this.addAll(this.size, c);
    }

    // додає масив елементів починаючи з зазначеного індекса, та кидає
    // виключну ситуацію, якщо індекс виходить за межі колекції
    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        Object[] arr = this.toArray();
        Object[] newArr = new Object[this.size + c.length];
        System.arraycopy(arr, 0, newArr, 0, index);
        System.arraycopy(c, 0, newArr, index, c.length);
        if (index < this.size) {
            System.arraycopy(arr, index, newArr, index  + c.length,
                    this.size - index);
        }
        return new ImmutableLinkedList(newArr);
    }

    //повертає елемент за індексом, та кидає виключну ситуацію, якщо індекс
    // виходить за межі колекції
    @Override
    public Object get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = this.head;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.val;
    }

    //видаляє елемент за індексом, та кидає виключну ситуацію, якщо індекс
    // виходить за межі колекції
    @Override
    public ImmutableLinkedList remove(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Object[] arr = this.toArray();
        Object[] newArr = new Object[this.size-1];
        System.arraycopy(arr, 0, newArr, 0, index);
        System.arraycopy(arr, index + 1, newArr, index,
                this.size - index - 1);
        return new ImmutableLinkedList(newArr);
    }

    //змінює значення елементу за індексом, та кидає виключну ситуацію,
    // якщо індекс виходить за межі колекції
    @Override
        public ImmutableLinkedList set(int index, Object e) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
        Object[] arr = this.toArray();
        Object[] newArr = new Object[this.size];
        System.arraycopy(arr, 0, newArr, 0, index);
        newArr[index] = e;
        System.arraycopy(arr, index + 1, newArr, index + 1,
                this.size - index - 1);
        return new ImmutableLinkedList(newArr);
    }

    //шукає індекс елемента (повертає індекс першого який знайшов,
    // або -1 у випадку відсутності)
    @Override
    public int indexOf(Object e) {
        Node node = this.head;
        for (int i = 0; i < this.size; i++) {
            if (node.val.equals(e)) {
                return i;
            }
            node = node.next;
        }
        return -1;
    }

    //розмір колекції
    @Override
    public int size() {
        return this.size;
    }

    //очищує вміст колекції
    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    //якщо у колеції нема елементів то повертає true
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    //перетворює колекцію до масиву обєктів
    @Override
    public Object[] toArray() {
        Object[] arr = new Object[this.size];
        Node node = this.head;
        for (int i = 0; i < this.size; i++) {
            arr[i] = node.val;
            node = node.next;
        }
        return arr;
    }

    //повертає рядок, де через кому відображаютсься елементи колекції
    @Override
    public String toString() {
        return Arrays.toString(this.toArray());
    }
}
