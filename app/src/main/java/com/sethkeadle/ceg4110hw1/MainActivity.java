package com.sethkeadle.ceg4110hw1;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ConstraintLayout constraintLayout;
    private RelativeLayout relativeLayout;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        requestAppPermissions();
    }

    public void changeBackgroundColor(View view) {
        int colorNew = randomColorGenerator();
        //set random color in text box
        EditText fontBoxText = (EditText)findViewById(R.id.textBox);
        fontBoxText.setTextColor(colorNew);
        setCurrentTextColorLabel(fontBoxText);
    }
    private int randomColorGenerator(){
        Random rnd = new Random();
        int colorNew = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return colorNew;
    }
    public void setCurrentTextColorLabel(EditText fontBoxText)
    {
        int currentColor = fontBoxText.getCurrentTextColor();
        int hexCurrColor = (0xFFFFFF & currentColor);
        String colorStringHex = String.format("#%06X", hexCurrColor);

        //log out the hex color string
        //Log.i("hexColor", colorStringHex);

        //rgb from hex
        int[] rgb = getRGB(hexCurrColor);
//        for (int i=0; i<rgb.length; i++)
//        {
//            Log.i("rgbColor " + String.valueOf(i) , String.valueOf(rgb[i]));
//        }

        String finalOut = "COLOR: " + rgb[0] + "r, " + rgb[1] + "g, " + rgb[2] + "b, " + colorStringHex;
        //Log.i("final Out", finalOut);

        //final Out to colorPrintOutLabel
        //Toast.makeText(MainActivity.this, finalOut, Toast.LENGTH_LONG).show();
        TextView label = (TextView) findViewById(R.id.colorLabel);
        label.setText(finalOut);
    }

    public static int[] getRGB(final int hex)
    {
        int r = (hex & 0xFF0000) >> 16;
        int g = (hex & 0xFF00) >> 8;
        int b = (hex & 0xFF);
        return new int[] {r, g, b};
    }

    public void goToSecondPage(View view)
    {
        startActivity(new Intent(MainActivity.this, Main2Activity.class));
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



}
