package com.example.tweaty.gesturestest;


import java.util.ArrayList;

public class TestData {
    private int testId;
    private int testNumber;
    private long testTime;
    private boolean isCorrect;
    private int precision;
//    private int type1, type2, type3;
//    private boolean isCorrect1, isCorrect2, isCorrect3;
//    private int precision1, precision2, precision3;
//    private long time1, time2, time3;
    public ArrayList<TestData> sequences = new ArrayList<>();

    //pusty do poprawnego zapisu
    public TestData() {
    }

    //do pojedynczych testow
    public TestData(int testId, int testNumber, long testTime, boolean isCorrect, int precision) {
        this.testId = testId;
        this.testNumber = testNumber;
        this.testTime = testTime;
        this.isCorrect = isCorrect;
        this.precision = precision;
        this.sequences.add(new TestData());
        this.sequences.add(new TestData());
        this.sequences.add(new TestData());
    }
    //do sekwencji calej
    public TestData(int testId, int testNumber, long testTime, ArrayList<TestData> sequences) {
        this.testId = testId;
        this.testTime = testTime;
        this.sequences = sequences;
        this.testNumber = testNumber;
    }

    //do elementow sekwencji
    public TestData(int testId, long testTime, boolean isCorrect, int precision) {
        this.testId = testId;
        this.testTime = testTime;
        this.isCorrect = isCorrect;
        this.precision = precision;
    }

 /*   public TestData(int testId, int testNumber, long testTime, int type1, boolean isCorrect1, int precision1, long time1, int type2, boolean isCorrect2, int precision2, long time2, int type3, boolean isCorrect3, int precision3, long time3) {
        this.testId = testId;
        this.testNumber = testNumber;
        this.testTime = testTime;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        this.isCorrect1 = isCorrect1;
        this.isCorrect2 = isCorrect2;
        this.isCorrect3 = isCorrect3;
        this.precision1 = precision1;
        this.precision2 = precision2;
        this.precision3 = precision3;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
    }*/

    public int getTestId() {
        return testId;
    }

    public long getTestTime() {
        return testTime;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public int getPrecision() {
        return precision;
    }

    public int getTestNumber() {
        return testNumber;
    }

/*    public long getTime3() {
        return time3;
    }

    public long getTime2() {
        return time2;
    }

    public long getTime1() {
        return time1;
    }

    public boolean isCorrect3() {
        return isCorrect3;
    }

    public int getPrecision1() {
        return precision1;
    }

    public int getPrecision2() {
        return precision2;
    }

    public int getPrecision3() {
        return precision3;
    }

    public int getType1() {
        return type1;
    }

    public int getType2() {
        return type2;
    }

    public int getType3() {
        return type3;
    }

    public boolean isCorrect1() {
        return isCorrect1;
    }

    public boolean isCorrect2() {
        return isCorrect2;
    }*/


}
