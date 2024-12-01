import java.util.HashMap;
import java.util.Map;

class LFUCache {

   
     // Map to track nodes by frequency. Each frequency maps to a DoubleLinkedList of nodes.
     
    Map<Integer, DoubleLinkedList> frequencyMap;

     // Cache to store key-value pairs mapped to their corresponding DLLNode for fast access.
    Map<Integer, DLLNode> cache;

     // Maximum capacity of the cache.
    final int capacity;


     // Current size of the cache.
     
    int curSize;
   
    // Minimum frequency of any node in the cache, used for LFU eviction.
   
    int minFrequency;

    /**
     * Constructor to initialize LFUCache with given capacity.
     * @param capacity Maximum capacity of the cache.
     */
    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.frequencyMap = new HashMap<>();
        this.cache = new HashMap<>();
        this.curSize = 0;
        this.minFrequency = 0; 
    }
    
    /**
     * Retrieve the value associated with the given key.
     * @param key The key to search for.
     * @return The value if the key exists in the cache, otherwise -1.
     */
    public int get(int key) {
        if (!cache.containsKey(key)) return -1; // Key not found in cache.
        DLLNode curNode = cache.get(key);
        updateNode(curNode); // Update frequency of the node.
        return curNode.value; // Return the value.
    }
    
    /**
     * Insert a key-value pair into the cache.
     * @param key The key to be inserted.
     * @param value The value associated with the key.
     */
    public void put(int key, int value) {
        if (capacity == 0) return; // If cache capacity is zero, do nothing.

        if (cache.containsKey(key)) {
            // Key exists, update the value and its frequency.
            DLLNode curNode = cache.get(key);
            curNode.value = value; // Update the value.
            updateNode(curNode); // Update the node frequency.
        } else {
            curSize++; // Increment current cache size.

            // If cache exceeds capacity, remove the least frequently used node.
            if (curSize > capacity) {
                DoubleLinkedList minFreqList = frequencyMap.get(minFrequency);
                cache.remove(minFreqList.tail.prev.key); // Remove from cache map.
                minFreqList.removeNode(minFreqList.tail.prev); // Remove node from list.
                curSize--; // Decrement size.
            }

            // Add new node to cache.
            minFrequency = 1; // Reset minimum frequency to 1.
            DLLNode newNode = new DLLNode(key, value);
            DoubleLinkedList newList = frequencyMap.getOrDefault(1, new DoubleLinkedList());
            newList.addNode(newNode); // Add new node to frequency list.
            frequencyMap.put(1, newList); // Update frequency map.
            cache.put(key, newNode); // Add to cache map.
        }
    }

    /**
     * Update the frequency of a node when it is accessed or updated.
     * @param curNode The node whose frequency needs to be updated.
     */
    private void updateNode(DLLNode curNode) {
        int curFrequency = curNode.frequency; // Get current frequency.
        DoubleLinkedList curList = frequencyMap.get(curFrequency);
        curList.removeNode(curNode); // Remove node from current frequency list.

        // If this was the last node of the current minimum frequency list, update minFrequency.
        if (curFrequency == minFrequency && curList.listSize == 0) {
            minFrequency++;
        }

        curNode.frequency++; // Increment node's frequency.

        // Add node to the new frequency list.
        DoubleLinkedList newList = frequencyMap.getOrDefault(curNode.frequency, new DoubleLinkedList());
        newList.addNode(curNode);
        frequencyMap.put(curNode.frequency, newList); // Update frequency map.
    }

    /**
     * Class representing a node in the doubly linked list.
     */
    class DLLNode {
        int key, value, frequency;
        DLLNode prev, next;

        /**
         * Constructor to initialize a node with key and value. Frequency is set to 1.
         * @param key The key of the node.
         * @param value The value of the node.
         */
        public DLLNode(int key, int value) {
            this.key = key;
            this.value = value;
            this.frequency = 1; // Frequency starts at 1.
        }
    }

    /**
     * Class representing a doubly linked list.
     */
    class DoubleLinkedList {
        DLLNode head, tail;
        int listSize;

        /**
         * Constructor to initialize an empty doubly linked list.
         */
        public DoubleLinkedList() {
            head = new DLLNode(0, 0); // Dummy head node.
            tail = new DLLNode(0, 0); // Dummy tail node.
            head.next = tail;
            tail.prev = head;
            listSize = 0; // Initially, the list is empty.
        }

        /**
         * Remove a node from the doubly linked list.
         * @param curNode The node to be removed.
         */
        public void removeNode(DLLNode curNode) {
            DLLNode prevNode = curNode.prev;
            DLLNode nextNode = curNode.next;
            prevNode.next = nextNode; // Update pointers.
            nextNode.prev = prevNode;
            listSize--; // Decrement size.
        }

        /**
         * Add a node to the front (most recently used position) of the doubly linked list.
         * @param curNode The node to be added.
         */
        public void addNode(DLLNode curNode) {
            DLLNode nextNode = head.next;
            curNode.next = nextNode; // Update pointers.
            curNode.prev = head;
            head.next = curNode;
            nextNode.prev = curNode;
            listSize++; // Increment size.
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key, value);
 */
