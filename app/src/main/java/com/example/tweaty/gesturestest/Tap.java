package com.example.tweaty.gesturestest;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class Tap extends View{

    Context context;
    Paint paint = new Paint();
    Paint text = new Paint();
    float mX, mY, mWidth, mHeight, mXcenter, mYcenter;
    float mSize;
    IsActionCompleteListener listener;

    Tap(Context context, float width, float height, int size){
        super(context);
        this.context = context;
        mWidth = width;
        mHeight = height;
        paint.setColor(Color.RED);
        text.setColor(Color.WHITE);
        text.setTextSize(50);
        text.setTextAlign(Paint.Align.CENTER);
        text.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        setSize(size);
        Log.i("pref", "size: " + size);
        setCo(randomPlace());
    }

    public void setListener(IsActionCompleteListener listener){
        this.listener = listener;
    }

    public void setCo(float x, float y){
        mX = x;
        mY = y;
        mXcenter = mX + mSize/2;
        mYcenter = mY + mSize/2;
    }
    public void setCo(Point p){
        mX = p.x;
        mY = p.y;
        mXcenter = mX + mSize/2;
        mYcenter = mY + mSize/2;
    }

    public Point randomPlace(){
        Log.i("test", "size: " + mSize);
        Random r = new Random();
        Log.i("test", "width: " + mWidth + "; height: " + mHeight);
        int xx = r.nextInt((int)(mWidth-mSize));
        int yy = r.nextInt((int)(mHeight-mSize));
        Log.i("test", "x: " + xx + "; y: " + yy);
        return new Point(xx, yy);
    }

    public void setSize(float size){ // metoda do ustawiania wielkosci kwadratu w oparciu o DPI tel
        mSize = HelperClass.cmToDpi(size, getResources().getDisplayMetrics());
        Log.i("DPI", " size: " + mSize);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(mX, mY, mX +mSize, mY+mSize, paint);
        canvas.drawText("1", mXcenter, (mYcenter - ((text.descent() + text.ascent()) / 2)), text);
    }
    public boolean intersect(float x, float y){
        return x >= mX && x <= mX + mSize && y >= mY && y <= mY + mSize;
    }
/*    public int distance(float x2, float y2){
        return (int) Math.sqrt( Math.pow((mXcenter - x2), 2) + Math.pow((mYcenter-y2), 2));
    }*/
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isCorrect = intersect(event.getX(), event.getY());
        int precision = (int) HelperClass.distance(mXcenter, mYcenter, event.getX(), event.getY());
        Log.i("TAP Intersect", String.valueOf(isCorrect));
        Log.i("TAP precision", String.valueOf(precision));
        setCo(randomPlace());
        listener.onActionComplete(isCorrect, precision);
        invalidate();
        return super.onTouchEvent(event);
    }


}
