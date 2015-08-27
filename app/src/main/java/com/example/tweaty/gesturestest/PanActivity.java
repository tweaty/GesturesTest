package com.example.tweaty.gesturestest;

import android.os.Bundle;
import android.util.Log;


public class PanActivity extends TestActivity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    private float mTolerance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initSettings() {
        super.initSettings();
        mTolerance = sharedPrefs.getInt("key_tolerancePan", 5);
    }

    @Override
    protected void setInfoDialog() {
        super.setInfoDialog();
        infoDialog.setTileId(R.string.title_activity_pan);
        infoDialog.setViewId(R.layout.fragment_instruction_pan);
    }

    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        Pan pan = new Pan(getApplicationContext(), mDisplayWidth, mDisplayHeight, mSize, mTolerance, this);
//        pan.setListener(this);
        mFrame.addView(pan);
        startTime = System.currentTimeMillis();
    }

    @Override
    public void onActionComplete(boolean isCorrect, int precision) {
        time = System.currentTimeMillis() - startTime;
        Log.i("Time", String.valueOf(time));
        DataHolder.getInstance().addTest(new TestData(2, counter, time, isCorrect, precision));
        counter++;
        if (counter > mTestsNumber){
            setResult(RESULT_OK);
            finish();
            //DataHolder.getInstance(); do sprawdzania brakepoint
        }
        else{
            text.setText(msg + " " + counter + " z " + mTestsNumber);
            startTime = System.currentTimeMillis();
        }
    }
}