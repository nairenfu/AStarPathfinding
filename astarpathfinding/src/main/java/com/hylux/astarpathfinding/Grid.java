package com.hylux.astarpathfinding;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Grid {

    private Map<Integer, Vector> vectors;

    private List<Vector> allVectors, unvisited, open, closed;

    public List<Vector> getAllVectors() {
        return allVectors;
    }

    public void setAllVectors(List<Vector> allVectors) {
        this.allVectors = allVectors;
    }

    public Map<Integer, Vector> getVectors() {
        return vectors;
    }

    public List<Vector> findPath(Vector start, Vector end) { //TODO Check if start, end both in allV? Trivial start = end?
        if (allVectors != null) {
            unvisited = allVectors;
        } else {
            unvisited = new ArrayList<>(vectors.values());
        }

        //TODO need to initalise lists?

        unvisited.remove(start);
        open.add(start);
        Vector current = start;
        current.setParent(current);
        current.setDistanceFromStart(0);
        current.setHeuristic(Utility.edgeDistance(current, end, Utility.MANHATTAN));
        current.setPathLength(current.getHeuristic());

        //TODO MAJOR Maybe should do recursive?
        //TODO should I remove the parent, or just ignore?
        //TODO 1. Large overhead - initialise all edge values 2. Longer find - on the fly
        while (current != end) {
            Vector next;
            for (int i = 0; i < current.getNeighbours().size(); i++) {
                if (current.getNeighbours().get(i) != current.getParent()) {
                    next = current.getNeighbours().get(i);
                    if (open.contains(next)) {
                        float alternatePath = current.getDistanceFromStart() + Utility.edgeDistance(current, next, Utility.MANHATTAN);
                        if (alternatePath < next.getDistanceFromStart()) {
                            next.setParent(current);
                            next.setDistanceFromStart(alternatePath);
                            next.setPathLength(next.getHeuristic() + next.getDistanceFromStart());
                        }
                    }

                    if (unvisited.contains(next)) {
                        open.add(next);
                        next.setParent(current);
                        next.setHeuristic(Utility.edgeDistance(next, end, Utility.MANHATTAN));
                        next.setDistanceFromStart(Utility.edgeDistance(next, start, Utility.MANHATTAN));
                        next.setPathLength(next.getHeuristic() + next.getDistanceFromStart());
                    }
                }
            }

            Collections.sort(open, Vector.PathLengthComparator);
            //Log.d("VECTOR OPEN", open.toString());
            if (open.contains(end)) {
                //end.setParent(current);
                current = end;
                //Log.d("VECTOR", "SUCCESS");
                break;
            }
            open.remove(current);
            closed.add(current);
            current = open.get(0);
        }

        List<Vector> path = new ArrayList<>();
        path.add(0, current);
        //Log.d("VECTOR CURRENT", current.toString());
        while (current != start) {
            //Log.d("VECTOR PARENT", current.getParent().toString());
            current = current.getParent();
            path.add(0, current);
        }
        //Log.d("VECTOR PATH", path.toString());
        return path;
    }

    public Grid(List<Vector> allVectors) {
        this.allVectors = allVectors;
        Collections.sort(allVectors, Vector.IdComparator);

        unvisited = new ArrayList<>();
        open = new ArrayList<>();
        closed = new ArrayList<>();
    }

    public Grid(Map<Integer, Vector> vectors) {
        this.vectors = vectors;

        unvisited = new ArrayList<>();
        open = new ArrayList<>();
        closed = new ArrayList<>();
    }
}
