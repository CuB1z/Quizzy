package com.quizzy.models;

import java.util.List;

public class ImageQuestion extends Question {
    private int imageResId;

    public ImageQuestion(String questionText, List<String> options, int correctOptionIndex, int value, int imageResId) {
        super(questionText, options, correctOptionIndex, value);
        this.imageResId = imageResId;
    }

    public int getImageResId() { return imageResId; }

    @Override
    public String getType() {
        return "IMAGE";
    }
}