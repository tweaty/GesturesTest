package com.example.tweaty.gesturestest;


import android.content.Context;
import android.view.View;

public class Gesture extends View{

    protected IsActionCompleteListener listener;

    public Gesture(Context context) {
        super(context);
    }

    public void setListener(IsActionCompleteListener listener){
        this.listener = listener;
    }
}
