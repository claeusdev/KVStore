package dev.nmanu;

import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        Crache<String, Integer> KVStore = new Crache<>();
        KVStore.put("a", 1);
        KVStore.put("b", 2);
        KVStore.put("c", 3);
        int last = KVStore.get("a");
        int next = KVStore.get("b");
        KVStore.evict();

        LRU<String> a = KVStore.getEvictionList();
        Iterator<LRU<String>.Node> it = a.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}