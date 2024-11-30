
import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    class Node {
        int key, value;
        Node prev, next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private final Map<Integer, Node> map;
    private final int capacity;
    private final Node head, tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }
        Node node = map.get(key);
        moveToFront(node); // Move accessed node to the front
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value; // Update value
            moveToFront(node); // Move updated node to the front
        } else {
            if (map.size() == capacity) {
                removeLeastRecentlyUsed(); // Evict the least recently used item
            }
            Node newNode = new Node(key, value);
            addToFront(newNode); // Add new node to the front
            map.put(key, newNode);
        }
    }

    private void moveToFront(Node node) {
        removeNode(node); // Remove node from current position
        addToFront(node); // Insert node at the front
    }

    private void addToFront(Node node) {
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
        node.prev = head;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void removeLeastRecentlyUsed() {
        Node lru = tail.prev; // The node before tail is least recently used
        removeNode(lru);      // Remove from the doubly linked list
        map.remove(lru.key);  // Remove from the map
    }
}
