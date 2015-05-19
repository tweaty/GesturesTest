package com.example.tweaty.gesturestest;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TapActivity extends ActionBarActivity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    private RelativeLayout mFrame;
    private TextView text;
    private int mDisplayWidth, mDisplayHeight;


    private int liczba = 0;
    private String tekst = "Liczba klikniêæ: ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //no status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tap);

        mFrame = (RelativeLayout) findViewById(R.id.frame);
        text = (TextView) findViewById(R.id.text);
        text.setText(tekst + liczba);
        InfoDialog infoDialog = new InfoDialog();

        infoDialog.show(getFragmentManager(), "Info");
    }


    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        Zoom tap = new Zoom(getApplicationContext(), mDisplayWidth, mDisplayHeight);
        tap.setListener(this);
        mFrame.addView(tap);
    }

    @Override
    public void onActionComplete() {
        liczba++;
        text.setText(tekst+liczba);
    }
}
