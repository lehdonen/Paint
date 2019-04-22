package fi.tuni.paint;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Class for storing path and paint.
 */
public class MyPath {
    /**
     * Variable to store paint.
     */
    private Paint paint;

    /**
     * Variable to store path.
     */
    private Path path;

    /**
     * Constructor for MyPath object.
     *
     * @param paint Used to input paint.
     * @param path Used to input path.
     */
    public MyPath(Paint paint, Path path) {
        this.paint = paint;
        this.path = path;
    }

    /**
     * Default constructor for MyPath object.
     */
    public MyPath() {

    }

    /**
     * Method for getting paint.
     *
     * @return Paint stored in MyPath object.
     */
    public Paint getPaint() {
        return paint;
    }

    /**
     * Method for getting path.
     *
     * @return Path stored in MyPath object.
     */
    public Path getPath() {
        return path;
    }
}
