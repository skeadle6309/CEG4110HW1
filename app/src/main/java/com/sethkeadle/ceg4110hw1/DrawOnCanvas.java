package com.sethkeadle.ceg4110hw1;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;

public class DrawOnCanvas extends View {
    //variables
    private static int MY_PERMISSIONS_WRITE_EXTERNAL_STORAGE = 1;
    private static final float TOLERANCE = 5; //to check for valid user input not motion blurr\
    private int width, height;
    Context mContext;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private LinkedList<Path> mPathList;
    private LinkedList<Paint> mPaintList;
    private Path mPath;
    private Paint mPaint;
    private float mX,mY;

    //constructor
    public DrawOnCanvas(Context context) {
        super(context);
        mContext = context;
        //create a drawing path
        mPath = new Path();
        mPathList = new LinkedList<Path>();
        mPaintList = new LinkedList<Paint>();

        //create a paint color
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);

        //add path and paint to list
        mPaintList.addLast(mPaint);
        mPathList.addLast(mPath);


    }

    //public function
    public void clearCanvas()
    {
        mPath.reset();
        for (int i = 0 ; i < mPathList.size(); i++)
        {
            mPathList.get(i).reset();
        }
        invalidate();
    }

    public void setColor(int color)
    {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
        mPath = new Path();
        mPathList.addLast(mPath);
        mPaintList.addLast(mPaint);
    }

    public void checkPermision(String permission)
    {
        int check = ContextCompat.checkSelfPermission(mContext, permission);
        if (check == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(mContext, "granted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(mContext, "denied", Toast.LENGTH_SHORT).show();
        }
    }
    public void save(View view)
    {
        checkPermision(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        view.setDrawingCacheEnabled(true);

        File file = Environment.getExternalStorageDirectory();
        File newFile = new File(file, "test.jpg");

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(mContext,
                    "Save Bitmap: " + fileOutputStream.toString(),
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mContext,
                    "Something wrong: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext,
                    "Something wrong: " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

    }

    //private functions
    //set up a new drawing canvas on load or on rotation. also make suitable for any android device
    @Override
    protected void onSizeChanged(int width, int height, int oWidth, int oHeight)
    {
        super.onSizeChanged(width, height, oWidth, oHeight);

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);


        //draw onto the canvas


        for (int i = 0; i < mPathList.size(); i++)
        {
            canvas.drawPath(mPathList.get(i), mPaintList.get(i));
        }

    }

    //when the finger starts to move across the screen get the path information
    private void startPath(float x, float y)
    {
        mPath.moveTo(x,y);
        mX = x;
        mY = y;
    }

    //a function to verify that the move was a desired move by the user
    private void movePath(float x, float y)
    {
        float dx = Math.abs(x -mX);
        float dy = Math.abs(x - mY);

        if (dx >= TOLERANCE || dy >= TOLERANCE)
        {
            mPath.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
            mX = x;
            mY = y;
        }
    }


    private void upTouch()
    {
        mPath.lineTo(mX, mY);
    }

    //override the on touch event

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                {
                    startPath(x, y);
                    invalidate();
                    break;
                }
            case MotionEvent.ACTION_MOVE:
                {
                    movePath(x, y);
                    invalidate();
                    break;
                }
            case MotionEvent.ACTION_UP:
                {
                    upTouch();
                    invalidate();
                    break;
                }
        }
        return true;
    }


}
