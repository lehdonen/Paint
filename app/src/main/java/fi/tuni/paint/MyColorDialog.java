package fi.tuni.paint;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

public class MyColorDialog extends Dialog {
    private int r, g, b;
    private int initialColor;
    private int colorToBe;
    private OnColorChangedListener listener;


    public interface OnColorChangedListener {
        void OnColorChanged(int color);
    }

    public MyColorDialog(Context context, OnColorChangedListener onColorChangedListener, int initialColor) {
        super(context);
        this.initialColor = initialColor;
        this.listener = onColorChangedListener;
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.color_picker_dialog);
        setTitle("Pick a Color");
        setCancelable(true);


        final ImageView colorView = findViewById(R.id.newColor);
        colorView.setBackgroundColor(initialColor);

        SeekBar rBar = findViewById(R.id.rBar);
        rBar.setProgress(getR());
        rBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setR(progress);
                colorView.setBackgroundColor(getColorToBe());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar gBar = findViewById(R.id.gBar);
        gBar.setProgress(getG());
        gBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setG(progress);
                colorView.setBackgroundColor(getColorToBe());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBar bBar = findViewById(R.id.bBar);
        bBar.setProgress(getB());
        bBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                setB(progress);
                colorView.setBackgroundColor(getColorToBe());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setR(int r) {
        this.r = r;
        setColorToBe(r, g, b);
    }

    public void setG(int g) {
        this.g = g;
        setColorToBe(r, g, b);
    }

    public void setB(int b) {
        this.b = b;
        setColorToBe(r, g, b);
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public void setColorToBe(int r, int g, int b) {
        this.colorToBe = Color.rgb(r, g, b);

        listener.OnColorChanged(getColorToBe());
    }

    public int getColorToBe() {
        return this.colorToBe;
    }

}

