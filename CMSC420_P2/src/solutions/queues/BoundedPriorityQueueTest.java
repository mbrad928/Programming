package solutions.queues;


import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by max on 4/23/15.
 */
public class BoundedPriorityQueueTest {
    @Test
    public void testConstructor(){
       boolean thrown = false;
        try {
            BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<>(0);
        } catch (RuntimeException e){
            thrown = true;
        }
        assertTrue(thrown);
        BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<>(5);
        assertTrue(queue.maxSize == 5);
    }

    @Test
    public void testEnqueue(){
	BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<>(5);
	assertTrue(queue.isEmpty());
	queue.enqueue(5,5);
	assertTrue(queue.first() == 5);
	assertTrue(queue.last() == 5);
	assertTrue(queue.size() == 1);
	assertFalse(queue.isEmpty());
    }

}
