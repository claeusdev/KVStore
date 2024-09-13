package dev.nmanu;

import java.util.HashMap;
import java.util.LinkedList;

class Crache<K, V> extends Cache<K, V> {
    private EvictionPolicy policy;

    private HashMap<K, V> store;
    private LRU<K> evictionList;

    Crache() {
        store = new HashMap<>();
        evictionList = new LRU<>();
        policy = EvictionPolicy.LRU;
    }

    Crache(EvictionPolicy policy) {
        store = new HashMap<>();
        evictionList = new LRU<>();
        this.policy = policy;
    }

    public HashMap<K, V> getStore() {
        return store;
    }

    @Override
    public V get(K key) {
        V value = store.get(key);
        if (value != null) {
            System.out.println("HIT: " + key + " : " + evictionList.find(key));
            evictionList.promoteToRecent(evictionList.find(key));
        }

        return value;
    }

    @Override
    void put(K key, V value) {
        evictionList.add(key);
        store.put(key, value);
    }

    @Override
    void evict() {
        LRU<K>.Node node = evictionList.removeFirst();
        if (node != null && node.getData() != null) {
            K keyToRemove = node.getData();
            store.remove(keyToRemove);
        }
    }

    LRU<K> getEvictionList() {
        return evictionList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var entry : store.entrySet()) {
            sb.append(entry.getKey() + " : " + entry.getValue() + "\n");
        }
        return sb.toString();
    }
}
