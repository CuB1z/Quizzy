package com.quizzy.models;

import java.io.Serializable;
import java.util.List;

public abstract class Question implements Serializable {
    protected String questionText;
    protected List<String> options;
    protected int correctOptionIndex;
    protected int value;

    public Question(String questionText, List<String> options, int correctOptionIndex, int value) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
        this.value = value;
    }

    public String getQuestionText() { return questionText; }
    public List<String> getOptions() { return options; }
    public int getCorrectOptionIndex() { return correctOptionIndex; }
    public int getValue() { return value; }

    public abstract String getType();
}
