package com.example.tweaty.gesturestest;

import android.os.Bundle;
import android.util.Log;


public class TapActivity extends TestActivity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setInfoDialog() {
        super.setInfoDialog();
        infoDialog.setTileId(R.string.title_activity_tap);
        infoDialog.setViewId(R.layout.fragment_instruction_tap);
    }

    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        Tap tap = new Tap(getApplicationContext(), mDisplayWidth, mDisplayHeight, mSize, this);
        //tap.setListener(this);
        mFrame.addView(tap);
        startTime = System.currentTimeMillis();
    }

    @Override
    //public void onActionComplete() {
    public void onActionComplete(boolean isCorrect, int precision) {
        time = System.currentTimeMillis() - startTime;
        Log.i("Time", String.valueOf(time));
        DataHolder.getInstance().addTest(new TestData(1, counter, time, isCorrect, precision));
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
