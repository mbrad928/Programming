package solutions.trees;

import solutions.queues.BoundedPriorityQueue;
import solutions.utils.KDPoint;

import java.util.Collection;

/**
 * Created by max on 4/17/15.
 */
public class KDTree {

    public KDTree(){
        this(2);
    }

    public KDTree(int k){

    }

    public void insert(KDPoint p){

    }

    public void delete(KDPoint p){

    }

    public boolean lookup(KDPoint p){
        return false;
    }

    public KDPoint getRoot(){
        return null;
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
}
