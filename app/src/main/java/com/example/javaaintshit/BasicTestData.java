package com.example.javaaintshit;

public class BasicTestData {
    private String basicTestText;
    private Integer basicTestAnswer;

    public BasicTestData(String questionText) {
        this.basicTestText = questionText;
        this.basicTestAnswer = null;
    }

    public String getQuestionText() {
        return basicTestText;
    }

    public Integer getAnswer() {
        return basicTestAnswer;
    }

    public void setAnswer(Integer answer) {
        this.basicTestAnswer = answer;
    }
}
