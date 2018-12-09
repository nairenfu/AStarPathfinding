package com.hylux.astarpathfinding;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Vector extends Point{

    /*1. Comparable interface can be used to provide single way of sorting
         whereas Comparator interface is used to provide different ways of sorting.
      2. We don’t need to make any code changes at client side for using Comparable,
         Arrays.sort() or Collection.sort() methods automatically uses the compareTo() method of the class.
         For Comparator, client needs to provide the Comparator class to use in compare() method.
    */

    private int id;
    private List<Vector> neighbours;
    private Vector parent;
    private float heuristic, distanceFromStart, pathLength;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Vector> getNeighbours() {
        return neighbours;
    }

    public void setNeighbours(List<Vector> neighbours) {
        this.neighbours = neighbours;
    }

    public void addNeighbour(Vector neighbour) {
        if (this.neighbours.isEmpty()) {
            this.neighbours.add(neighbour);
        } else {
            if (!this.neighbours.contains(neighbour)) {
                this.neighbours.add(neighbour);
            }
        }
    }

    public Vector getParent() {
        return parent;
    }

    public void setParent(Vector parent) {
        this.parent = parent;
    }

    public float getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(float heuristic) {
        this.heuristic = heuristic;
    }

    public float getDistanceFromStart() {
        return distanceFromStart;
    }

    public void setDistanceFromStart(float distanceFromStart) {
        this.distanceFromStart = distanceFromStart;
    }

    public Vector(int id, int x, int y) {
        super(x, y);
        this.id = id;

        this.neighbours = new ArrayList<>();
        Log.d("VECTOR", neighbours.toString());
        this.parent = this;
        this.heuristic = -1f;
        this.pathLength = -1f;
        this.distanceFromStart = -1f;
    }

    public float getPathLength() {
        return pathLength;
    }

    public void setPathLength(float pathLength) {
        this.pathLength = pathLength;
    }

    public static Comparator<Vector> IdComparator = new Comparator<Vector>() {
        @Override
        public int compare(Vector v1, Vector v2) {
            return Integer.compare(v1.id, v2.id);
        }
    };

    public static Comparator<Vector> PathLengthComparator = new Comparator<Vector>() {
        @Override
        public int compare(Vector v1, Vector v2) {
            return Float.compare(v1.pathLength, v2.pathLength);
        }
    };

    @Override
    public String toString() {
        return "Vector(" + id +
                ", (" + x +
                ", " + y +
                "))";
    }

    //TODO SET NEIGHBORS?
}
