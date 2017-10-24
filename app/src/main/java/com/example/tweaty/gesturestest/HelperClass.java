package com.example.tweaty.gesturestest;


import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

public class HelperClass {

    public static int cmToDpi(float size, Context context){
        //float totalDIP = metrics.densityDpi;
        //float inches = size / 25.4f;
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, size,
                context.getResources().getDisplayMetrics()));
        //return Math.round(inches * totalDIP);
    }

    public static float distance(float x1, float y1, float x2, float y2){
        float distance = Math.round(Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2)));
        Log.i("distance", " distance: " + distance);
        return distance;
    }
}
