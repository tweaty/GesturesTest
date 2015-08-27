package com.example.tweaty.gesturestest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.NumberPicker;


public class ResolutionPickerPreference extends DialogPreference {

    public int MAX_VALUE;
    public int MIN_VALUE = 100;

    private NumberPicker picker;
    private int value;
    private boolean isHeight;

    public ResolutionPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ResolutionPickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }



    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        picker = new NumberPicker(getContext());
        picker.setLayoutParams(layoutParams);


        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(picker);

        return dialogView;
    }

    private void init(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ResolutionPickerPreference);
        isHeight = a.getBoolean(R.styleable.ResolutionPickerPreference_isHeight, false);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        if (!isHeight) {
            MAX_VALUE = size.x;
        } else {
            MAX_VALUE = size.y;
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        picker.setMinValue(MIN_VALUE);
        picker.setMaxValue(MAX_VALUE);
        picker.setValue(getValue());
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            setValue(picker.getValue());
            callChangeListener(value);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, MIN_VALUE);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        setValue(restorePersistedValue ? getPersistedInt(MIN_VALUE) : (int) defaultValue);
    }

    public void setValue(int value){
        this.value = value;
        persistInt(this.value);
    }

    public int getValue() {
        return this.value;
    }
}
