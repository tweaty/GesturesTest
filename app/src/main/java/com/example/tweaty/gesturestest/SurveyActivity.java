package com.example.tweaty.gesturestest;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.UUID;


public class SurveyActivity extends ActionBarActivity implements AgeDialog.AgeDialogListener{

    private Button bAge;
    private EditText editText;
    private int mAge = 0;
    private String id = UUID.randomUUID().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        bAge = (Button)findViewById(R.id.bAge);
        editText = (EditText)findViewById(R.id.editText);
        editText.setText(id);
    }

    public void showAgeDialog(View v) {
        AgeDialog dialog = new AgeDialog();
        dialog.setAgeValue(mAge);
        dialog.show(getFragmentManager(), "AgeDialog");
    }

    @Override
    public void onDialogPositiveClick(AgeDialog dialog) {
        mAge = dialog.getAgeValue();
        bAge.setText(String.valueOf(mAge));
    }

    @Override
    public void onDialogNegativeClick(AgeDialog dialog) {

    }

    public void onClickButton(View v){
        if (mAge == 0){
            Toast.makeText(getApplicationContext(), R.string.fill_age, Toast.LENGTH_LONG).show();
        }else{
            Intent startIntent = new Intent(this, TestListActivity.class);
            startActivity(startIntent);
        }

    }

}
