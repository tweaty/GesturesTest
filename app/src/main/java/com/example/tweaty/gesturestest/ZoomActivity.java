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
        mTolerance = sharedPrefs.getInt("key_tolerancePaS", 5);
    }

    @Override
    protected void setInfoDialog() {
        super.setInfoDialog();
        infoDialog.setTileId(R.string.title_activity_zoom);
        infoDialog.setViewId(R.layout.fragment_instruction_zoom);
    }

    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        Zoom zoom = new Zoom(getApplicationContext(), mDisplayWidth, mDisplayHeight, mTolerance, this);
        //zoom.setListener(this);
        mFrame.addView(zoom);
        startTime = System.currentTimeMillis();
    }

    @Override
    //public void onActionComplete() {
    public void onActionComplete(boolean isCorrect, int precision) {
        time = System.currentTimeMillis() - startTime;
        Log.i("Time", String.valueOf(time));
        DataHolder.getInstance().addTest(new TestData(3, counter, time, isCorrect, precision));
        counter++;
        if (counter > mTestsNumber){
            setResult(RESULT_OK);
            finish();
//            DataHolder.getInstance(); //do sprawdzania brakepoint
        }
        else{
            text.setText(msg + " " + counter + " z " + mTestsNumber);
            startTime = System.currentTimeMillis();
        }
    }
}