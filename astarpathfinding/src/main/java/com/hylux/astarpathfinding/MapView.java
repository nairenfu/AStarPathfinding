package com.hylux.astarpathfinding;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ShapeDrawable;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapView extends View {

    private Grid grid;

    private int width, height;

    private VectorDrawables drawables;

    private List<Vector> vectors;

    public MapView(Context context, Grid grid) {
        super(context);

        this.grid = grid;

        width = Resources.getSystem().getDisplayMetrics().widthPixels;
        height = Resources.getSystem().getDisplayMetrics().heightPixels;

        this.drawables = new VectorDrawables(width, height);

        this.vectors = new ArrayList<>(grid.getVectors().values());
    }

    public void Draw(List<Vector> vectors) {
        for (Vector vector : vectors) {
            
        }
    }
}
