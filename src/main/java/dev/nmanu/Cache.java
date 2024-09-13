package dev.nmanu;

abstract class Cache<K, V> {
    abstract V get(K key);
    abstract void put(K key, V value);
    abstract void evict();
}
