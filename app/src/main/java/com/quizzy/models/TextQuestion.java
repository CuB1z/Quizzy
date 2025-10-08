package com.quizzy.models;

import java.util.List;

public class TextQuestion extends Question {

    public TextQuestion(String questionText, List<String> options, int correctOptionIndex, int value) {
        super(questionText, options, correctOptionIndex, value);
    }

    @Override
    public String getType() {
        return "TEXT";
    }
}