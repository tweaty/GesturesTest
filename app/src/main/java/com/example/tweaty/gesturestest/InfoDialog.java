package com.example.tweaty.gesturestest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class InfoDialog extends DialogFragment {

    private InfoDialogListener mInfoDialogListener;
    private int mViewId, mTileId;
    private String mPositiveButtonText = getString(R.string.quit);
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mInfoDialogListener = (InfoDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement InfoDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();


        builder.setView(inflater.inflate(mViewId, null))//.setMessage("TapTest")
                .setTitle(R.string.title_activity_tap)
                .setPositiveButton(mPositiveButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       mInfoDialogListener.onDialogPositiveClick(InfoDialog.this);
                    }
                });


        return builder.create();
    }

    public void setViewId(int id){
        mViewId = id;
    }

    public void setTileId(int id){
        mTileId = id;
    }

    public void setPositiveButtonText(String positiveButton) {
        this.mPositiveButtonText = positiveButton;
    }

    public interface InfoDialogListener {
        void onDialogPositiveClick(InfoDialog dialog);
        //void onDialogNegativeClick(InfoDialog dialog);
    }
}
