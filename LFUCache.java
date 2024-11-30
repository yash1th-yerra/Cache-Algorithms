

import java.util.HashMap;
import java.util.Map;

class LFUCache {

    Map<Integer,DoubleLinkedList> frequencyMap;
    Map<Integer,DLLNode> cache;
    final int capacity;
    int curSize;
    int minFrequency;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.frequencyMap = new HashMap<>();
        this.cache = new HashMap<>();
        curSize=0;
        this.minFrequency = 0; 
    }
    
    public int get(int key) {
        if(!cache.containsKey(key)) return -1;
        DLLNode curNode = cache.get(key);
        updateNode(curNode);
        return curNode.value;
    }
    
    public void put(int key, int value) {
        if(capacity==0) return;

        if(cache.containsKey(key)){
            DLLNode curNode = cache.get(key);
            curNode.value = value;
            updateNode(curNode);
        }
        else{
            curSize++;
            if(curSize>capacity){
                DoubleLinkedList minFreqList = frequencyMap.get(minFrequency);
                cache.remove(minFreqList.tail.prev.key);
                minFreqList.removeNode(minFreqList.tail.prev);
                curSize--;
            }

            minFrequency = 1;
            DLLNode newNode = new DLLNode(key,value);
            DoubleLinkedList newList = frequencyMap.getOrDefault(1,new DoubleLinkedList());
            newList.addNode(newNode);
            frequencyMap.put(1,newList);
            cache.put(key,newNode);
        }
        
    }

    public void updateNode(DLLNode curNode){
        int curFrequency = curNode.frequency;
        DoubleLinkedList curList = frequencyMap.get(curFrequency);
        curList.removeNode(curNode);

        if(curFrequency==minFrequency && curList.listSize==0){
            minFrequency++;
        }

        curNode.frequency++;

        DoubleLinkedList newList = frequencyMap.getOrDefault(curNode.frequency,new DoubleLinkedList());
        newList.addNode(curNode);
        frequencyMap.put(curNode.frequency,newList);

        

    }



    class DLLNode{
        int key,value,frequency;
        DLLNode prev,next;
        public DLLNode(int key,int value){
            this.key = key;
            this.value = value;
            this.frequency =1;
        }
    }


    class DoubleLinkedList{
        DLLNode head,tail;
        int listSize;
        public DoubleLinkedList(){
            head = new DLLNode(0,0);
            tail = new DLLNode(0,0);
            head.next  = tail;
            tail.prev = head;
            listSize =0;
        }


        public void removeNode(DLLNode curNode){
            DLLNode prevNode = curNode.prev;
            DLLNode nextNode = curNode.next;
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            listSize--;
        }
        public void addNode(DLLNode curNode){
            DLLNode nextNode = head.next;
            curNode.next = nextNode;
            curNode.prev = head;
            head.next = curNode;
            nextNode.prev = curNode;
            listSize++;
        }
    }
}

/**
 * Your LFUCache object will be instantiated and called as such:
 * LFUCache obj = new LFUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */