package com.tsb.skribbl.model.game;

public class DrawingLine {
    private final float startX;
    private final float startY;
    private final float endX;
    private final float endY;

    public DrawingLine(float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }

    public float getStartX() {
        return startX;
    }

    public float getStartY() {
        return startY;
    }

    public float getEndX() {
        return endX;
    }

    public float getEndY() {
        return endY;
    }
}
