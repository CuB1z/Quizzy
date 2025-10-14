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
            "¿Quién ganó el Campeonato Mundial de F1 en 2023?",
            Arrays.asList("Max Verstappen", "Lewis Hamilton", "Charles Leclerc", "Fernando Alonso"),
            0
        ));

        questions.add(new TextQuestion(
            "¿En qué año Michael Schumacher ganó su primer campeonato mundial?",
            Arrays.asList("1993", "1994", "1995", "1996"),
            1
        ));

        questions.add(new ImageQuestion(
            "¿Qué escudería es esta?",
            Arrays.asList("Red Bull Racing", "Ferrari", "Mercedes", "McLaren"),
            3,
            R.drawable.mclaren
        ));

        return questions;
    }
}
