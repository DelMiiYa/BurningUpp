package com.example.javaaintshit;

public class AdvanceTestData {
    private String advanceTestText;
    private Integer advanceTestAnswer;

    public AdvanceTestData(String questionText) {
        this.advanceTestText = questionText;
        this.advanceTestAnswer = null;
    }

    public String getQuestionText() {
        return advanceTestText;
    }

    public Integer getAnswer() {
        return advanceTestAnswer;
    }

    public void setAnswer(Integer answer) {
        this.advanceTestAnswer = answer;
    }
}
