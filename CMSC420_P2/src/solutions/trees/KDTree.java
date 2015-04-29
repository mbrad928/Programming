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
        if(!lookup(p)) {
            //needs to work in any dimensionality > 0.
            int level = 0; //can do level % k to find which coordinate to check
            int dir = 0; //0 if we go left, 1 if we go right
            TreeNode curr = root;
            TreeNode prev = null;
            while (curr != null) {
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
            if (prev == null) {
                this.root = new TreeNode(p);
            } else {
                curr = new TreeNode(p);
                if (dir == 0) {
                    prev.left = curr;
                } else {
                    prev.right = curr;
                }
            }
        }
    }

    public void delete(KDPoint p){
        if(lookup(p)) {//only continue if p is in the tree
            //find where it is and keep track of what level it is on
            int level = 0;
            TreeNode curr = root;
            TreeNode prev = null;
            int dir = 0; //0 for left, 1 for right
            while (!curr.point.equals(p)) {
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
                //we now know what level it is on, which will tell us which variable to check based on level%k
                int var = level % k;
                //see if curr has a right tree
                if (curr.right != null) {
                    //can do normal delete. need to find minimum of var coord
                    TreeNode replacement = findMin(curr.right, var);
                    //prev must point to replacement
                    delete(replacement.point);
                    if(prev == null){
                        this.root = replacement;
                    } else {
                        if (dir == 0)
                            prev.left = replacement;
                        else
                            prev.right = replacement;
                    }
                    replacement.left = curr.left;
                    replacement.right = curr.right;

                } else if (curr.left != null) {
                    //have to get replacement from left and move left tree to right side
                    //prev must point to replacement
                    TreeNode replacement = findMin(curr.left, var);
                    delete(replacement.point);
                    if(prev == null){
                        this.root = replacement;
                    } else {
                        if (dir == 0)
                            prev.left = replacement;
                        else
                            prev.right = replacement;
                    }
                    replacement.left = curr.left;
                    replacement.right = curr.right;

                } else if(prev == null){
                    this.root = null;
                } else {//leaf node
                    if(dir == 0)
                        prev.left = null;
                    else
                        prev.right = null;

            }
        }
    }

    public TreeNode findMin(TreeNode start, int var){
        TreeNode lmin = start;
        TreeNode rmin = start;
        TreeNode curr = start;
        if(start.left != null) {
            lmin = findMin(start.left, var);
        }
        if(start.right != null) {
            rmin = findMin(start.right, var);
        }
        if(start.left != null && lmin.point.coords[var] < curr.point.coords[var]){
            curr = lmin;
            if(start.right != null && rmin.point.coords[var] < lmin.point.coords[var]){
                curr = rmin;
            }
        } else if(start.right != null && rmin.point.coords[var] < curr.point.coords[var]){
            curr = rmin;
        }
        return curr;
    }

    public boolean lookup(KDPoint p){
        int level = 0;
        TreeNode curr = root;
        while(curr != null){
            if(p.equals(curr.point)){
                return true;
            } else if(p.coords[level % k] < curr.point.coords[level % k]){
                //left
                curr = curr.left;
            } else {
                //right
                curr = curr.right;
            }
            level++;
        }
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
        if(this.root == null)
            return -1;
        return this.root.height();
    }

    public boolean isEmpty(){
        return height() == -1;
    }


}
