package com.hylux.astarpathfinding;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.PathShape;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class GridView extends View {

    private Grid grid;
    private List<ShapeDrawable> allDrawables;

    private int SCREEN_HEIGHT, SCREEN_WIDTH;
    private int MIN_X, MAX_X, MIN_Y, MAX_Y, RANGE_X, RANGE_Y;

    //TODO CHANGE HARDCODED STUFF TO REAL CODE
    public GridView(Context context, Grid grid) {
        super(context);
        SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
        Log.d("VECTOR_VIEW", "");
        SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
        Log.d("VECTOR HEIGHT", String.valueOf(SCREEN_HEIGHT));
        Log.d("VECTOR WIDTH", String.valueOf(SCREEN_WIDTH));

        this.grid = grid;
        allDrawables = new ArrayList<>();

        Collections.sort(this.grid.getAllVectors(), Vector.XComparator);
        MIN_X = this.grid.getAllVectors().get(0).x;
        MAX_X = this.grid.getAllVectors().get(this.grid.getAllVectors().size() - 1).x; //Check for null?
        RANGE_X = round((MAX_X - MIN_X) * 4 / 3);
        Collections.sort(this.grid.getAllVectors(), Vector.YComparator);
        MIN_Y = this.grid.getAllVectors().get(0).y;
        MAX_Y = this.grid.getAllVectors().get(this.grid.getAllVectors().size() - 1).y;
        RANGE_Y = round((MAX_Y - MIN_Y) * 5 / 3);
        Log.d("VECTOR MIN_MAX", "X: (" + MIN_X + ", " + MAX_X + "), Y: (" + MIN_Y + ", " + MAX_Y + ')');

        Collections.sort(this.grid.getAllVectors(), Vector.IdComparator);
        Log.d("VECTOR ALL", this.grid.getAllVectors().toString());

        for (int i = 0; i < this.grid.getAllVectors().size(); i++) {
            float xPosition = (this.grid.getAllVectors().get(i).x + RANGE_X/2 + MIN_X) * (SCREEN_WIDTH / RANGE_X);
            float yPosition = (RANGE_Y - this.grid.getAllVectors().get(i).y) * (SCREEN_HEIGHT / RANGE_Y) - SCREEN_HEIGHT / 5;
            Log.d("VECTOR_POS", this.grid.getAllVectors().get(i).toString() + '(' + xPosition + ", " + yPosition + ')');
            ShapeDrawable mCircle = new ShapeDrawable(new OvalShape());
            mCircle.setBounds((int) xPosition - 5,
                    (int) yPosition - 5,
                    (int) xPosition + 5,
                    (int) yPosition + 5);
            allDrawables.add(mCircle);

            for (int n = 0; n < this.grid.getAllVectors().get(i).getNeighbours().size(); n++) {
                float xb = (this.grid.getAllVectors().get(i).getNeighbours().get(n).x + RANGE_X/2 + MIN_X) * (SCREEN_WIDTH / RANGE_X);
                float yb = (RANGE_Y - this.grid.getAllVectors().get(i).getNeighbours().get(n).y) * (SCREEN_HEIGHT / RANGE_Y) - SCREEN_HEIGHT / 5;
                //TODO HOW TO DISPLAY LINE???
                Log.d("VECTOR_L", "(" + xb + ", " + yb + ')');
                Path line = new Path();
                line.moveTo(xPosition, yPosition);
                line.lineTo((int) xb, (int) yb);
                //Log.d("VECTOR LINE", line.toString());
                //ShapeDrawable mLine = new ShapeDrawable(new PathShape(line, abs(xPosition - xb), abs(yPosition - yb)));
                ShapeDrawable mLine = new ShapeDrawable(new PathShape(line, SCREEN_WIDTH, SCREEN_HEIGHT));
                mLine.getPaint().setStyle(Paint.Style.STROKE);
                /*if (xb > xPosition) {
                    if (yb > yPosition) {
                        mLine.setBounds((int) xPosition, (int)yPosition, (int) xb, (int) yb);
                    } else {
                        mLine.setBounds((int) xPosition, (int) yb, (int) xb, (int) yPosition);
                    }
                }

                if (xb < xPosition) {
                    if (yb > yPosition) {
                        mLine.setBounds((int) xb, (int) yPosition, (int) xPosition, (int) yb);
                    } else {
                        mLine.setBounds((int) xb, (int) yb, (int) xPosition, (int) yPosition);
                    }
                }*/
                mLine.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT); //Bounds can be absolute, global bounds.
                allDrawables.add(mLine);
            }
        }
        Log.d("VECTOR DRAW", allDrawables.toString());
    }

    public void drawPath(List<Vector> wayPoints) {
        Path path = new Path();
        for (int i = 0; i < wayPoints.size(); i++) {
            int x = (wayPoints.get(i).x + RANGE_X/2 + MIN_X) * (SCREEN_WIDTH / RANGE_X);
            int y = (RANGE_Y - wayPoints.get(i).y) * (SCREEN_HEIGHT / RANGE_Y) - SCREEN_HEIGHT / 5;
            if (i == 0) {
                path.moveTo(x, y);
            } else {
                path.lineTo(x, y);
            }
        }

        ShapeDrawable mPath = new ShapeDrawable(new PathShape(path, SCREEN_WIDTH, SCREEN_HEIGHT));
        mPath.getPaint().setStyle(Paint.Style.STROKE);
        mPath.getPaint().setColor(Color.MAGENTA);
        mPath.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        allDrawables.add(mPath);
    }

    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < allDrawables.size(); i++) {
            allDrawables.get(i).draw(canvas);
        }
    }

    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        SCREEN_WIDTH = xNew;
        SCREEN_HEIGHT = yNew;
    }
}
