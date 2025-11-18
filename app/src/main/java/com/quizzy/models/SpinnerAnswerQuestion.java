package com.quizzy.models;

import java.util.List;

public class SpinnerAnswerQuestion extends Question {

    public SpinnerAnswerQuestion(String questionText, List<String> options, int correctOptionIndex) {
        super(questionText, options, correctOptionIndex);
    }

    @Override
    public String getType() {
        return "SPINNER_ANSWER";
    }
}
