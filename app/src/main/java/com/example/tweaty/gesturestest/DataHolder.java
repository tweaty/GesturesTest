package com.example.tweaty.gesturestest;


import java.util.ArrayList;

public class DataHolder {
    private static DataHolder ourInstance = new DataHolder();

    private String id;
    private int age;
    private String sex;
    private boolean usigSmartphone;
    private ArrayList<TestData> tests;
    private TestData testData;

    public static DataHolder getInstance() {
        return ourInstance;
    }

    public ArrayList<TestData> getTests() {
        return tests;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setUsigSmartphone(boolean usigSmartphone) {
        this.usigSmartphone = usigSmartphone;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public String getSex() {
        return sex;
    }

    public boolean isUsigSmartphone() {
        return usigSmartphone;
    }

    public void addTest(TestData testData){
        tests.add(testData);
    }

    public void clearAllData(){
        id = null;
        age = 0;
        sex = null;
        usigSmartphone = false;
        tests.clear();
    }
    public void clearTestData(int Id){
        for (int i = tests.size()-1; i>=0; i--){
            if (tests.get(i).getTestId() == Id)
                tests.remove(i);
        }
    }
    private DataHolder() {
        tests = new ArrayList<TestData>();
    }
}
