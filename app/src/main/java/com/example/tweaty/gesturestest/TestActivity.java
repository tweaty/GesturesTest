package com.example.tweaty.gesturestest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity{

    protected RelativeLayout mFrame;
    protected TextView text;
    protected int mDisplayWidth, mDisplayHeight, mSize, mTestsNumber;
    protected int counter = 1;
    protected String msg;
    protected InfoDialog infoDialog = new InfoDialog();
    protected SharedPreferences sharedPrefs;
    protected long startTime, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //no status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);

        mFrame = (RelativeLayout) findViewById(R.id.frame);
        text = (TextView) findViewById(R.id.text);
        msg = getString(R.string.probe_counter);

        initSettings();

        text.setText(msg + " " + counter + " z " + mTestsNumber);
        setInfoDialog();
        infoDialog.show(getFragmentManager(), "InfoDialog");

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.back_button_dialog, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    protected void setInfoDialog(){
        infoDialog.setCancelable(false);
        infoDialog.setPositiveButtonText(R.string.begin);
    }


    protected void initSettings(){
        sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        mSize = sharedPrefs.getInt("key_element_size", 10);
        mTestsNumber = sharedPrefs.getInt("key_test_numeber", 30);

        boolean fullscreen = sharedPrefs.getBoolean("key_checkbox_fullscreen", true);
        if (!fullscreen) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mFrame.getLayoutParams();
            lp.height = sharedPrefs.getInt("key_width_picker", 600);
            lp.width = sharedPrefs.getInt("key_height_picker", 480);
            mFrame.setLayoutParams(lp);
        }
    }
}
