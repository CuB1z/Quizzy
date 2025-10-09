package com.quizzy.repositories;

import com.quizzy.R;
import com.quizzy.models.ImageQuestion;
import com.quizzy.models.Question;
import com.quizzy.models.TextQuestion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionRepository {

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();

        questions.add(new TextQuestion(
                "¿Cuál es la capital de España?",
                Arrays.asList("Madrid", "Barcelona", "Valencia", "Sevilla"),
                0,
                3
        ));

        questions.add(new TextQuestion(
                "¿En qué año llegó el hombre a la Luna?",
                Arrays.asList("1967", "1968", "1969", "1970"),
                2,
                3
        ));

        questions.add(new ImageQuestion(
                "¿Qué película es esta?",
                Arrays.asList("Inception", "Interstellar", "The Matrix", "Avatar"),
                0,
                3,
                R.drawable.ic_launcher_foreground
        ));

        return questions;
    }
}
