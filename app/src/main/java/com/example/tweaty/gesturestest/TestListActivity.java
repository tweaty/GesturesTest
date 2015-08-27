package com.example.tweaty.gesturestest;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class TestListActivity extends ListActivity {
    private ArrayList<Test> mTests = new ArrayList<Test>();
    private Button mSendButton;
    private int mFormat;
    private String fileUri, email;
    ArrayList<Uri> attachments = new ArrayList<>();
    TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        getListView().addHeaderView(new View(this), null, true);
        mTests.add(new Test("Tap"));
        mTests.add(new Test("Pap"));
        mTests.add(new Test("Pinch and stretch"));
        mTests.add(new Test("Badanie sekwencji"));
        adapter = new TestAdapter(this, mTests);
        setListAdapter(adapter);
        mSendButton = (Button)findViewById(R.id.sendData);
        initSettings();
    }

    protected void initSettings(){
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        mFormat = Integer.parseInt(sharedPrefs.getString("key_format", "0"));
        email = sharedPrefs.getString("key_email", "test@test.pl");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
                mTests.get(requestCode-1).setDone(true);
            }
        else
            DataHolder.getInstance().clearTestData(requestCode);

        adapter.notifyDataSetChanged();

        if(isAllDone())
            mSendButton.setEnabled(true);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        switch (position) {
            case 1:
                Intent startIntent;
                if (!mTests.get(position - 1).isDone()) {
                    startIntent = new Intent(this, TapActivity.class);
                    startActivityForResult(startIntent, 1);
                }
                break;
            case 2:
                if (!mTests.get(position - 1).isDone()) {
                    startIntent = new Intent(this, PanActivity.class);
                    startActivityForResult(startIntent, 2);
                }
                break;
            case 3:
                if (!mTests.get(position - 1).isDone()) {
                    startIntent = new Intent(this, ZoomActivity.class);
                    startActivityForResult(startIntent, 3);
                }
                break;
            case 4:
                if (!mTests.get(position - 1).isDone()) {
                    startIntent = new Intent(this, SequenceActivity.class);
                    startActivityForResult(startIntent, 4);
                }
                break;
        }
    }

    public void sendData(View v){

//        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE); // do zalaczenia wielu plikow, nie dziala wtedy mailto:
        //emailIntent.setType("vnd.android.cursor.dir/email");//"vnd.android.cursor.dir/email"
        emailIntent.setType("message/rfc822");
//        emailIntent.setData(Uri.parse("mailto:")); // tylko programy do wysylania maili, dziala tylko z ACTION_SENDTO, mie dziala z action_send_multiple
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject));
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, getResources().getString(R.string.email_text));
//        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + fileUri));
        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, attachments); // dodaje liste zawierajaca uri do plikow
        startActivity(Intent.createChooser(emailIntent, getResources().getString(R.string.send_email)));
    }

    public void finishTest(View v){
        finish();
    }

    private boolean isAllDone(){
        for(int i = 0; i<mTests.size(); i++){
            if(!mTests.get(i).isDone())
                return false;
        }
        if (isExternalStorageWritable()) {
            if (mFormat == 0)
                SaveDataToCsv();
            else if (mFormat == 1)
                SaveDataToXml();
            else {
                SaveDataToXml();
                SaveDataToCsv();
            }
        }
        else
        {
            Toast.makeText(getApplicationContext(), R.string.storage_error, Toast.LENGTH_LONG).show();
        }
        return true;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }


    private void SaveDataToCsv() {
        DataHolder dh = DataHolder.getInstance();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_test");
        myDir.mkdirs();
        String fname = dh.getId() +".csv";
        File file = new File (myDir, fname);
        fileUri = myDir + "/" + fname;
        attachments.add(Uri.parse("file://" + myDir + "/" + fname));
        String header = "id,wiek,plec,smartphone,testId,nrproby,czas,poprawnosc,precyzja,typ1,czas1,poprawnosc1,precyzja1,typ2,czas2,poprawnosc2,precyzja2,typ3,czas3,poprawnosc3,precyzja3\n";
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(header.getBytes());
            StringBuilder sb = new StringBuilder();
            for (TestData td : dh.getTests()){
                sb.append(dh.getId()).append(",").append(dh.getAge()).append(",").append(dh.getSex())
                        .append(",").append(dh.isUsigSmartphone()).append(",").append(td.getTestId())
                        .append(",").append(td.getTestNumber()).append(",").append(td.getTestTime()).
                        append(",").append(td.isCorrect()).append(",").append(td.getPrecision());
                for (TestData data: td.sequences){
                    sb.append(",").append(data.getTestId()).append(",").append(data.getTestTime()).append(",").
                            append(data.isCorrect()).append(",").append(data.getPrecision());
                }
                sb.append("\n");
                out.write(sb.toString().getBytes());
                sb.delete(0,sb.length());
            }
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SaveDataToXml() {
        DataHolder dh = DataHolder.getInstance();
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_test");
        myDir.mkdirs();
        String fname = dh.getId() +".xml";
        File file = new File (myDir, fname);
        fileUri = myDir + "/" + fname;
        attachments.add(Uri.parse("file://" + myDir + "/" + fname));
        if (file.exists()) file.delete ();
        try {
            XmlSerializer serializer = Xml.newSerializer();
            FileOutputStream out = new FileOutputStream(file);
            serializer.setOutput(out, "UTF-8");
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", "BadanieGestow");
            serializer.startTag("", "Id");
            serializer.text(dh.getId());
            serializer.endTag("", "Id");
            serializer.startTag("", "Wiek");
            serializer.text(String.valueOf(dh.getAge()));
            serializer.endTag("", "Wiek");
            serializer.startTag("", "Plec");
            serializer.text(dh.getSex());
            serializer.endTag("", "Plec");
            serializer.startTag("", "Smartphone");
            serializer.text(String.valueOf(dh.isUsigSmartphone()));
            serializer.endTag("", "Smartphone");
            ArrayList<TestData> tests = dh.getTests();
            for (TestData td : tests){
                serializer.startTag("", "Test");
                    serializer.startTag("", "TestId");
                    serializer.text(String.valueOf(td.getTestId()));
                    serializer.endTag("", "TestId");
                    serializer.startTag("", "NrProby");
                    serializer.text(String.valueOf(td.getTestNumber()));
                    serializer.endTag("", "NrProby");
                    serializer.startTag("", "Czas");
                    serializer.text(String.valueOf(td.getTestTime()));
                    serializer.endTag("", "Czas");
                if(td.getTestId() != 4) {
                    serializer.startTag("", "Poprawnosc");
                    serializer.text(String.valueOf(td.isCorrect()));
                    serializer.endTag("", "Poprawnosc");
                    serializer.startTag("", "Precyzja");
                    serializer.text(String.valueOf(td.getPrecision()));
                    serializer.endTag("", "Precyzja");
                } else {
                    for (TestData data : td.sequences){
                        serializer.startTag("", "Test");
                            serializer.startTag("", "TestId");
                            serializer.text(String.valueOf(data.getTestId()));
                            serializer.endTag("", "TestId");
                            serializer.startTag("", "Czas");
                            serializer.text(String.valueOf(data.getTestTime()));
                            serializer.endTag("", "Czas");
                            serializer.startTag("", "Poprawnosc");
                            serializer.text(String.valueOf(data.isCorrect()));
                            serializer.endTag("", "Poprawnosc");
                            serializer.startTag("", "Precyzja");
                            serializer.text(String.valueOf(data.getPrecision()));
                            serializer.endTag("", "Precyzja");
                        serializer.endTag("", "Test");
                    }
                }
                serializer.endTag("", "Test");
            }
            serializer.endTag("", "BadanieGestow");
            serializer.endDocument();
            serializer.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class TestAdapter extends ArrayAdapter<Test> {
        Context context;
        public TestAdapter(Context context, ArrayList<Test> tests){
            super(context, 0, tests);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_test, null);
            }
            Test test = getItem(position);

            TextView nameTextView = (TextView)convertView.findViewById(R.id.name);
            nameTextView.setText(test.getName());
            CheckBox isDoneCheckBox = (CheckBox)convertView.findViewById(R.id.isDoneCheckBox);
            isDoneCheckBox.setChecked(test.isDone());

            return convertView;
        }
    }

    private class Test{
        private String mName;
        private boolean mDone;

        public Test(String mName) {
            this.mName = mName;
            mDone = false;
        }

        public boolean isDone() {
            return mDone;
        }

        public void setDone(boolean mIsDone) {
            this.mDone = mIsDone;
        }

        public String getName() {
            return mName;
        }

        public void setName(String mName) {
            this.mName = mName;
        }
    }
}
