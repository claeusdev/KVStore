# Crache

`Crache` is a simple, generic caching library written in Java. It supports an **LRU (Least Recently Used)** eviction policy out of the box and can be extended to support other eviction policies. The cache provides basic `get`, `put`, and `evict` operations.

## Features
- **Generic Key-Value Storage:** Use any type for keys and values.
- **LRU Eviction Policy:** Automatically evicts the least recently used item when the cache reaches capacity.
- **Extensibility:** Can be configured to support other eviction policies.

## Class Overview
### Crache
The `Crache<K, V>` class is a generic implementation of a cache with the following main components:
- **HashMap for Storage:** Internally uses a `HashMap<K, V>` to store key-value pairs.
- **LRU List for Eviction:** Maintains an eviction list to track and evict least recently used items.
- **Eviction Policy:** Currently supports LRU as the default policy.

### Constructors
- `Crache()`: Initializes the cache with LRU as the default eviction policy.
- `Crache(EvictionPolicy policy)`: Initializes the cache with a specified eviction policy.

### Public Methods
#### `V get(K key)`
Fetches a value associated with the given key.
- **Returns:** The value if it exists in the cache; otherwise, `null`.
- **Side Effect:** Promotes the accessed key to the most recent position in the eviction list if it exists.

#### `void put(K key, V value)`
Inserts or updates the value associated with the key.
- **Effect:** Adds the key to the eviction list and stores the value in the `HashMap`.

#### `void evict()`
Removes the least recently used item from the cache.
- **Effect:** Evicts the first item in the eviction list and removes its corresponding key-value pair from the store.

#### `HashMap<K, V> getStore()`
Returns the internal storage used by the cache.

#### `LRU<K> getEvictionList()`
Returns the eviction list (used internally for LRU eviction).

#### `String toString()`
Provides a string representation of the cache's contents.

## Usage Example
```java
import dev.nmanu.Crache;

public class Main {
    public static void main(String[] args) {
        Crache<String, Integer> cache = new Crache<>();

        // Insert key-value pairs
        cache.put("A", 1);
        cache.put("B", 2);
        cache.put("C", 3);

        // Access elements
        System.out.println("Get A: " + cache.get("A")); // HIT
        System.out.println("Cache state:\n" + cache);

        // Evict least recently used
        cache.evict();
        System.out.println("After eviction:\n" + cache);
    }
}
```

### Output
```
HIT: A : LRU.Node@12345
Get A: 1
Cache state:
A : 1
B : 2
C : 3

After eviction:
B : 2
C : 3
```

## Installation
To use this library, clone the repository and include the `dev.nmanu` package in your project.

```bash
git clone https://github.com/your-username/crache.git
```

## Future Work
- Support for additional eviction policies (e.g., LFU, FIFO).
- Configurable maximum cache size.
- Thread-safe implementation for concurrent environments.

## Contributing
Feel free to fork the repository, submit issues, or create pull requests for improvements and bug fixes.

## License
This project is licensed under the MIT License. See the `LICENSE` file for details.

