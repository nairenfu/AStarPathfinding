package com.hylux.astarpathfinding;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class MapView extends View {

    private Grid grid;

    private int width, height;
    private Point dimensions;

    private VectorDrawables vectorDrawables;

    private List<Vector> vectors;
    private Point xRange, yRange;

    private List<Drawable> drawables;

    public MapView(Context context, Grid grid) {
        super(context);

        this.grid = grid;

        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        height = Resources.getSystem().getDisplayMetrics().heightPixels - 100; //TODO make this not hardcoded
        dimensions = new Point(width, height);

        this.vectorDrawables = new VectorDrawables(width, height);

        this.vectors = new ArrayList<>(grid.getAllVectors());

        Collections.sort(vectors, Vector.XComparator);
        int xMin = vectors.get(0).x;
        int xMax = vectors.get(vectors.size()-1).x;
        xRange = new Point(xMin, xMax);

        Collections.sort(vectors, Vector.YComparator);
        int yMin = vectors.get(0).y;
        int yMax = vectors.get(vectors.size()-1).y;
        yRange = new Point(yMin, yMax);

        drawables = new ArrayList<>();

        drawVectors(vectors);
    }

    public void drawVectors(List<Vector> vectors) {
        for (Vector vector : vectors) {
            Point position = new Point(vector);
            position = new Point(Utility.normalToAndroidPosition(position, dimensions, xRange, yRange, 16));
            drawables.add(vectorDrawables.newPoint(position));

            for (Vector neighbor : vector.getNeighbours()) {
                Point end = new Point(Utility.normalToAndroidPosition(new Point(neighbor), dimensions, xRange, yRange, 16));
                drawables.add(vectorDrawables.newLine(position, end));
            }
        }
        Log.d("DRAWABLES", drawables.toString());
    }

    public void drawPath(List<Vector> vectorPath) {
        List<Point> wayPoints = new ArrayList<>();

        for (Vector vector : vectorPath) {
            wayPoints.add(new Point(Utility.normalToAndroidPosition(new Point(vector), dimensions, xRange, yRange, 16)));
        }

        drawables.add(vectorDrawables.newPath(wayPoints));
        Log.d("PATH ADDED ", wayPoints.toString());
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        Log.d("ON_DRAW ", "CALLED");
        for (Drawable drawable : drawables) {
            drawable.draw(canvas);
        }
    }
}
