package solutions.queues;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by max on 4/17/15.
 */
public class BoundedPriorityQueue<T> {

    public ArrayList<QueueNode<T>> heap;
    public double maxPriority;
    public int currSize;
    public int maxSize;


    public BoundedPriorityQueue(int size){
        if(size <= 0)
            throw new RuntimeException("size must be greater than 0");
        heap = new ArrayList<QueueNode<T>>(size+1);//+1 to account for inserting then removing max
        maxPriority = 0;
        currSize = 0;
        maxSize = size;
    }

    public void enqueue(T element, double priority){
        if(!containsPriority(priority)) {
            if (currSize < maxSize) { //insert normally
                insert(element, priority);
            } else { //need to check incoming priority and maxPriority
                if (priority < maxPriority) {
                    //insert and remove max
                    insert(element,priority);
                    QueueNode<T> max = maxNode();
                    heap.remove(max);
                    currSize-=1;
                    maxPriority = maxNode().priority;
                }
                //otherwise nothing changes
            }
        }
    }

    public void insert(T element, double priority){
        QueueNode<T> newNode = new QueueNode<T>(element, priority);
        heap.add(newNode);
        currSize += 1;
        if (priority > maxPriority)
            maxPriority = priority;
        //bubble up
        if (currSize > 1) {
            for (int i = heap.size() - 1; i > 0; i--) {
                QueueNode<T> currNode = heap.get(i);
                QueueNode<T> parent = heap.get(parent(i));
                if (currNode.priority < parent.priority) {
                    //bubble up
                    //remove and reinsert element
                    heap.remove(currNode);
                    heap.remove(parent);
                    heap.add(i, parent);
                    heap.add(parent(i), currNode);
                } else {
                    break;
                }
            }
        }
    }

    public T dequeue(){
        if(currSize > 0){
            //remove min, then take last key and put at top then percolate down
            heap.remove(0);
            currSize -= 1;
            QueueNode<T> last = heap.get(heap.size()-1);
            heap.remove(last);
            heap.add(0,last);
            //now percolate down. swap with smaller child
            if(currSize > 0) {
                for (int i = 0; i < heap.size(); i++) {
                    QueueNode<T> currNode = heap.get(i);
                    QueueNode<T> leftChild = heap.get(leftChild(i));
                    QueueNode<T> rightChild = heap.get(rightChild(i));
                    if(currNode.priority < leftChild.priority && currNode.priority < rightChild.priority) {
                        break; //already min
                    } else if(currNode.priority > leftChild.priority && currNode.priority > rightChild.priority){
                        //pick the smaller priority
                        if(leftChild.priority < rightChild.priority) {
                            //switch with left child
                            heap.remove(currNode);
                            heap.remove(leftChild);
                            heap.add(i, leftChild);
                            heap.add(leftChild(i),currNode);
                        } else {
                            //switch with right child
                            heap.remove(currNode);
                            heap.remove(rightChild);
                            heap.add(i, rightChild);
                            heap.add(rightChild(i),currNode);
                        }
                    } else if(currNode.priority > leftChild.priority) {
                        //switch with left child
                        heap.remove(currNode);
                        heap.remove(leftChild);
                        heap.add(i,leftChild);
                        heap.add(leftChild(i),currNode);
                    } else {
                        //switch with right child
                        heap.remove(currNode);
                        heap.remove(rightChild);
                        heap.add(i, rightChild);
                        heap.add(rightChild(i),currNode);
                    }
                }
            }
        }
        return null;
    }

    public T first(){
        if(currSize>0)
            return heap.get(0).key;
        return null;
    }

    public T last(){
        if(currSize > 0)
            return heap.get(heap.size()-1).key;
        return null;
    }

    public int size(){
        return  currSize;
    }

    public boolean isEmpty(){
        return currSize==0;
    }

    public void clear(){
        heap.clear();
        currSize = 0;
        maxPriority = 0;
    }

    public Iterator<T> iterator(){
        ArrayList<T> list = new ArrayList<T>();
        for(QueueNode<T> node: heap){
            list.add(node.key);
        }
        return list.iterator();
    }

    public QueueNode<T> maxNode(){
        return heap.get(heap.size()-1);
    }

    public boolean containsPriority(double priority){
        for(QueueNode<T> node : heap){
            if(node.priority == priority)
                return true;
        }
        return false;
    }

    public int parent(int index){
        return ((index-1)/2);
    }

    public int leftChild(int index){
        return (2*index)+1;
    }

    public int rightChild(int index){
        return (2*index)+2;
    }

    public String toString(){
        String output = "[";
        for(QueueNode<T> node : heap){
            output += node.key+","+node.priority+";";
        }
        output += "]";
        return output;
    }


}
