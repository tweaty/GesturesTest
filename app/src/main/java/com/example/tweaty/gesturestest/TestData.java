package com.example.tweaty.gesturestest;


public class TestData {
    private int testId;
    private int testNumber;
    private int testTime;
    private boolean isCorrect;
    private int precision;
    private int type1, type2, type3;
    private int time1, time2, time3;

    public TestData(int testId, int testNumber, int testTime, boolean isCorrect, int precision) {
        this.testId = testId;
        this.testNumber = testNumber;
        this.testTime = testTime;
        this.isCorrect = isCorrect;
        this.precision = precision;
    }

    public TestData(int testId, int testNumber, int testTime, int precision, boolean isCorrect, int type1, int type2, int type3, int time1, int time2, int time3) {
        this.testId = testId;
        this.testNumber = testNumber;
        this.testTime = testTime;
        this.precision = precision;
        this.isCorrect = isCorrect;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
    }
}
