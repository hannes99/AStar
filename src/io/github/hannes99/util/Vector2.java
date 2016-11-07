package io.github.hannes99.util;

/**
 * Created by robert on 11/7/16.
 */
public class Vector2 {

    private double x, y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 v) {
        return add(v.x, v.y);
    }

    public Vector2 add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

}
