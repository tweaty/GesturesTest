package com.example.tweaty.gesturestest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity implements InfoDialog.InfoDialogListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bStart = (Button)findViewById(R.id.startButton);
        Button bAbout = (Button)findViewById(R.id.aboutButton);
        Button bSettings = (Button)findViewById(R.id.settingsButton);
        Button bInstrcution = (Button)findViewById(R.id.instructionButton);
        bAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show();
            }
        });

        bStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(MainActivity.this, SurveyActivity.class);
                startActivity(startIntent);
            }
        });
        bSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settingsIntent);
            }
        });
        bInstrcution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent instructionIntent = new Intent(MainActivity.this, InstructionActivity.class);
                startActivity(instructionIntent);
            }
        });
    }

    public void show(){
        InfoDialog infoDialog = new InfoDialog();
        infoDialog.setTileId(R.string.about);
        infoDialog.setPositiveButtonText(R.string.quit);
        infoDialog.setViewId(R.layout.about_dialog);
        infoDialog.show(getFragmentManager(), "AboutDialog");

    }

    @Override
    public void onDialogPositiveClick(InfoDialog dialog) {

    }
}
