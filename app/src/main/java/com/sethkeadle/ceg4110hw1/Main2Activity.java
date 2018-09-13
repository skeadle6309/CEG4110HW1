package com.sethkeadle.ceg4110hw1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.byox.drawview.enums.DrawingCapture;
import com.byox.drawview.views.DrawView;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Main2Activity extends AppCompatActivity implements ColorPickerDialogListener {
    private DrawView mDrawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mDrawView = (DrawView)findViewById(R.id.draw_view);
        requestAppPermissions();

    }
    public void goToMain(View view)
    {
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
    }

    public void clearPage(View view) {
        mDrawView.restartDrawing();
    }

    public void pickColor(View view) {
        ColorPickerDialog.newBuilder().setColor(Color.BLACK).show(this);
    }
    @Override
    public void onColorSelected(int dialogId, int color) {
        mDrawView.setDrawColor(color);
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }

    public void saveImage(View view) {
        Bitmap previewBitmap;
        String previewFormat;
        if (mDrawView.isSaveEnabled())
        {
            Toast.makeText(this, "save enabled", Toast.LENGTH_SHORT).show();
            view.setDrawingCacheEnabled(true);

            File filePath = Environment.getExternalStorageDirectory();
            File imageFile = new File(filePath, "test.png");
            try{FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
                Object[] returnedObj = mDrawView.createCapture(DrawingCapture.BITMAP);
                previewBitmap = (Bitmap)returnedObj[0];
                previewBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(this, imageFile.toString(), Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                Toast.makeText(this, "FNF", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "IO", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "save disabled", Toast.LENGTH_SHORT).show();
        }

    }

    private void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 112); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    //---------OLD STUFF SALVAGE WHAT I NEED------------------

//    public void pickColor(View view) {
//        ColorPickerDialog.newBuilder().setColor(Color.BLACK).show(this);
//    }

//    public void clearPage(View view) {
//        drawView.clearCanvas();
//    }
//    public void saveImage(View view) {
//        drawView.save(view);
//    }

//    @Override
//    public void onColorSelected(int dialogId, int color) {
//        drawView.setColor(color);
//    }
//
//    @Override
//    public void onDialogDismissed(int dialogId) {
//
//    }
}
