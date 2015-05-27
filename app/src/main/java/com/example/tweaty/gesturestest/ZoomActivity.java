package com.example.tweaty.gesturestest;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ZoomActivity extends Activity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    private RelativeLayout mFrame;
    private TextView text;
    private int mDisplayWidth, mDisplayHeight;


    private int liczba = 0;
    private String tekst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //no status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_test);

        mFrame = (RelativeLayout) findViewById(R.id.frame);
        text = (TextView) findViewById(R.id.text);
        tekst = getString(R.string.probe_counter);
        text.setText(tekst+" "+liczba +" z "+30);
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.setViewId(R.layout.age_dialog);
        infoDialog.show(getFragmentManager(), "Info");
    }


    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        Zoom zoom = new Zoom(getApplicationContext(), mDisplayWidth, mDisplayHeight);
        zoom.setListener(this);
        mFrame.addView(zoom);
    }

    @Override
    public void onActionComplete() {
        liczba++;
        text.setText(tekst + " " + liczba + " z " + 30);
    }
}