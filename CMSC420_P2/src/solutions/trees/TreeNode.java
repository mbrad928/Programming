package solutions.trees;

import solutions.utils.KDPoint;

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
}
