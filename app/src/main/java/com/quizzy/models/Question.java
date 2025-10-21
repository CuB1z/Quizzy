package com.quizzy.models;

import java.io.Serializable;
import java.util.List;

public abstract class Question implements Serializable {
    protected String questionText;
    protected List<String> options;
    protected int correctOptionIndex;

    public Question(String questionText, List<String> options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }

}
