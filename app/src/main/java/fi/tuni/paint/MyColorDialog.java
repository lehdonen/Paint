package fi.tuni.paint;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;

/**
 * Custom dialog for color changing.
 */
public class MyColorDialog extends Dialog {

    /**
     * Integer values for red, green and blue.
     */
    private int r, g, b;

    /**
     * Integer value for initial color.
     */
    private int initialColor;

    /**
     * Integer value for new color.
     */
    private int colorToBe;

    /**
     * Helper integer to decide dialog layout.
     */
    private int dialogMode;

    /**
     * Color changed listener.
     */
    private OnColorChangedListener listener;


    /**
     * Public interface for listening color changes.
     */
    public interface OnColorChangedListener {
        void OnColorChanged(int color);
        void SaveRGB(int r, int g, int b);
    }

    /**
     * Constructor for MyColorDialog class.
     *
     * @param context Used to input context.
     * @param onColorChangedListener Used to input color changed listener.
     * @param initialColor Used to input initial color.
     * @param initialR Used to input initial red value.
     * @param initialG Used to input initial green value.
     * @param initialB Used to input initial blue value.
     * @param dialogMode Used to input dialog content view mode.
     */
    public MyColorDialog(Context context, OnColorChangedListener onColorChangedListener, int initialColor, int initialR, int initialG, int initialB, int dialogMode) {
        super(context);
        this.initialColor = initialColor;
        this.listener = onColorChangedListener;
        this.r = initialR;
        this.g = initialG;
        this.b = initialB;
        this.dialogMode = dialogMode;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (dialogMode == 0) {
            setContentView(R.layout.color_picker_dialog);
        } else {
            setContentView(R.layout.bg_color_dialog);
        }

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

    /**
     * Method for setting new red value.
     *
     * @param r Used to input new red value.
     */
    public void setR(int r) {
        this.r = r;
        setColorToBe(r, g, b);
    }

    /**
     * Method for setting new green value.
     *
     * @param g Used to input new green value.
     */
    public void setG(int g) {
        this.g = g;
        setColorToBe(r, g, b);
    }

    /**
     * Method for setting new blue value.
     *
     * @param b Used to input new blue value.
     */
    public void setB(int b) {
        this.b = b;
        setColorToBe(r, g, b);
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
     * Method for setting new color preview.
     *
     * @param r Used to input new red value.
     * @param g Used to input new green value.
     * @param b Used to input new blue value.
     */
    public void setColorToBe(int r, int g, int b) {
        this.colorToBe = Color.rgb(r, g, b);

        listener.OnColorChanged(getColorToBe());
    }

    /**
     * Method for getting new color.
     *
     * @return Integer value of new color.
     */
    public int getColorToBe() {
        return this.colorToBe;
    }

    @Override
    protected void onStop() {
        super.onStop();
        listener.SaveRGB(getR(), getG(), getB());
    }
}

