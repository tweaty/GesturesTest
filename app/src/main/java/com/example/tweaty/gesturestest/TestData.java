package com.example.tweaty.gesturestest;


import java.util.ArrayList;

public class TestData {
    private int testId;
    private int testNumber;
    private long testTime;
    private boolean isCorrect;
    private int precision;
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
        this.sequences.addAll(sequences);
        this.testNumber = testNumber;
    }

    //do elementow sekwencji
    public TestData(int testId, long testTime, boolean isCorrect, int precision) {
        this.testId = testId;
        this.testTime = testTime;
        this.isCorrect = isCorrect;
        this.precision = precision;
    }

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

}
