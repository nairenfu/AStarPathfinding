package com.hylux.astarpathfinding;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
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

public class GridView extends View {

    private Grid grid;
    private List<ShapeDrawable> allDrawables;

    private final int SCREEN_HEIGHT, SCREEN_WIDTH;

    //TODO CHANGE HARDCODED STUFF TO REAL CODE
    public GridView(Context context, Grid grid) {
        super(context);
        SCREEN_HEIGHT = Resources.getSystem().getDisplayMetrics().heightPixels;
        SCREEN_WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
        allDrawables = new ArrayList<>();

        Log.d("VECTOR HEIGHT", String.valueOf(SCREEN_HEIGHT));
        Log.d("VECTOR WIDTH", String.valueOf(SCREEN_WIDTH));

        int X_SCALE = 10 - (-3) + 8, Y_SCALE = 10 - 0 + 8;

        this.grid = grid;
        Collections.sort(this.grid.getAllVectors(), Vector.IdComparator);
        Log.d("VECTOR ALL", this.grid.getAllVectors().toString());

        for (int i = 0; i < this.grid.getAllVectors().size(); i++) {
            Log.d("VECTOR", this.grid.getAllVectors().get(i).toString());
            float xPosition = this.grid.getAllVectors().get(i).x * SCREEN_WIDTH / X_SCALE + SCREEN_WIDTH / 2;
            Log.d("VECTOR POS", String.valueOf(xPosition));
            float yPosition = this.grid.getAllVectors().get(i).y * SCREEN_HEIGHT / Y_SCALE + SCREEN_HEIGHT / 20;
            Log.d("VECTOR POS", String.valueOf(yPosition));
            ShapeDrawable mCircle = new ShapeDrawable(new OvalShape());
            mCircle.setBounds((int) xPosition - 5,
                    (int) yPosition - 5,
                    (int) xPosition + 5,
                    (int) yPosition + 5);
            allDrawables.add(mCircle);

            for (int n = 0; n < this.grid.getAllVectors().get(i).getNeighbours().size(); n++) {
                Log.d("VECTOR N", String.valueOf(n));
                Log.d("VECTOR NEIGH", this.grid.getAllVectors().get(i).getNeighbours().toString());
                Log.d("VECTOR LINE", this.grid.getAllVectors().get(i).getNeighbours().get(n).toString());
                float xb = this.grid.getAllVectors().get(i).getNeighbours().get(n).x * SCREEN_WIDTH / X_SCALE + SCREEN_WIDTH / 2;
                float yb = this.grid.getAllVectors().get(i).getNeighbours().get(n).y * SCREEN_HEIGHT / Y_SCALE + SCREEN_HEIGHT / 20;
                //TODO HOW TO DISPLAY LINE???
                Path line = new Path();
                line.moveTo(xPosition, yPosition);
                line.addPath(line, xb, yb);
                Log.d("VECTOR LINE", line.toString());
                ShapeDrawable mLine = new ShapeDrawable(new PathShape(line, abs(xPosition - xb), abs(yPosition - yb)));
                mLine.getPaint().setStyle(Paint.Style.STROKE);
                if (xb > xPosition) {
                    if (yb > yPosition) {
                        mLine.setBounds((int) xPosition, (int) yPosition, (int) xb, (int) yb);
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
                }
                allDrawables.add(mLine);
            }
        }
        Log.d("VECTOR DRAW", allDrawables.toString());
    }

    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < allDrawables.size(); i++) {
            allDrawables.get(i).draw(canvas);
        }
    }
}
