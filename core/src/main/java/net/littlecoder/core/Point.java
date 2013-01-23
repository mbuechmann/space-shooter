package net.littlecoder.core;

import java.awt.geom.Point2D;

class Point extends Point2D.Float {

    private float originalX;
    private float originalY;

    public Point(float x, float y) {
        originalX = x;
        originalY = y;
        this.x = x;
        this.y = y;
    }

    public Point transform(float angle, float translationX, float translationY) {
        x =
                (float) (Math.cos(angle) * originalX) +
                        (float) (Math.sin(angle) * originalY);
        y =
                -(float) (Math.sin(angle) * originalX) +
                        (float) (Math.cos(angle) * originalY);

        x += translationX;
        y += translationY;

        return this;
    }

}
