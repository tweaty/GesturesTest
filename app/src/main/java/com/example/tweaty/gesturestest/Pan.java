package com.example.tweaty.gesturestest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;

public class Pan extends Tap {
    Paint paint2 = new Paint();
    float mX2, mY2, mX2center, mY2center, mTolercance;
    private boolean isIntersect = false;


    Pan(Context context, float width, float height, int size, float tolerance , IsActionCompleteListener listener) {
        super(context, width, height, size, listener);
        this.mTolercance = tolerance;
        paint2.setColor(Color.BLACK);
        setCo2(randomPlace());
    }

    public void setCo2(Point p){
        mX2 = p.x;
        mY2 = p.y;
        mX2center = mX2 + mSize/2;
        mY2center = mY2 + mSize/2;
    }

    public boolean intersectSecond(){
        return (mX < (mX2 + mSize))
                && ((mX + mSize) > mX2)
                && (mY < (mY2 + mSize))
                && ((mY + mSize) > mY2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(mX2, mY2, mX2 + mSize, mY2 + mSize, paint2);
        canvas.drawText("2", (mX2 + mSize / 2), mY2 + mSize / 2 - ((text.descent() + text.ascent()) / 2), text);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isIntersect = this.intersect(x,y);
                  /*  if (isIntersect){
                        this.setCo(x-50, y-50);
                        this.invalidate();
                    }*/
                Log.i("Intersect", String.valueOf(isIntersect));
                break;
            case MotionEvent.ACTION_MOVE:
                if (isIntersect) {
                    Log.i("test", "x: " + x + "; y: " + y);
                    setCo(x - mSize/2, y - mSize/2);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isIntersect) {
                    isIntersect = false;
                    int precision = (int)HelperClass.distance(mXcenter, mYcenter, mX2center, mY2center);
                    boolean isCorrect = (precision <= mTolercance);
                    Log.i("PAN IntersectSEC", String.valueOf(isCorrect));
                    Log.i("PAN precision", String.valueOf(precision));
                    listener.onActionComplete(isCorrect , precision);
                    setCo(randomPlace());
                    setCo2(randomPlace());
                    invalidate();
                }
                break;
        }
        return true;
    }


}
