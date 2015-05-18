package com.example.tweaty.gesturestest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;


public class AgeDialog extends DialogFragment {
    AgeDialogListener mAgeListner;
    private NumberPicker np;
    private boolean mNpDefaultValue = true;
    private int mNpValue;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mAgeListner = (AgeDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement AgeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View ageView = inflater.inflate(R.layout.age_dialog, null);
        np = (NumberPicker) ageView.findViewById(R.id.numberPicker1);
        np.setMinValue(5);
        np.setMaxValue(100);
        if (!mNpDefaultValue) np.setValue(mNpValue);
        np.setWrapSelectorWheel(true);

        builder.setView(ageView)
                .setTitle(R.string.age)
                .setPositiveButton(R.string.set, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAgeListner.onDialogPositiveClick(AgeDialog.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAgeListner.onDialogNegativeClick(AgeDialog.this);
                    }
                });

        return builder.create();
    }

    public int getAgeValue() {
        return np.getValue();
    }

    public void setAgeValue(int value){
        mNpDefaultValue = false;
        if (value == 0) mNpValue = 18;
        else mNpValue = value;
    }

    public interface AgeDialogListener {
        void onDialogPositiveClick(AgeDialog dialog);
        void onDialogNegativeClick(AgeDialog dialog);
    }

}
