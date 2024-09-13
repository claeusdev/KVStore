package dev.nmanu;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

class LRU<K> implements Iterable<LRU<K>.Node>{
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
        private K data;
        private Node next;
        private Node prev;

        public Node() {
            data = null;
        }

        public Node(K key) {
            data = key;
        }

        public Node getNext() {
            return next;
        }

        public Node getPrev() {
            return prev;
        }

        public K getData() {
            return data;
        }
    }

    LRU() {
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

    void add(K key) {
        Node newNode = new Node(key);
        tail.prev.next = newNode;
        newNode.prev = tail.prev;
        tail.prev = newNode;
        newNode.next = tail;
        size++;
    }

    Node find(K key) {
        Node currentNode = head;
        while (currentNode.getNext() != null) {
            if (isKeyMatching(currentNode, key)) {
                return currentNode;
            }
            currentNode = currentNode.getNext();
        }
        return null;
    }

    private boolean isKeyMatching(Node node, K key) {
        return node.getData() != null && node.getData().equals(key);
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
