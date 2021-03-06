package solutions.trees;

import org.junit.Test;
import solutions.queues.BoundedPriorityQueue;
import solutions.utils.KDPoint;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by max on 4/28/15.
 */
public class KDTreeTest {

    @Test
    public void testConstructor(){
        KDTree tree = new KDTree();
        boolean update = false;
        assertTrue(tree.k == 2);
        try {
            tree = new KDTree(0);
        } catch (Exception e){
            update = true;
        }
        assertTrue(update);
    }

    @Test
    public void testInsert(){
        //tree with k = 2
        KDTree tree = new KDTree();
        tree.insert(new KDPoint(5, 4));
        assertTrue(tree.root.point.coords[0] == 5);
        assertTrue(tree.root.point.coords[1] == 4);
        assertTrue(tree.root != null);
        tree.insert(new KDPoint(4, 4));
        assertTrue(tree.root.point.coords[0] == 5);
        assertTrue(tree.root.point.coords[1] == 4);
        assertTrue(tree.root.left.point.coords[0] == 4);
        assertTrue(tree.root.left.point.coords[1] == 4);
        tree.insert(new KDPoint(5, 6));//test for tie
        assertTrue(tree.root.right.point.coords[0] == 5);
        assertTrue(tree.root.right.point.coords[1] == 6);
        //now test insertion from comparing y coords
        tree.insert(new KDPoint(4, 3));//should be all the way left
        assertTrue(tree.root.left.left.point.coords[0] == 4);
        assertTrue(tree.root.left.left.point.coords[1] == 3);
        //insert left then right
        tree.insert(new KDPoint(4, 5));
        assertTrue(tree.root.left.right.point.coords[0] == 4);
        assertTrue(tree.root.left.right.point.coords[1] == 5);
        //insert right then left
        tree.insert(new KDPoint(6, 5));
        assertTrue(tree.root.right.left.point.coords[0] == 6);
        assertTrue(tree.root.right.left.point.coords[1] == 5);
        //insert right then right
        tree.insert(new KDPoint(7, 7));
        assertTrue(tree.root.right.right.point.coords[0] == 7);
        assertTrue(tree.root.right.right.point.coords[1] == 7);
        //now insert at 3rd level to check for x comparison again
        tree.insert(new KDPoint(3,3));
        assertTrue(tree.root.left.left.left.point.coords[0] == 3);
        assertTrue(tree.root.left.left.left.point.coords[1] == 3);
    }

    @Test
    public void testInsert2(){
        //k = 3
        KDTree tree = new KDTree(3);
        KDPoint root = new KDPoint(5,5,5);
        tree.insert(root);
        assertTrue(tree.root.point.equals(root));
        KDPoint x1 = new KDPoint(3,5,5);
        tree.insert(x1);
        assertTrue(tree.root.left.point.equals(x1));
        KDPoint y1 = new KDPoint(3,4,5);
        tree.insert(y1);
        assertTrue(tree.root.left.left.point.equals(y1));
        KDPoint z1 = new KDPoint(3,4,4);
        tree.insert(z1);
        assertTrue(tree.root.left.left.left.point.equals(z1));
        assertNull(tree.root.right);
        KDPoint x2 = new KDPoint(6,5,5);
        tree.insert(x2);
        assertTrue(tree.root.right.point.equals(x2));
        KDPoint y2 = new KDPoint(6,4,5);
        tree.insert(y2);
        assertTrue(tree.root.right.left.point.equals(y2));
        KDPoint z2 = new KDPoint(6,4,6);
        tree.insert(z2);
        assertTrue(tree.root.right.left.right.point.equals(z2));
        assertNull(tree.root.right.right);
        assertNull(tree.root.right.left.left);
    }

    @Test
    public void testLookup(){
        KDTree tree = new KDTree(2);
        assertFalse(tree.lookup( new KDPoint(1,2)));
        tree.insert(new KDPoint(1, 2));
        assertTrue(tree.lookup(new KDPoint(1, 2)));
        tree.insert(new KDPoint(5, 4));
        tree.insert(new KDPoint(3, 8));
        tree.insert(new KDPoint(1, 5));
        assertTrue(tree.lookup(new KDPoint(3, 8)));
        assertTrue(tree.lookup(new KDPoint(1, 5)));
        assertFalse(tree.lookup(new KDPoint(1,1)));
    }

    @Test
    public void testHeight(){
        KDTree tree = new KDTree(2);
        assertTrue(tree.height() == -1);
        assertTrue(tree.isEmpty());
        tree.insert(new KDPoint(5, 5));
        assertFalse(tree.isEmpty());
        assertTrue(tree.height() == 0);
        tree.insert(new KDPoint(4, 5));
        assertTrue(tree.height() == 1);
        tree.insert(new KDPoint(6, 5));
        assertTrue(tree.height() == 1);
        tree.insert(new KDPoint(6, 4));
        assertTrue(tree.height() == 2);
        tree.insert(new KDPoint(7, 4));
        assertTrue(tree.height() == 3);
    }

    @Test
    public void testDelete(){
        KDTree tree = new KDTree(2);
        KDPoint p = new KDPoint(1,2);
        tree.insert(p);
        tree.delete(p);
        assertNull(tree.root);
        assertTrue(tree.isEmpty());
        tree.insert(p);
        KDPoint p2 = new KDPoint(2,3);
        tree.insert(p2);
        tree.delete(p);
        assertTrue(tree.root.point.coords[0] == 2);

        tree = new KDTree(2);
        KDPoint a = new KDPoint(1,2);
        tree.insert(a);
        KDPoint a2 = new KDPoint(-1,1);
        tree.insert(a2);
        KDPoint a3 = new KDPoint(0,0);
        tree.insert(a3);
        KDPoint a4 = new KDPoint(-2,-1);
        tree.insert(a4);
        tree.delete(a2);
        assertTrue(tree.root.point.coords[0] == 1);
        assertTrue(tree.root.left.point.coords[0] == -2);
        assertNull(tree.root.left.left);
        assertTrue(tree.root.left.right.point.coords[0] == 0);

    }

    @Test
    public void testFindMin(){
        KDTree tree = new KDTree(2);
        KDPoint p = new KDPoint(1,1);
        tree.insert(p);
        TreeNode n = new TreeNode(p);
        assertTrue(tree.findMin(n, 0).point.coords[0] == 1);
        KDPoint p2 = new KDPoint(2,1);
        tree.insert(p2);
        n = new TreeNode(p);
        TreeNode n2 = new TreeNode(p2);
        assertTrue(tree.findMin(tree.root,0).point.coords[0]==1);
        tree.insert(new KDPoint(0, 0));
        assertTrue(tree.findMin(tree.root.left, 0).point.coords[0] == 0);
        tree.insert(new KDPoint(0, -1));
        assertTrue(tree.findMin(tree.root,1).point.coords[1]==-1);
    }

    @Test
    public void testRange(){
        KDTree tree = new KDTree(2);
        tree.insert(new KDPoint(1,1));
        tree.insert(new KDPoint(-1,-1));
        tree.insert(new KDPoint(5,5));
        tree.insert(new KDPoint(2, 2));
        Collection<KDPoint> coll = tree.range(new KDPoint(0,0),2);
        assertTrue(coll.contains(new KDPoint(1, 1)));
        assertTrue(coll.contains(new KDPoint(-1,-1)));
        assertTrue(coll.contains(new KDPoint(2,2)));
        tree = new KDTree(2);
        assertTrue(tree.range(new KDPoint(1,1),1).isEmpty());
        tree.insert(new KDPoint(10,10));
        assertTrue(tree.range(new KDPoint(1,1),1).isEmpty());
    }

    @Test
    public void testNearestNeighbor(){
        KDTree tree = new KDTree(2);
        assertNull(tree.nearestNeighbor(new KDPoint(0, 0)));
        KDPoint p = new KDPoint(0,0);
        tree.insert(p);
        assertNull(tree.nearestNeighbor(new KDPoint(0, 0)));
        KDPoint p2 = new KDPoint(-3,-3);
        tree.insert(p2);
        assertTrue(tree.nearestNeighbor(p).coords[0] == -3);
        KDPoint p3 = new KDPoint(2,2);
        tree.insert(p3);
        assertTrue(tree.nearestNeighbor(p).coords[0] == 2);
        KDPoint p4 = new KDPoint(-2,10);
        tree.insert(p4);
        assertTrue(tree.nearestNeighbor(p).coords[0] == 2);
        KDPoint p5 = new KDPoint(1,1);
        tree.insert(p5);
        assertTrue(tree.nearestNeighbor(p).coords[0] == 1);
        assertTrue(tree.nearestNeighbor(p5).coords[0] == 0);
        assertTrue(tree.nearestNeighbor(p3).coords[0] == 1);
        KDPoint p6 = new KDPoint(-3,9);
        tree.insert(p6);
        assertTrue(tree.nearestNeighbor(p4).coords[1] == 9);
    }

    @Test
    public void testNearestNeighbor2(){
        KDTree tree = new KDTree(2);
        KDPoint p1 = new KDPoint(1,-1);
        tree.insert(p1);
        KDPoint p2 = new KDPoint(5,3);
        tree.insert(p2);
        KDPoint p3 = new KDPoint(-2,7);
        tree.insert(p3);
        KDPoint p4 = new KDPoint(0,0);
        tree.insert(p4);
        KDPoint p5 = new KDPoint(-5,-1);
        tree.insert(p5);
        KDPoint p6 = new KDPoint(-3,-3);
        tree.insert(p6);
        KDPoint p7 = new KDPoint(6,-3);
        tree.insert(p7);
        KDPoint p8 = new KDPoint(-8,-8);
        tree.insert(p8);
        KDPoint p9 = new KDPoint(-6,3);
        tree.insert(p9);
        KDPoint p10 = new KDPoint(1,4);
        tree.insert(p10);
        assertTrue(tree.nearestNeighbor(p4).equals(p1));
        assertTrue(tree.nearestNeighbor(p8).equals(p6));
        assertTrue(tree.nearestNeighbor(p6).equals(p5));
        assertTrue(tree.nearestNeighbor(p2).equals(p10));
        assertTrue(tree.nearestNeighbor(p7).equals(p1));
        assertTrue(tree.nearestNeighbor(p5).equals(p6));
        assertTrue(tree.nearestNeighbor(p9).equals(p5));
        assertTrue(tree.nearestNeighbor(p1).equals(p4));

        KDTree tree2 = new KDTree(2);
        KDPoint a1 = new KDPoint(0,0);
        tree2.insert(a1);
        KDPoint a2 = new KDPoint(75.5,10);
        tree2.insert(a2);
        KDPoint a3 = new KDPoint(75.4,10);
        tree2.insert(a3);
        assertTrue(tree2.nearestNeighbor(a1).equals(a3));
        KDPoint a4 = new KDPoint(-70.0,10);
        tree2.insert(a4);
        assertTrue(tree2.nearestNeighbor(a1).equals(a4));

    }

    @Test
    public void testKNN(){
        KDTree tree = new KDTree(2);
        KDPoint p1 = new KDPoint(0,0);
        KDPoint p2 = new KDPoint(1,1);
        KDPoint p3 = new KDPoint(2,2);
        KDPoint p4 = new KDPoint(3,3);
        tree.insert(p1);
        tree.insert(p2);
        tree.insert(p3);
        tree.insert(p4);
        BoundedPriorityQueue<KDPoint> queue = tree.kNearestNeighbors(3,p1);
        assertTrue(queue.first().equals(p2));
        assertTrue(queue.last().equals(p4));
        int update = 0;
        try{
            queue = tree.kNearestNeighbors(0,p2);
        } catch (Exception e){
            update = 1;
        }
        assertTrue(update == 1);

        tree = new KDTree(1);
        KDPoint a1 = new KDPoint(0.0);
        KDPoint a2 = new KDPoint(1.0);
        KDPoint a3 = new KDPoint(2.0);
        tree.insert(a1);
        tree.insert(a2);
        tree.insert(a3);
        queue = tree.kNearestNeighbors(2,a2);
        assertTrue(queue.first().equals(a1));
        assertTrue(queue.last().equals(a3));
    }

}