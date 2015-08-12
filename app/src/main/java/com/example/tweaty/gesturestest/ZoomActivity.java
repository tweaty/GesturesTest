package com.example.tweaty.gesturestest;

import android.os.Bundle;
import android.util.Log;


public class ZoomActivity extends TestActivity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    private float mTolerance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initSettings() {
        super.initSettings();
        mTolerance = sharedPrefs.getInt("key_tolerance", 5);
    }

    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        Zoom zoom = new Zoom(getApplicationContext(), mDisplayWidth, mDisplayHeight, mTolerance);
        zoom.setListener(this);
        mFrame.addView(zoom);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onActionComplete() {
        counter++;

        endTime = System.currentTimeMillis() - startTime;
        startTime = System.currentTimeMillis();
        Log.i("Time", String.valueOf(endTime));

        if (counter > mTestsNumber){

            setResult(RESULT_OK);
            finish();
        }
        else{

            text.setText(msg + " " + counter + " z " + mTestsNumber);

        }
    }
}