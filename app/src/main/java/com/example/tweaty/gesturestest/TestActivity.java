package com.example.tweaty.gesturestest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TestActivity extends Activity{

    protected RelativeLayout mFrame;
    protected TextView text;
    protected int mDisplayWidth, mDisplayHeight, mSize, mTestsNumber;
    protected int counter = 0;
    protected String msg;
    private InfoDialog infoDialog;
    protected SharedPreferences sharedPrefs;

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

        infoDialog = new InfoDialog();
        infoDialog.setCancelable(false);
        infoDialog.show(getFragmentManager(), "Info");

    }

    protected void setInfoDialogLayout(int layout){
        infoDialog.setViewId(layout);
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
