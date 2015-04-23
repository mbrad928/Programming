package solutions.queues;

/**
 * Created by max on 4/23/15.
 */
public class QueueNode<T> {

    public T key;
    public double priority;

    public QueueNode(T key, double priority){
        this.key = key;
        this.priority = priority;
    }

}
