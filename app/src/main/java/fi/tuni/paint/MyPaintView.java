package fi.tuni.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Custom Paint view class.
 */
class MyPaintView extends View {
    /**
     * Drawing canvas.
     */
    private Canvas canvas;

    /**
     * Drawing bitmap.
     */
    private Bitmap bm;

    /**
     * Drawing path.
     */
    private Path path;

    /**
     * Drawing paint.
     */
    private Paint paintD;

    /**
     * Canvas paint.
     */
    private Paint paintC;

    /**
     * Drawable object size.
     */
    private float objectSize;

    /**
     * Drawing brush size.
     */
    private float brushSize;

    /**
     * Drawing color.
     */
    private int color;

    /**
     * Integer to store color before eraser mode.
     */
    private int colorBeforeEraser;

    /**
     * Integer to store background color.
     */
    private int bgColor;

    /**
     * Integer for drawing mode.
     */
    private int drawMode;

    /**
     * Boolean for fill stroke style.
     */
    private boolean fill;

    /**
     * Integer values for red, green and blue.
     */
    private int r, g, b;

    /**
     * Constructor for MyPaintView class.
     *
     * @param context Used to input context.
     * @param attrs Used to input attribute set.
     */
    public MyPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paintD = new Paint();
        paintC = new Paint(Paint.ANTI_ALIAS_FLAG);
        setDrawingCacheEnabled(true);

        paintC.setAntiAlias(true);

        brushSize = 10f;
        objectSize = 10f;
        color = 0xff000000;
        drawMode = 1;

        paintD.setStyle(Paint.Style.STROKE);
        paintD.setStrokeCap(Paint.Cap.ROUND);

        paintD.setColor(color);
        paintD.setStrokeWidth(brushSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bm, 0, 0, paintC);
        if (drawMode <= 1) {
            canvas.drawPath(path, paintD);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case (MotionEvent.ACTION_MOVE) :
                path.lineTo(x, y);
                break;
            case (MotionEvent.ACTION_DOWN) :
                path.moveTo(x, y);
                break;
            case (MotionEvent.ACTION_UP) :
                switch (drawMode) {
                    case (0) :
                        canvas.drawPath(path, paintD);
                        break;
                    case (1) :
                        canvas.drawPath(path, paintD);
                        break;
                    case (2) :
                        canvas.drawCircle(x, y, objectSize, paintD);
                        break;
                    case (3) :
                        canvas.drawRect(x, objectSize, objectSize, y, paintD);
                        break;
                }
                path.reset();
                invalidate();
                break;
            default :
                return false;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bm);
        canvas.drawColor(bgColor);
    }

    /**
     * Method for clearing canvas.
     */
    public void clear() {
        canvas.drawColor(bgColor);
        invalidate();
    }

    /**
     * Method for setting drawing color.
     *
     * @param color Used to input integer value of new color.
     */
    public void setColor(int color) {
        this.color = color;

        if (drawMode != 0) {
            paintD.setColor(color);
        }
    }

    /**
     * Method for setting background color.
     *
     * @param color Used to input integer value of new color.
     */
    public void setBgColor(int color) {
        this.bgColor = color;
        canvas.drawColor(color);
    }

    /**
     * Method for getting current drawing color.
     *
     * @return Current drawing color.
     */
    public int getColor() {
        return color;
    }

    /**
     * Method for setting size of drawable objects.
     *
     * @param objectSize Used to input new size.
     */
    public void setObjectSize(float objectSize) {
        this.objectSize = objectSize;
    }

    /**
     * Method for setting new brush size.
     *
     * @param brushSize Used to input new brush size.
     */
    public void setBrushSize(float brushSize) {
        this.brushSize = brushSize;
        paintD.setStrokeWidth(brushSize);
    }

    /**
     * Method for setting paint fill bool.
     *
     * @param fill Used to input new fill state.
     */
    public void setFill(boolean fill) {
        this.fill = fill;

        if (fill) {
            paintD.setStyle(Paint.Style.FILL_AND_STROKE);
        } else {
            paintD.setStyle(Paint.Style.STROKE);
        }
    }

    /**
     * Method for getting current fill state.
     *
     * @return Current fill state.
     */
    public boolean isFill() {
        return fill;
    }

    /**
     * Method for getting current drawable object size.
     *
     * @return Current drawable object size.
     */
    public float getObjectSize() {
        return objectSize;
    }

    /**
     * Method for getting current brush size.
     *
     * @return Current brush size.
     */
    public float getBrushSize() {
        return brushSize;
    }

    /**
     * Method for setting drawing mode.
     *
     * @param drawMode Used to input new drawing mode.
     */
    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;
        colorBeforeEraser = getColor();

        if (drawMode == 0) {
            if (isFill()) {
                paintD.setStyle(Paint.Style.FILL_AND_STROKE);
            } else {
                paintD.setStyle(Paint.Style.STROKE);
            }
            paintD.setColor(bgColor);
        } else {
            paintD.setColor(colorBeforeEraser);
        }
    }

    /**
     * Method for setting new r, g, b color values.
     *
     * @param r Used to input new red value.
     * @param g Used to input new green value.
     * @param b Used to input new blue value.
     */
    public void setRGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * Method for getting current red value.
     *
     * @return Current red value.
     */
    public int getR() {
        return r;
    }

    /**
     * Method for getting current green value.
     *
     * @return Current green value.
     */
    public int getG() {
        return g;
    }

    /**
     * Method for getting current blue value.
     *
     * @return Current blue value.
     */
    public int getB() {
        return b;
    }

    /**
     * Method for getting drawing as bitmap.
     *
     * @return Drawing as bitmap.
     */
    public Bitmap getBitMap() {
        return bm;
    }
}