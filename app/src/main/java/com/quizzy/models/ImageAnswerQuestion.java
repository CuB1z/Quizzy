package com.quizzy.models;

import java.util.List;

public class ImageAnswerQuestion extends Question{
        private final List<Integer> imageAnswers;

        public ImageAnswerQuestion(String questionText, List<String> options, int correctOptionIndex, List<Integer> imageAnswers){
            super(questionText, options, correctOptionIndex);
            this.imageAnswers = imageAnswers;
        }

        @Override
        public String getType() {
            return "IMAGE_ANSWER";
        }

        public List<Integer> getImageAnswers() {
            return imageAnswers;
        }
}
