package com.example.tweaty.gesturestest;

import android.content.Intent;
import android.os.Bundle;


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
    }

    @Override
    public void onActionComplete() {
        counter++;
        text.setText(msg + " " + counter + " z " + mTestsNumber);
        if (counter >= mTestsNumber){

            setResult(RESULT_OK);
            finish();
        }
    }

}
