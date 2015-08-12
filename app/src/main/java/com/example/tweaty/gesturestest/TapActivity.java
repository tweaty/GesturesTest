package com.example.tweaty.gesturestest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


public class TapActivity extends TestActivity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setInfoDialogLayout(R.layout.age_dialog);
    }

    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        Tap tap = new Tap(getApplicationContext(), mDisplayWidth, mDisplayHeight, mSize);
        tap.setListener(this);
        mFrame.addView(tap);
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
