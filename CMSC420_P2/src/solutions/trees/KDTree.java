package solutions.trees;

import solutions.queues.BoundedPriorityQueue;
import solutions.utils.KDPoint;

import java.util.Collection;

/**
 * Created by max on 4/17/15.
 */
public class KDTree {

    TreeNode root;
    int k;

    public KDTree(){
        this(2);
    }

    public KDTree(int k){
        if (k <=0 ){
            throw new RuntimeException("K must be > 0");
        }
        this.k = k;
    }

    public void insert(KDPoint p){
        //needs to work in any dimensionality > 0.
        int level = 0; //can do level % k to find which coordinate to check
        int dir = 0; //0 if we go left, 1 if we go right
        TreeNode curr = root;
        TreeNode prev = null;
        while(curr != null) {
            if (p.coords[level % k] < curr.point.coords[level % k]) {
                //left subtree
                prev = curr;
                curr = curr.left;
                dir = 0;
            } else {
                //right subtree
                prev = curr;
                curr = curr.right;
                dir = 1;
            }
            level++;
        }
        if(prev == null){
            this.root = new TreeNode(p);
        } else {
            curr = new TreeNode(p);
            if(dir == 0){
                prev.left = curr;
            } else {
                prev.right = curr;
            }
        }

    }

    public void delete(KDPoint p){

    }

    public boolean lookup(KDPoint p){
        return false;
    }

    public KDPoint getRoot(){
        return this.root.point;
    }

    public Collection<KDPoint> range(KDPoint p, double range){
        return  null;
    }

    public KDPoint nearestNeighbor(KDPoint p){
        return null;
    }

    public BoundedPriorityQueue<KDPoint> kNearestNeighbors(int k, KDPoint p){
        return null;
    }

    public int height(){
        return 0;
    }

    public boolean isEmpty(){
        return height() == -1;
    }

    public class TreeNode{
        KDPoint point;
        TreeNode left, right;

        public TreeNode(){
            this.point = new KDPoint(2);
            this.left = null;
            this.right = null;
        }

        public TreeNode(KDPoint p){
            this.point = p;
            this.left = null;
            this.right = null;
        }
    }
}
