package fi.tuni.paint;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

/**
 * Main activity of paint app.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Dialog for picking draw mode.
     */
    private Dialog dialog;

    /**
     * Permission detection helper boolean.
     */
    private boolean galleryPermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        final MyPaintView mpw = findViewById(R.id.mpw);
        MyColorDialog colorDialog = new MyColorDialog(this, new MyColorDialog.OnColorChangedListener() {
            @Override
            public void OnColorChanged(int color) {
                mpw.setBgColor(color);
            }

            @Override
            public void SaveRGB(int r, int g, int b) {
            }
        }, mpw.getBgColor(), 255, 255, 255, 1);

        colorDialog.show();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        final MyPaintView mpw;
        final MyColorDialog colorDialog;

        switch (item.getItemId()) {
            case (R.id.clearCanvas) :
                mpw = findViewById(R.id.mpw);
                mpw.clear();
                return true;
            case (R.id.chooseColor) :
                mpw = findViewById(R.id.mpw);
                colorDialog = new MyColorDialog(this, new MyColorDialog.OnColorChangedListener() {
                    @Override
                    public void OnColorChanged(int color) {
                        mpw.setColor(color);
                    }

                    @Override
                    public void SaveRGB(int r, int g, int b) {
                        mpw.setRGB(r, g, b);
                    }
                }, mpw.getColor(), mpw.getR(), mpw.getG(), mpw.getB(), 0);

                colorDialog.show();

                return true;
            case (R.id.drawMode) :
                mpw = findViewById(R.id.mpw);

                dialog = new Dialog(this);
                dialog.setContentView(R.layout.draw_mode_dialog);
                dialog.setTitle("Set Drawing Mode");
                dialog.setCancelable(true);
                dialog.show();

                SeekBar sizeBar = dialog.findViewById(R.id.sizeBar);
                sizeBar.setProgress((int) mpw.getBrushSize());
                sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mpw.setBrushSize(progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                SeekBar objectSizeBar = dialog.findViewById(R.id.objectSizeBar);
                objectSizeBar.setProgress((int) mpw.getObjectSize());
                objectSizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        mpw.setObjectSize(progress);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                CheckBox fillBox = dialog.findViewById(R.id.fillBox);
                fillBox.setChecked(mpw.isFill());
                fillBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            mpw.setFill(true);
                        } else {
                            mpw.setFill(false);
                        }
                    }
                });

                return true;
            case (R.id.eraser) :
                mpw = findViewById(R.id.mpw);
                mpw.setDrawMode(0);
                return true;
            case (R.id.save) :
                if (galleryPermission) {
                    save();
                } else {
                    Toast.makeText(this, "No permission to use device gallery", Toast.LENGTH_LONG).show();
                }
                return true;
            case (R.id.undo) :
                mpw = findViewById(R.id.mpw);
                if (mpw.drawObjectsSize() != 0) {
                    mpw.undoLastAction();
                } else {
                    Toast.makeText(this, "Draw something first", Toast.LENGTH_LONG).show();
                }

                return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                galleryPermission = true;
            } else {
                galleryPermission = false;
            }
        }
    }

    /**
     * Method for saving drawing to device gallery.
     */
    public void save() {
        MyPaintView mpw = findViewById(R.id.mpw);

        MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),
                mpw.getBitMap(),"PaintDrawing","Paint drawing");
        Toast.makeText(this, "Drawing saved to device gallery", Toast.LENGTH_LONG).show();
    }

    /**
     * Method for changing paints drawing mode.
     *
     * @param v Used to input pressed button.
     */
    public void onDrawModeButtonClick(View v) {
        MyPaintView mpw = findViewById(R.id.mpw);

        switch (v.getId()) {
            case (R.id.b1) :
                mpw.setDrawMode(1);
                break;
            case (R.id.b2) :
                mpw.setDrawMode(2);
                break;
            case (R.id.b3) :
                mpw.setDrawMode(3);
                break;
        }
    }
}
