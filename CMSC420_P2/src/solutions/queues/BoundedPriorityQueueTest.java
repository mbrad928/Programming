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
	    queue.enqueue(5, 5);
	    assertTrue(queue.first() == 5);
	    assertTrue(queue.last() == 5);
	    assertTrue(queue.size() == 1);
	    assertFalse(queue.isEmpty());
        queue.enqueue(4, 4);
        assertTrue(queue.first() == 4);
        assertTrue(queue.last() == 5);
        System.out.println(queue);
        queue.enqueue(7, 7);
        queue.enqueue(8, 8);
        queue.enqueue(9, 9);
        queue.enqueue(6, 6);
        System.out.println(queue);
        queue.enqueue(7, 6);
        System.out.println(queue);
        assertTrue(queue.size()==5);
    }

    @Test
    public void testDequeue(){
        BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<>(5);
        queue.enqueue(5,5);
        queue.enqueue(8,8);
        queue.enqueue(7,7);
        System.out.println(queue);
        queue.dequeue();
        System.out.println(queue);
    }
}
