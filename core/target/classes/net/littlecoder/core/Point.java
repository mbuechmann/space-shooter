package net.littlecoder.core;

class Point {
    
    private float originalX;
    private float originalY;
    private float currentX;
    private float currentY;

    private float rotation;
    private float translationX;
    private float translationY;
    private boolean dirty;

    public Point(float x, float y) {
	originalX = x;
	originalY = y;
	currentX = x;
	currentY = y;

	rotation = 0f;
	translationX = 0f;
	translationY = 0f;
	dirty = false;
    }

    public Point setRotation(float angle) {
	dirty = true;
	rotation = angle;

	return this;
    }

    public Point setTranslation(float x, float y) {
	dirty = true;
	translationX = x;
	translationY = y;

	return this;
    }

    public float x() {
	if (dirty)
	    recalc();
	return currentX;
    }

    public float y() {
	if (dirty)
	    recalc();
	return currentY;
    }

    private void recalc() {
	dirty = false;
	currentX = (float)(Math.cos(rotation) * originalX) + (float)(Math.sin(rotation) * originalY);
	currentY = -(float)(Math.sin(rotation) * originalX) + (float)(Math.cos(rotation) * originalY);
	currentX += translationX;
	currentY += translationY;
    }

}
