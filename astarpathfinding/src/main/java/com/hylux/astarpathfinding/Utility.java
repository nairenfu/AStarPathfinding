package com.hylux.astarpathfinding;

import android.graphics.Point;
import android.util.Log;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Utility {

    public static final int MANHATTAN = 0, EUCLIDEAN = 1;

    public static float round(float value, int decimalPlaces) {
        int pow = 10;
        for (int i = 1; i < decimalPlaces; i++) {
            pow *= 10;
        }
        float tmp = value * pow;
        float tmpSub = tmp - (int) tmp;

        return ((float) ((int) (
                value >= 0
                        ? (tmpSub >= 0.5f ? tmp + 1 : tmp)
                        : (tmpSub >= -0.5f ? tmp : tmp - 1)
        ))) / pow;
    }

    public static float edgeDistance(Vector a, Vector b, int type) {
        float dx = abs(a.x - b.x);
        float dy = abs(a.y - b.y);
        switch (type) {
            case 0: return dx + dy;

            case 1: return round((float) sqrt(dx * dx + dy * dy), 2);

            default: return dx + dy;
        }
    }

    public static Point normalToAndroidPosition(Point normal, Point dimensions, Point xRange, Point yRange, int border) {
        //Log.d("NORMAL: ", normal.toString());
        Point range = new Point(xRange.y-xRange.x, yRange.y-yRange.x);
        Point unit = new Point((dimensions.x-2*border)/range.x, (dimensions.y-2*border)/range.y);
        Point origin = new Point(unit.x*(-xRange.x)+border, unit.y*yRange.x+dimensions.y-border);
        Point android = new Point(origin.x+unit.x*normal.x, origin.y-unit.y*normal.y);
        //Log.d("ANDROID: ", android.toString());
        return android;
    }
}
