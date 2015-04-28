package solutions.trees;

import org.junit.Test;
import solutions.utils.KDPoint;

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

}