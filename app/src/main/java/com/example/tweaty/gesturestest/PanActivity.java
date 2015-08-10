package com.example.tweaty.gesturestest;

import android.os.Bundle;


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
    }

    @Override
    public void onActionComplete() {
        counter++;
        text.setText(msg + " " + counter + " z " + mTestsNumber);
        if (counter >= mTestsNumber) {

            setResult(RESULT_OK);
            finish();
        }
    }
}