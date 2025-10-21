package com.quizzy.models;

import java.util.List;

public class ImageQuestion extends Question {
    private final int imageResId;

    public ImageQuestion(String questionText, List<String> options, int correctOptionIndex, int imageResId) {
        super(questionText, options, correctOptionIndex);
        this.imageResId = imageResId;
    }

    public int getImageResId() { return imageResId; }

}