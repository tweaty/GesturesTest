package com.example.tweaty.gesturestest;

import android.os.Bundle;
import android.util.Log;


public class PanActivity extends TestActivity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        Pan pan = new Pan(getApplicationContext(), mDisplayWidth, mDisplayHeight, mSize);
        pan.setListener(this);
        mFrame.addView(pan);
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