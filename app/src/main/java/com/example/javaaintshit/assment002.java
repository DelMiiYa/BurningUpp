package com.example.javaaintshit;

public class assment002 {
    private String assment2text;
    private Integer assment2Answer;

    public assment002(String questionText) {
        this.assment2text = questionText;
        this.assment2Answer = null;
    }

    public String getQuestionText() {
        return assment2text;
    }

    public Boolean getAnswer() {
        return assment2Answer;
    }

    public void setAnswer(Integer answer) {
        this.assment2Answer = answer;
    }
}
