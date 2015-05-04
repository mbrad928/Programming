package solutions.trees;

import solutions.utils.KDPoint;

import java.util.ArrayList;

/**
 * Created by Max on 4/28/15.
 */
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
    }

    //height of tree rooted at this node
    public int height(){
        //can't be called on null so no need for null check
        if(this.left == null && this.right == null){
            return 0;
        }
        int lefth = -1;
        if(this.left != null)
            lefth = this.left.height();
        int righth = -1;
        if(this.right != null)
            righth = this.right.height();
        if(lefth > righth)
            return 1 + lefth;
        else return 1 + righth;
    }

    public boolean lookup(KDPoint p, int k){
        int level = 0;
        TreeNode curr = this;
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

    public void rangeHelper(ArrayList<KDPoint>  points, KDPoint p, KDPoint max, KDPoint min,int level, int k){
        if((this.point.coords[level%k] >= min.coords[level%k]) && this.point.coords[level%k] <= max.coords[level%k]){
            points.add(this.point);
        }
        if(this.right != null && (this.left == null || !this.left.lookup(max,k)))
            this.right.rangeHelper(points,p,max,min,level+1,k);
        if(this.left != null && (this.right == null || !this.right.lookup(min,k)))
            this.left.rangeHelper(points, p,max,min,level+1,k);
    }
}
