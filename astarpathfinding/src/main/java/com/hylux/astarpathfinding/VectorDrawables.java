package com.hylux.astarpathfinding;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.util.Log;

import java.util.List;

public class VectorDrawables {

    private final int WIDTH, HEIGHT;
    private Rect bounds;

    public VectorDrawables(int width, int height){
        this.WIDTH = width;
        this.HEIGHT = height;
        this.bounds = new Rect(0, 0, WIDTH, HEIGHT);
    }

    public ShapeDrawable newPoint(int x, int y) {
        ShapeDrawable point = new ShapeDrawable(new OvalShape());
        point.setBounds(x-5, y-5, x+5, y+5);
        return point;
    }

    public ShapeDrawable newPoint(Point position) {
        ShapeDrawable point = new ShapeDrawable(new OvalShape());
        point.setBounds(position.x-5, position.y-5, position.x+5, position.y+5);
        return point;
    }

    public ShapeDrawable newLine(int x1, int y1, int x2, int y2) {
        Path path = new Path();
        path.moveTo(x1, y1);
        path.lineTo(x2, y2);
        ShapeDrawable line = new ShapeDrawable(new PathShape(path, WIDTH, HEIGHT));
        line.setBounds(bounds);
        line.getPaint().setStyle(Paint.Style.STROKE);
        return line;
    }

    public ShapeDrawable newLine(Point start, Point end) {
        Path path = new Path();
        path.moveTo(start.x, start.y);
        path.lineTo(end.x, end.y);
        ShapeDrawable line = new ShapeDrawable(new PathShape(path, WIDTH, HEIGHT));
        line.setBounds(bounds);
        line.getPaint().setStyle(Paint.Style.STROKE);
        return line;
    }

    public ShapeDrawable newPath(List<Point> wayPoints) {
        Path path = new Path();
        path.moveTo(wayPoints.get(0).x, wayPoints.get(0).y);

        for (Point wayPoint : wayPoints) {
            path.lineTo(wayPoint.x, wayPoint.y);
        }
        Log.d("PATH: ", path.toString());
        ShapeDrawable line = new ShapeDrawable(new PathShape(path, WIDTH, HEIGHT));
        line.setBounds(bounds);
        line.getPaint().setStyle(Paint.Style.STROKE);
        line.getPaint().setColor(Color.MAGENTA);
        return line;
    }
}
