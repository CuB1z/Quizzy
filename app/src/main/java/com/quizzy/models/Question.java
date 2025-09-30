package com.quizzy.models;

import android.content.Context;
import android.view.View;

import java.util.List;

public abstract class Question {
    protected String questionText;
    protected List<String> options;
    protected int correctOptionIndex;
    protected int points = 3;
    protected int errorPenalty = 2;

    // [Constructors] ====================================
    public Question(String questionText, List<String> options, int correctOptionIndex) {
        if (options == null || options.size() < 2) {
            throw new IllegalArgumentException("Options list must contain at least two options.");
        }

        if (correctOptionIndex < 0 || correctOptionIndex >= options.size()) {
            throw new IllegalArgumentException("Correct option index is out of bounds.");
        }

        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public Question(String questionText, int points, int errorPenalty, List<String> options, int correctOptionIndex) {
        this(questionText, options, correctOptionIndex);
        this.points = points;
        this.errorPenalty = errorPenalty;
    }

    // [Public Methods] ==================================
    public abstract View render(Context context);

    public boolean isCorrect(int selectedOptionIndex) {
        return selectedOptionIndex == this.correctOptionIndex;
    }

    public int calculateScore(boolean isCorrect) {
        return isCorrect ? this.points : -this.errorPenalty;
    }

    // [Getters] =========================================
    public String getQuestionText() {
        return this.questionText;
    }

    public List<String> getOptions() {
        return this.options;
    }

    public int getCorrectOptionIndex() {
        return this.correctOptionIndex;
    }

    public int getPoints() {
        return this.points;
    }

    public int getErrorPenalty() {
        return this.errorPenalty;
    }
}
