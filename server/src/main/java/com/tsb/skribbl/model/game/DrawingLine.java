package com.tsb.skribbl.model.game;

public class DrawingLine {
    private final float startX;
    private final float startY;
    private final float endX;
    private final float endY;
    private final String color;
    private final float width;

    public DrawingLine(float startX, float startY, float endX, float endY, String color, float width) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.color = color;
        this.width = width;
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

    public String getColor() {
        return color;
    }

    public float getWidth() {
        return width;
    }
}
