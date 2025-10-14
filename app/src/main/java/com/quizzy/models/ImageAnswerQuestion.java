package com.quizzy.models;

import java.util.List;

public class ImageAnswerQuestion extends Question{

        private final List<Integer> imageAnswers;

        public ImageAnswerQuestion(String questionText, List<String> options, int correctOptionIndex, List<Integer> imageAnswers){
            super(questionText, options, correctOptionIndex);
            this.imageAnswers = imageAnswers;

        }

        public List<Integer> getImageAnswers() {
            return imageAnswers;
        }
}
