package dev.nmanu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedList<K, V> implements Iterable<LinkedList<K, V>.Node>{
    private Node head;
    private Node tail;
    private int size;

    @Override
    public Iterator<Node> iterator() {
        return new LinkedListIterator();
    }

    // Extracted Iterator class
    private class LinkedListIterator implements Iterator<Node> {
        private Node current = head.getNext();

        @Override
        public boolean hasNext() {
            return current != null && current != tail;
        }

        @Override
        public Node next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Node temp = current;
            current = current.getNext();
            return temp;
        }
    }

    class Node {
        private HashMap<K, V> data;
        private Node next;
        private Node prev;

        public Node(){
            data = new HashMap<>();
        }

        public Node(K key, V value) {
            data = new HashMap<>();
            data.put(key, value);
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public HashMap<K, V> getData() {
            return data;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (var entry : data.entrySet()){
                sb.append(entry.getKey() + " : " + entry.getValue() + "\n");
            }
            return sb.toString();
        }
    }

    LinkedList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.prev = head;
    }

    public Node getFirst() {
        if (isEmpty()) {
            return null;
        }
        return head.getNext();
    }

    public Node getLast() {
        if (isEmpty()) {
            return null;
        }
        return tail.getPrev();
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    void add(K key, V value) {
        Node newNode = new Node(key, value);
        tail.prev.next = newNode;
        newNode.prev = tail.prev;
        tail.prev = newNode;
        newNode.next = tail;
        size++;
    }

    Node find(K key) {
        LinkedList.Node node = head;
        while (node != null) {
            if (node.data.containsKey(key)) {
                return node;
            }
            node = node.next;
        }
        return null;
    }

    Node removeFirst() {
        if (isEmpty()) {
            return null;
        }
        Node node = head.next;
        head.next = node.next;
        node.next.prev = head;
        size--;
        return head.next;
    }

    void promoteToRecent(Node node) {
        if (node == null || node == tail.prev) {
            return;
        }
        // Remove node from its current position
        node.prev.next = node.next;
        node.next.prev = node.prev;
        // Attach node at the end
        tail.prev.next = node;
        node.prev = tail.prev;
        node.next = tail;
        tail.prev = node;

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        while (head.next != tail) {
            sb.append(head.next.toString());
            head = head.next;
        }
        return sb.toString();
    }
}
