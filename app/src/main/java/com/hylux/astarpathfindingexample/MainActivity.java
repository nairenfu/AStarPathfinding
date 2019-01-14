package com.hylux.astarpathfindingexample;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hylux.astarpathfinding.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConstraintLayout main = findViewById(R.id.main);

        List<Vector> allVectors = new ArrayList<>();
        allVectors.add(new Vector(0, 0, 0));
        allVectors.add(new Vector(1, 0, 4));
        allVectors.add(new Vector(2, -3, 1));
        allVectors.add(new Vector(3, 3, 5));
        allVectors.add(new Vector(4, 7, 5));
        allVectors.add(new Vector(5, 10, 10));
        Collections.sort(allVectors, Vector.IdComparator);
        allVectors.get(0).addNeighbour(allVectors.get(1));
        allVectors.get(0).addNeighbour(allVectors.get(2));
        allVectors.get(0).addNeighbour(allVectors.get(3));
        allVectors.get(2).addNeighbour(allVectors.get(0));
        allVectors.get(2).addNeighbour(allVectors.get(4));
        allVectors.get(3).addNeighbour(allVectors.get(4));
        allVectors.get(4).addNeighbour(allVectors.get(5));
        //Log.d("VECTOR", allVectors.toString());
        /*for (int i = 0; i < allVectors.size(); i++) {
            Log.d("VECTOR", allVectors.get(i).getNeighbours().toString());
        }*/
        Grid grid = new Grid(allVectors);
        //GridView gridView = new GridView(this, grid);
        MapView mapView = new MapView(this, grid);
        setContentView(mapView);

        //Log.d("VECTOR GRID", grid.toString());
        List<Vector> path = grid.findPath(grid.getAllVectors().get(0), grid.getAllVectors().get(5));
        //gridView.drawPath(path);
        mapView.drawPath(path);
        Log.d("VECTOR PATH", path.toString());
    }
}
