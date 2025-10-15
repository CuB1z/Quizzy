package com.quizzy.repositories;

import com.quizzy.R;
import com.quizzy.models.ImageAnswerQuestion;
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

        questions.add(new ImageAnswerQuestion(
            "¿Quién es Niki Lauda?",
            Arrays.asList("piloto_1", "piloto_2", "piloto_3", "piloto_4"),
            0,
            Arrays.asList(R.drawable.niki_lauda, R.drawable.james_hunt, R.drawable.ayrton_senna, R.drawable.michael_schumacher)
        ));

        return questions;
    }
}
