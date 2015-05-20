package com.example.tweaty.gesturestest;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class TestListActivity extends ListActivity {

    private ArrayList<Test> mTests = new ArrayList<Test>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);
        getListView().addHeaderView(new View(this), null, true);
        mTests.add(new Test("Tap"));
        mTests.add(new Test("Pap"));
        mTests.add(new Test("Pinch and stretch"));
        mTests.add(new Test("Badanie sekwencji"));
        TestAdapter adapter = new TestAdapter(this, mTests);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        switch (position){
            case 1:
                Intent startIntent = new Intent(this, TapActivity.class);
                startActivity(startIntent);
                break;

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
