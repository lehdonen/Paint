package fi.tuni.paint;

import android.graphics.Paint;

/**
 * Class for storing circle attributes.
 */
public class MyCircle {
    /**
     * Floats to store coordinates.
     */
    private float x, y;

    /**
     * Float to store size.
     */
    private float objectSize;

    /**
     * Variable to store paint.
     */
    private Paint paint;

    /**
     * Constructor for MyCircle object.
     *
     * @param x Used to input x coordinate.
     * @param y Used to input y coordinate.
     * @param objectSize Used to input object size.
     * @param paint Used to input paint.
     */
    public MyCircle(float x, float y, float objectSize, Paint paint) {
        this.x = x;
        this.y = y;
        this.objectSize = objectSize;
        this.paint = paint;
    }

    /**
     * Method for getting x value.
     *
     * @return x value.
     */
    public float getX() {
        return x;
    }

    /**
     * Method for getting y value.
     *
     * @return y value.
     */
    public float getY() {
        return y;
    }

    /**
     * Method for getting object size value.
     *
     * @return objectSize value.
     */
    public float getObjectSize() {
        return objectSize;
    }

    /**
     * Method for getting paint.
     *
     * @return Paint stored in MyCircle object.
     */
    public Paint getPaint() {
        return paint;
    }
}
