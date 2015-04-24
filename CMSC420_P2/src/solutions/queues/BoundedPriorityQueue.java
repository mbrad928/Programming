package solutions.queues;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by max on 4/17/15.
 */
public class BoundedPriorityQueue<T> {

    public ArrayList<QueueNode<T>> elements;
    public double maxPriority;
    public int currSize;
    public int maxSize;


    public BoundedPriorityQueue(int size){
        if(size <= 0)
            throw new RuntimeException("size must be greater than 0");
        elements = new ArrayList<QueueNode<T>>(size);
        maxPriority = 0;
        currSize = 0;
        maxSize = size;
    }

    public void enqueue(T element, double priority){
    	if(!containsPriority(priority)) {
			if(currSize < maxSize){
				//insert normally
				insert(element,priority);
			} else {
				//check relation between priority and maxPriority
				if(priority < maxPriority){
					//remove current max and insert new
					elements.remove(maxNode());
					currSize -= 1;
					maxPriority = maxNode().priority;
					insert(element,priority);
				}
			}
		}
    }

    public void insert(T element, double priority){
    	if(currSize == 0){
			elements.add(new QueueNode<T>(element,priority));
				currSize += 1;
		} else {
			for(int i = 0; i < elements.size(); i++){
				if(priority < elements.get(i).priority){
					elements.add(i,new QueueNode<T>(element,priority);
					currSize+=1;
						if(priority > maxPriority)
							maxPriority = priority;
				}
			}
		}
	}

    public T dequeue(){
		if(currSize > 0){
			QueueNode<T> min = elements.get(0);
    		elements.remove(0);
			currSize -= 1;
			if(isEmpty()){
				maxPriority = 0;
			} else {
				maxPriority = maxNode().priority;	
			}
			return min;
		}
		return null;
	}

    public T first(){
        if(currSize>0)
            return elements.get(0).key;
        return null;
    }

    public T last(){
        if(currSize > 0) 
			return maxNode().key;
        return null;
    }

    public int size(){
        return  currSize;
    }

    public boolean isEmpty(){
        return currSize==0;
    }

    public void clear(){
        elements.clear();
        currSize = 0;
        maxPriority = 0;
    }

    public Iterator<T> iterator(){
        ArrayList<T> list = new ArrayList<T>();
        for(QueueNode<T> node: elements){
            list.add(node.key);
        }
        return list.iterator();
    }

    public QueueNode<T> maxNode(){
        if(currSize > 0){
			QueueNode<T> max = elements.get(0);
			double currP = 0;
			for(QueueNode<T> node : elements){
				if(node.priority > currP){
					max = node;
					currP = node.priority;
				}
			}
			return max;
		}
        return null;
    }

    public boolean containsPriority(double priority){
        for(QueueNode<T> node : elements){
            if(node.priority == priority)
                return true;
        }
        return false;
    }

    public String toString(){
        String output = "[";
        for(QueueNode<T> node : elements){
            output += node.key+","+node.priority+";";
        }
        output += "]";
        return output;
    }


}
