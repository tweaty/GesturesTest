package com.example.tweaty.gesturestest;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TapActivity extends Activity implements InfoDialog.InfoDialogListener, IsActionCompleteListener {

    private RelativeLayout mFrame;
    private TextView text;
    private int mDisplayWidth, mDisplayHeight, mSize;


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
        text.setText(tekst + " " + liczba + " z " + 30);
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.setViewId(R.layout.age_dialog);
        infoDialog.setCancelable(false);
        infoDialog.show(getFragmentManager(), "Info");

        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        mSize = sharedPrefs.getInt("key_element_size", 10);

        boolean fullscreen = sharedPrefs.getBoolean("key_checkbox_fullscreen", true);
        if (!fullscreen) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mFrame.getLayoutParams();
            lp.height = sharedPrefs.getInt("key_width_picker", 600);
            lp.width = sharedPrefs.getInt("key_height_picker", 480);
            mFrame.setLayoutParams(lp);
        }
        //mFrame.setLayoutParams(new RelativeLayout.LayoutParams(300, 300));
    }


    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {
        mDisplayWidth = mFrame.getWidth();
        mDisplayHeight = mFrame.getHeight();
        Tap tap = new Tap(getApplicationContext(), mDisplayWidth, mDisplayHeight, mSize);
        tap.setListener(this);
/*        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tap.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_IN_PARENT, 1);
        tap.setLayoutParams(lp);*/
        mFrame.addView(tap);
    }

    @Override
    public void onActionComplete() {
        liczba++;
        text.setText(tekst+" "+liczba +" z "+30);
    }
}
