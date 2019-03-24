package fi.tuni.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

class MyPaintView extends View {
    private Canvas canvas;
    private Bitmap bm;

    private Path path;
    private Paint paintD;
    private Paint paintC;

    private float size;
    private int color;


    public MyPaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        path = new Path();
        paintD = new Paint();
        paintC = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintC.setAntiAlias(true);

        size = 10f;
        color = 0xff000000;

        paintD.setStyle(Paint.Style.STROKE);
        paintD.setStrokeCap(Paint.Cap.ROUND);

        paintD.setColor(color);
        paintD.setStrokeWidth(size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bm, 0, 0, paintC);
        canvas.drawPath(path, paintD);
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
                canvas.drawPath(path, paintD);
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
    }

    public void setSize(float size) {
        this.size = size;
    }
}