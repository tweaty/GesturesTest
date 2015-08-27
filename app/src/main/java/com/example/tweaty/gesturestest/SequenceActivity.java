package com.example.tweaty.gesturestest;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;


public class SequenceActivity extends TestActivity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    private float mTolerancePan;
    private float mTolerancePaS;
    private int[] sequenceElements = new int[3];
    int elementPosition = 0;
    ArrayList<TestData> gestureData = new ArrayList<>();
    long sequenceTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initSettings() {
        super.initSettings();
        mTolerancePan = sharedPrefs.getInt("key_tolerancePan", 5);
        mTolerancePaS = sharedPrefs.getInt("key_tolerancePaS", 5);
        sequenceElements[0] = Integer.parseInt(sharedPrefs.getString("key_sequence0", "0"));
        sequenceElements[1] = Integer.parseInt(sharedPrefs.getString("key_sequence1", "1"));
        sequenceElements[2] = Integer.parseInt(sharedPrefs.getString("key_sequence2", "2"));
    }

    @Override
    protected void setInfoDialog() {
        super.setInfoDialog();
        infoDialog.setTileId(R.string.title_activity_sequence);
        infoDialog.setViewId(R.layout.fragment_instruction_sequence);
    }

    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        mFrame.addView(new Tap(getApplicationContext(), mDisplayWidth, mDisplayHeight, mSize, this), 0);
        mFrame.addView(new Pan(getApplicationContext(), mDisplayWidth, mDisplayHeight, mSize, mTolerancePan, this), 1);
        mFrame.addView(new Zoom(getApplicationContext(), mDisplayWidth, mDisplayHeight, mTolerancePaS, this), 2);
        setGesture(sequenceElements[0]);
        startTime = System.currentTimeMillis();
    }

    private void setGesture(int id){
        for(int i=0; i< sequenceElements.length; i++){
            mFrame.getChildAt(i).setVisibility(View.GONE);
        }
        mFrame.getChildAt(id).setVisibility(View.VISIBLE);
    }

    @Override
    public void onActionComplete(boolean isCorrect, int precision) {
        //if (!start) mFrame.removeView(map.get(sequenceElements[sequenceElemnt]));
        time = System.currentTimeMillis() - startTime;
        sequenceTime+=time;
        gestureData.add(new TestData(sequenceElements[elementPosition] + 1, time, isCorrect, precision));
        elementPosition++;
        if (elementPosition > sequenceElements.length - 1){
            DataHolder.getInstance().addTest(new TestData(4, counter, sequenceTime, gestureData));
            elementPosition = 0;
            counter++;
            sequenceTime = 0;
            gestureData.clear();

            if (counter <= mTestsNumber) {
                text.setText(msg + " " + counter + " z " + mTestsNumber);
                startTime = System.currentTimeMillis();
            } else {
                setResult(RESULT_OK);
                finish();
            }
        }
        setGesture(sequenceElements[elementPosition]);

    }
}