package fi.tuni.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

class MyPaintView extends View {
    private Canvas canvas;
    private Bitmap bm;

    private Path path;
    private Paint paintD;
    private Paint paintC;

    private float objectSize;
    private float brushSize;
    private int color;

    private int drawMode;
    private boolean fill;

    public MyPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paintD = new Paint();
        paintC = new Paint(Paint.ANTI_ALIAS_FLAG);

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
    }

    public void clear() {
        canvas.drawColor(0xffffffff, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void setColor(int color) {
        this.color = color;
        paintD.setColor(color);
    }

    public int getColor() {
        return color;
    }

    public void setObjectSize(float objectSize) {
        this.objectSize = objectSize;
    }

    public void setBrushSize(float brushSize) {
        this.brushSize = brushSize;
        paintD.setStrokeWidth(brushSize);
    }

    public void setFill(boolean fill) {
        this.fill = fill;

        if (fill) {
            paintD.setStyle(Paint.Style.FILL_AND_STROKE);
        } else {
            paintD.setStyle(Paint.Style.STROKE);
        }
    }

    public boolean isFill() {
        return fill;
    }

    public float getObjectSize() {
        return objectSize;
    }

    public float getBrushSize() {
        return brushSize;
    }

    public void setDrawMode(int drawMode) {
        this.drawMode = drawMode;

        if (drawMode == 0) {
            paintD.setStyle(Paint.Style.STROKE);
            paintD.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else {
            paintD.setXfermode(null);
        }
    }
}