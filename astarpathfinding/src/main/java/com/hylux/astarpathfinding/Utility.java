package com.hylux.astarpathfinding;

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
}
