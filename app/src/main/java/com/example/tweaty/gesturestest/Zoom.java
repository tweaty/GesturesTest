package com.example.tweaty.gesturestest;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import java.util.Random;

public class Zoom extends View implements ScaleGestureDetector.OnScaleGestureListener{
    private ScaleGestureDetector mDetector;
    private Paint paint1 = new Paint();
    private Paint paint2 = new Paint();
    private float mX, mY, mX2, mY2, mSize1, mSize2, mResize, mWidth, mHeight;
    private float mTolerance;
    private float mScaleFactor = 1.0f;
    private Random random = new Random();
    private boolean zoomIn;

    protected IsActionCompleteListener listener;
    public void setListener(IsActionCompleteListener listener){
        this.listener = listener;
    }

    Zoom(Context context, float width, float height, float tolerance, IsActionCompleteListener listener){
        super(context);
        paint1.setColor(Color.BLACK);
        paint2.setColor(Color.RED);
        mWidth = width;
        mHeight = height;
        mTolerance = tolerance;
        setListener(listener);
        mDetector = new ScaleGestureDetector(getContext(), this);
        randomZoomOption();
    }


    public void randomZoomOption(){
        zoomIn = random.nextBoolean();
        mScaleFactor = 1.0f;
        setSize1(20);
        if (zoomIn){

            setSize2(10);
        }
        else{
            setSize2(30);
        }
    }

    public void setSize1(float size){ // metoda do ustawiania wielkosci kwadratu w oparciu o DPI tel, cos nie dziala jak nalezy
        mSize1 = HelperClass.cmToDpi(size, getResources().getDisplayMetrics());
        Log.i("DPI", " size: " + mSize1);

    }

    public void setSize2(float size) { // metoda do ustawiania wielkosci kwadratu w oparciu o DPI tel, cos nie dziala jak nalezy
        mSize2 = HelperClass.cmToDpi(size, getResources().getDisplayMetrics());
        mResize = mSize2;
        Log.i("DPI", " size: " + mSize2);

    }

    public boolean isInRange(float distance) {
        if (distance <= mTolerance) return true;
        else return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDetector.onTouchEvent(event);
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mX = ((mWidth/2)-(mSize1/2));
        mY = ((mHeight/2)-(mSize1/2));
        mX2 = Math.round((mWidth/2)-(mResize /2)); // moga wystepowaæ wartoœci typu 18.5
        mY2 = Math.round((mHeight / 2) - (mResize / 2)); // to samo

        if (zoomIn){
            canvas.drawRect(mX, mY, mX + mSize1, mY + mSize1, paint1);
            canvas.drawRect(mX2, mY2, mX2 + mResize, mY2 + mResize, paint2);
        }else{
            canvas.drawRect(mX2, mY2, mX2 + mResize, mY2 + mResize, paint2);
            canvas.drawRect(mX, mY, mX + mSize1, mY + mSize1, paint1);
        }

        Log.i("draw", "x1:" + mX + " y1:" + mY + " x2:"+mX2+" y2:"+mY+" size1:"+mSize1+" mResize:" + mResize);
    }

    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        mScaleFactor *= scaleGestureDetector.getScaleFactor();

        // Don't let the object get too small or too large.
        mScaleFactor = Math.max(0.25f, Math.min(mScaleFactor, 2.5f));
        mResize = Math.round(mSize2 * mScaleFactor);
        invalidate();

        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        //isStart = true;
        Log.i("start", "start");
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
        float precision = HelperClass.distance(mX, mY, mX2, mY2);
        boolean isCorrect = isInRange(precision);
        Log.i("statystyki", "distance: " + precision + " tolerancja: " + String.valueOf(isCorrect));
        listener.onActionComplete(isCorrect, (int) precision);
        randomZoomOption();
        invalidate();
        Log.i("start", "koniec");
    }
}
