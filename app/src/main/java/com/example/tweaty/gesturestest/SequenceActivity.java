package com.example.tweaty.gesturestest;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;


public class SequenceActivity extends TestActivity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    private float mTolerancePan;
    private float mTolerancePaS;
    private String[] sequence = new String[3];
    HashMap<String, View> map = new HashMap();
    int sequenceElemnt = 0;
    boolean start = true;
    ArrayList<TestData> gestureDatas = new ArrayList<>();
    long gestureTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initSettings() {
        super.initSettings();
        mTolerancePan = sharedPrefs.getInt("key_tolerancePan", 5);
        mTolerancePaS = sharedPrefs.getInt("key_tolerancePaS", 5);
        sequence[0] = sharedPrefs.getString("key_sequence0", "0");
        sequence[1] = sharedPrefs.getString("key_sequence1", "1");
        sequence[2] = sharedPrefs.getString("key_sequence2", "2");
    }

    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        map.put("0", new Tap(getApplicationContext(), mDisplayWidth, mDisplayHeight, mSize, this));
        map.put("1", new Pan(getApplicationContext(), mDisplayWidth, mDisplayHeight, mSize, mTolerancePan, this));
        map.put("2", new Zoom(getApplicationContext(), mDisplayWidth, mDisplayHeight, mTolerancePaS, this));
        setGesture(sequence[0]);
        start = false;
        startTime = gestureTime = System.currentTimeMillis();
    }

    private void setGesture(String key){
        View gesture = map.get(key);
        if (!start) mFrame.removeViewAt(0);
        mFrame.addView(gesture);
    }

    @Override
    public void onActionComplete(boolean isCorrect, int precision) {
        gestureTime = System.currentTimeMillis() - gestureTime; // do poprawy
//        Log.i("Time", String.valueOf(time));
//        DataHolder.getInstance().addTest(new TestData(2, counter, time, isCorrect, precision));
        gestureDatas.add(new TestData(Integer.parseInt(sequence[sequenceElemnt]), gestureTime, isCorrect, precision));
        sequenceElemnt++;
        if (sequenceElemnt > 2) {
            sequenceElemnt = 0;
            time = System.currentTimeMillis() - startTime;
            DataHolder.getInstance().addTest(new TestData(4, counter, time, gestureDatas));
            DataHolder.getInstance();
            gestureDatas.clear();
            counter ++;

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
        setGesture(sequence[sequenceElemnt]);
        gestureTime = System.currentTimeMillis();
//        counter++;


    }
}