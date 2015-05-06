package solutions.trees;

import solutions.queues.BoundedPriorityQueue;
import solutions.utils.KDPoint;
import solutions.utils.KNNComparator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by max on 4/17/15.
 */
public class KDTree {

    TreeNode root;
    int k;
    //for nearestNeighbor
    KDPoint guess;
    double bestDist;
    int dir;
    BoundedPriorityQueue<KDPoint> queue;

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
                    replacement.left = curr.right;
                    replacement.right = curr.left;

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
        if(this.root == null)
            return null;
        return this.root.point;
    }

    //minimums are coords of p - range and maximums are coods + range
    public Collection<KDPoint> range(KDPoint p, double range){
        ArrayList<KDPoint> points = new ArrayList<KDPoint>();
        if(this.root == null)
            return points;
        KDPoint max = new KDPoint(p.coords.length);
        for(int i = 0; i < p.coords.length; i++){
            max.coords[i] = p.coords[i]+range;
        }
        KDPoint min = new KDPoint(p.coords.length);
        for(int i = 0; i < p.coords.length; i++){
            min.coords[i] = p.coords[i] - range;
        }
        this.root.rangeHelper(points,p,max,min,0, this.k);

        return points;
    }

    public KDPoint nearestNeighbor(KDPoint p){
        if(this.root == null || !lookup(p) || (this.height()==0 && this.root.point.equals(p)))
            return null;
        else {
            //query point is p
            KNNComparator comp = new KNNComparator(p);
            guess = null;
            bestDist = Double.POSITIVE_INFINITY; //infinity
            dir = 0; //left is 0, right is 1
            nearestNeighborHelper(p,this.root,0);
            return guess;
        }
    }
    // TODO: fix nearestNeighbor
    public void nearestNeighborHelper(KDPoint p, TreeNode curr, int level ) {
        if (curr != null) {
            if (curr.point.distance(p) < bestDist && !p.equals(curr.point)) {
                bestDist = curr.point.distance(p);
                guess = curr.point;
            }
            if (p.coords[level % k] < curr.point.coords[level % k]) {
                nearestNeighborHelper(p, curr.left, level + 1);
                dir = 0;
            } else {
                nearestNeighborHelper(p, curr.right, level + 1);
                dir = 1;
            }
            if(Math.abs(curr.point.coords[level%k] - p.coords[level%k]) < bestDist) {
                if(dir == 0){
                    nearestNeighborHelper(p, curr.right, level + 1);
                } else {
                    nearestNeighborHelper(p, curr.left, level + 1);
                }
            }
        }
    }


    public BoundedPriorityQueue<KDPoint> kNearestNeighbors(int num, KDPoint p){
        if(num <= 0)
            throw new RuntimeException("K must be > 0");
        queue = new BoundedPriorityQueue<KDPoint>(num);
        if(this.height() == 0 && this.root.point.equals(p))
            return queue;
        else {
            bestDist = Double.POSITIVE_INFINITY; //infinity
            dir = 0; //left is 0, right is 1
            kNearestNeighborsHelper(num, p, this.root, 0);
            return queue;
        }
    }

    public void kNearestNeighborsHelper(int num, KDPoint p, TreeNode curr, int level){
        if (curr != null) {
            if(!curr.point.equals(p))
                queue.insert(curr.point,curr.point.distance(p));
            if (curr.point.distance(p) < bestDist && !p.equals(curr.point)) {
                bestDist = curr.point.distance(p);

            }
            if (p.coords[level % k] < curr.point.coords[level % k]) {
                kNearestNeighborsHelper(num, p, curr.left, level + 1);
                dir = 0;
            } else {
                kNearestNeighborsHelper(num,p,curr.right,level+1);
                dir = 1;
            }
            if(Math.abs(queue.last().coords[level%k] - p.coords[level%k]) < bestDist || queue.size() < num) {
                if(dir == 0){
                    kNearestNeighborsHelper(num, p, curr.right, level + 1);
                } else {
                    kNearestNeighborsHelper(num, p, curr.left, level + 1);
                }
            }
        }
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
