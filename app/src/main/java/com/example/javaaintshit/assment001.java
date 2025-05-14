package com.example.javaaintshit;

public class assment001 {
    private String assment1text;
    private Integer assment1Answer;

    public assment001(String questionText) {
        this.assment1text = questionText;
        this.assment1Answer = null;
    }

    public String getQuestionText() {
        return assment1text;
    }

    public Integer getAnswer() {
        return assment1Answer;
    }

    public void setAnswer(Integer answer) {
        this.assment1Answer = answer;
    }
}
