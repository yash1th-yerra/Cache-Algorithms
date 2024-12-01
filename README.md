
# LFU Cache (Least Frequently Used Cache)

## Table of Contents

1.  [What is LFU Cache?](#what-is-lfu-cache)
2.  [How Does LFU Cache Work?](#how-does-lfu-cache-work)
3.  [Why is LFU Cache Needed?](#why-is-lfu-cache-needed)
4.  [Where is LFU Cache Used?](#where-is-lfu-cache-used)
5.  [Real-World Examples](#real-world-examples)
6.  [Implementation Details](#implementation-details)
7.  [How to Use the Code](#how-to-use-the-code)

----------

## What is LFU Cache?

LFU (Least Frequently Used) Cache is a caching algorithm that removes the least frequently accessed items to make space for new entries when the cache reaches its capacity. Unlike other caching mechanisms like LRU (Least Recently Used), LFU prioritizes items based on their usage frequency.

### Key Characteristics:

-   **Frequency-Based Eviction**: Items used less often are evicted first.
-   **Dynamic Frequency Updates**: Accessing an item increases its usage frequency.
-   **Efficient Operations**: Optimized to perform `get` and `put` operations in near-constant time.

----------

## How Does LFU Cache Work?

1.  **Structure**:
    
    -   A map (`cache`) stores key-value pairs and links them to nodes that keep track of the frequency of access.
    -   A frequency map (`frequencyMap`) groups nodes by their access frequency.
2.  **Operations**:
    
    -   **`get(key)`**:
        -   If the key exists in the cache, return the value and increase the frequency of access.
        -   If the key does not exist, return `-1`.
    -   **`put(key, value)`**:
        -   If the key already exists, update its value and frequency.
        -   If the key does not exist:
            -   If the cache is full, evict the least frequently used item.
            -   Insert the new item with a frequency of `1`.
3.  **Eviction Policy**:
    
    -   If two items have the same frequency, the Least Recently Used (LRU) item among them is evicted.

----------

## Why is LFU Cache Needed?

Modern applications often need to manage limited resources, like memory or disk space, efficiently. LFU Cache provides a mechanism to ensure that frequently accessed data stays in memory while less important data is evicted.

### Key Benefits:

-   **Optimal for Repeated Access Patterns**: Keeps frequently used data readily available.
-   **Improved Performance**: Reduces latency by avoiding unnecessary computations or data retrievals.
-   **Minimized Resource Usage**: Makes efficient use of limited cache space.

----------

## Where is LFU Cache Used?

### Common Use Cases:

1.  **Database Query Caching**:
    
    -   Frequently queried data is cached to minimize expensive database lookups.
2.  **Web Browsers**:
    
    -   Store frequently accessed web pages or assets to reduce load times.
3.  **Content Delivery Networks (CDNs)**:
    
    -   Cache popular content to reduce server load and improve response times.
4.  **Machine Learning Models**:
    
    -   Cache frequently used model parameters or results for large-scale computations.

----------

## Real-World Examples

1.  **YouTube or Netflix**:
    
    -   Frequently watched videos or series can be cached on edge servers close to users.
2.  **E-commerce Websites**:
    
    -   Frequently viewed product details or recommendations are cached to provide faster page loads.
3.  **Social Media Platforms**:
    
    -   Popular posts or trending topics are cached for quicker rendering.
4.  **Online Gaming**:
    
    -   Frequently accessed assets like textures or maps are cached to minimize load times.

----------

## Implementation Details

The LFU Cache implementation in this repository uses a combination of HashMaps and Doubly Linked Lists to achieve optimal time complexity.

### Data Structures:

1.  **HashMap (`cache`)**:
    -   Maps keys to DLL nodes for O(1) access to cache elements.
2.  **HashMap (`frequencyMap`)**:
    -   Maps frequency values to Doubly Linked Lists that store all items with the same frequency.
3.  **Doubly Linked List**:
    -   Efficiently maintains insertion and removal of nodes.

### Complexity:

-   **`get`**: O(1)
-   **`put`**: O(1)
-   **Space**: O(n), where `n` is the cache capacity.

----------

## How to Use the Code

### Code Structure:

-   **LFUCache**: Main class for the LFU cache.
-   **DLLNode**: Represents a single node in the Doubly Linked List.
-   **DoubleLinkedList**: Manages the list of nodes for each frequency.

### Initialization:

java

Copy code

`LFUCache cache = new LFUCache(capacity);` 

### Supported Operations:

1.  **`get(int key)`**:
    
    -   Retrieve the value associated with the key.
   
    
    `int value = cache.get(key);` 
    
2.  **`put(int key, int value)`**:
    
    -   Insert a new key-value pair or update an existing one.
    
  
    
    `cache.put(key, value);` 
    

### Example Usage:



    LFUCache cache = new LFUCache(2); // Capacity is 2
     cache.put(1, 10); // Cache: {1=10} 
     cache.put(2, 20); // Cache: {1=10, 2=20}
      System.out.println(cache.get(1)); // Output: 10 (Increases frequency of key 1) cache.put(3, 30); // Evicts key 2, Cache: {1=10, 3=30}
       System.out.println(cache.get(2)); // Output: -1 (Key 2 is evicted) System.out.println(cache.get(3)); // Output: 3


----------

## Conclusion

LFU Cache is a powerful caching mechanism that ensures frequently used data remains available, improving application performance and resource utilization. Its use in real-world applications like web caching, databases, and CDNs highlights its importance in optimizing modern systems. The provided Java implementation offers an efficient way to integrate LFU caching into your projects.
