package com.example.tweaty.gesturestest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.UUID;


public class SurveyActivity extends ActionBarActivity implements AgeDialog.AgeDialogListener{

    private Button bAge;
    private EditText editText;
    private RadioButton rbMen, rbWomen, rbYes, rbNo;
    private int mAge = 0;
    private String id = UUID.randomUUID().toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        bAge = (Button)findViewById(R.id.bAge);
        rbMen = (RadioButton) findViewById(R.id.rbMen);
//        rbWomen = (RadioButton) findViewById(R.id.rbWomen);
        rbYes = (RadioButton) findViewById(R.id.rbYes);
//        rbNo = (RadioButton) findViewById(R.id.rbNo);
        editText = (EditText)findViewById(R.id.editTextId);
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
/*    public void onClickEditBox(View v){ // nie dziala czyszczenie
        editText.setText("");
    }*/
    public void onClickButton(View v){
        if (mAge == 0){
            Toast.makeText(getApplicationContext(), R.string.fill_age, Toast.LENGTH_LONG).show();
        }else{
            DataHolder dh = DataHolder.getInstance();
            dh.clearData();
            dh.setId(editText.getText().toString());
            dh.setAge(mAge);
            dh.setSex( (rbMen.isChecked())? "Mê¿czyzna" : "Kobieta" );
            dh.setUsigSmartphone( rbYes.isChecked() );
            Intent startIntent = new Intent(this, TestListActivity.class);
            startActivity(startIntent);
            finish();
        }

    }

}
