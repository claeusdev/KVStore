package dev.nmanu;

import java.util.HashMap;

class Crache<K, V> extends Cache<K, V> {
    private EvictionPolicy policy;

    private HashMap<K, V> store;
    private LinkedList<K, V> evictionList;

    Crache() {
        store = new HashMap<>();
        evictionList = new LinkedList<>();
        policy = EvictionPolicy.LRU;
    }

    Crache(EvictionPolicy policy) {
        store = new HashMap<>();
        evictionList = new LinkedList<>();
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
        evictionList.add(key, value);
        store.put(key, value);
    }

    @Override
    void evict() {
        LinkedList<K, V>.Node node = evictionList.removeFirst();
        if (node != null && node.getData() != null) {
            K keyToRemove = node.getData().keySet().iterator().next();
            store.remove(keyToRemove);
        }
    }

    LinkedList<K, V> getEvictionList() {
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
