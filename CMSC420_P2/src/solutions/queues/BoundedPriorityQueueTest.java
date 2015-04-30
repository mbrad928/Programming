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
        //test when duplicate priority for FIFO
        queue = new BoundedPriorityQueue<>(5);
        queue.enqueue(1,1);
        queue.enqueue(2,1);
        assertTrue(queue.first()==1);
        queue.enqueue(3,0);
        System.out.println(queue);
        queue.enqueue(4,4);
        queue.enqueue(4,5);
        System.out.println(queue);
        queue.enqueue(3,1);
        System.out.println(queue);
        queue.enqueue(4,1);
        System.out.println(queue);
        assertTrue(queue.last() == 4);
    }

    @Test
    public void testDequeue(){
        BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<>(5);
        queue.enqueue(5,5);
        queue.enqueue(8, 8);
        queue.enqueue(7, 7);
        System.out.println(queue);
        queue.dequeue();
        System.out.println(queue);
        queue.dequeue();
        assertTrue(queue.size() == 1);
        queue.dequeue();
        assertTrue(queue.isEmpty());
        System.out.println(queue);
        assertNull(queue.dequeue());
    }

    @Test
    public void testClear(){
        BoundedPriorityQueue<Integer> queue = new BoundedPriorityQueue<Integer>(5);
        queue.enqueue(1,1);
        queue.enqueue(2,2);
        queue.enqueue(3,3);
        queue.clear();
        assertTrue(queue.isEmpty());
    }
}
