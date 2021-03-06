package com.example.tweaty.gesturestest;
import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

public class NumberPickerPreference extends DialogPreference {
    private NumberPicker mPicker;
    private int mNumber = 0;
    private int mMinValue, mMaxValue;

    public NumberPickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        processAttributeSet(attrs);
    }

    public NumberPickerPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        processAttributeSet(attrs);
    }

    @Override
    protected View onCreateDialogView() {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        mPicker = new NumberPicker(getContext());
        mPicker.setLayoutParams(layoutParams);

        FrameLayout dialogView = new FrameLayout(getContext());
        dialogView.addView(mPicker);

        return dialogView;
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            mPicker.clearFocus();
            setValue(mPicker.getValue());
            callChangeListener(mNumber);
        }
    }

    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        mPicker.setMinValue(mMinValue);
        mPicker.setMaxValue(mMaxValue);
        mPicker.setValue(mNumber);
    }

    @Override
    protected void onSetInitialValue(boolean restoreValue, Object defaultValue) {
        setValue(restoreValue ? getPersistedInt(mNumber) : (int) defaultValue);
    }

    public void setValue(int value) {
        if (shouldPersist()) {
            persistInt(value);
        }

        if (value != mNumber) {
            mNumber = value;
            notifyChanged();
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getInt(index, 30);
    }

    private void processAttributeSet(AttributeSet attrs) {
        //This method reads the parameters given in the xml file and sets the properties according to it
        mMinValue = (attrs.getAttributeIntValue(null, "min", 0));
        mMaxValue = (attrs.getAttributeIntValue(null, "max", 0));
    }
}