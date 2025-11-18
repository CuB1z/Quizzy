package com.quizzy.repositories;

import com.quizzy.R;
import com.quizzy.models.ImageAnswerQuestion;
import com.quizzy.models.ImageQuestion;
import com.quizzy.models.Question;
import com.quizzy.models.SpinnerAnswerQuestion;
import com.quizzy.models.TextQuestion;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionRepository {
    public static int MAX_QUESTIONS = 10;

    public static List<Question> getQuestions() {
        List<Question> questions = new ArrayList<>();
        questions.add(new TextQuestion(
            "¿Quién ganó el Campeonato Mundial de F1 en 2023?",
            Arrays.asList("Max Verstappen", "Lewis Hamilton", "Charles Leclerc", "Fernando Alonso"),
            0
        ));
        questions.add(new SpinnerAnswerQuestion(
            "¿Cuál de estos pilotos NO ha ganado un campeonato mundial de F1?",
            Arrays.asList("Sebastian Vettel", "Nico Rosberg", "Mark Webber", "Kimi Räikkönen"),
            2
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
        questions.add(new TextQuestion(
                "¿En qué año debutó Lewis Hamilton en la Fórmula 1?",
                Arrays.asList("2005", "2007", "2008", "2006"),
                1
        ));
        questions.add(new TextQuestion(
                "¿Qué piloto tiene más campeonatos mundiales de F1?",
                Arrays.asList("Michael Schumacher", "Lewis Hamilton", "Ayrton Senna", "Sebastian Vettel"),
                1
        ));
        questions.add(new TextQuestion(
                "¿Cuál es el circuito más largo del calendario actual de F1?",
                Arrays.asList("Spa-Francorchamps", "Monza", "Silverstone", "Suzuka"),
                0
        ));
        questions.add(new TextQuestion(
                "¿Qué escudería es conocida como la 'Scuderia'?",
                Arrays.asList("Ferrari", "Red Bull", "Mercedes", "McLaren"),
                0
        ));
        questions.add(new TextQuestion(
                "¿Quién fue el primer campeón mundial de F1?",
                Arrays.asList("Juan Manuel Fangio", "Alberto Ascari", "Giuseppe Farina", "Jack Brabham"),
                2
        ));
        questions.add(new TextQuestion(
                "¿En qué país se encuentra el circuito de Interlagos?",
                Arrays.asList("Argentina", "Brasil", "México", "España"),
                1
        ));
        questions.add(new TextQuestion(
                "¿Qué piloto es apodado 'El León Británico'?",
                Arrays.asList("Nigel Mansell", "Lewis Hamilton", "Jenson Button", "Damon Hill"),
                0
        ));
        questions.add(new TextQuestion(
                "¿Cuál es el apodo de Ayrton Senna?",
                Arrays.asList("El Mago", "El Maestro", "Magic", "El Jefe"),
                2
        ));
        questions.add(new TextQuestion(
                "¿Qué piloto ganó el campeonato en 2009?",
                Arrays.asList("Jenson Button", "Sebastian Vettel", "Lewis Hamilton", "Fernando Alonso"),
                0
        ));
        questions.add(new TextQuestion(
                "¿Qué escudería utilizó motores Honda en 2021?",
                Arrays.asList("Red Bull Racing", "Ferrari", "Mercedes", "Alpine"),
                0
        ));
        questions.add(new TextQuestion(
                "¿En qué ciudad se corre el Gran Premio de Mónaco?",
                Arrays.asList("Montecarlo", "Niza", "Marsella", "París"),
                0
        ));
        questions.add(new TextQuestion(
                "¿Quién fue el compañero de equipo de Lewis Hamilton en 2016?",
                Arrays.asList("Nico Rosberg", "Valtteri Bottas", "Jenson Button", "Fernando Alonso"),
                0
        ));
        questions.add(new TextQuestion(
                "¿Qué piloto español ganó dos campeonatos mundiales?",
                Arrays.asList("Carlos Sainz", "Pedro de la Rosa", "Fernando Alonso", "Alfonso de Portago"),
                2
        ));
        questions.add(new TextQuestion(
                "¿Cuál es el color tradicional de Ferrari?",
                Arrays.asList("Rojo", "Amarillo", "Negro", "Azul"),
                0
        ));
        questions.add(new TextQuestion(
                "¿Qué piloto falleció en Imola en 1994?",
                Arrays.asList("Ayrton Senna", "Roland Ratzenberger", "Gilles Villeneuve", "Jules Bianchi"),
                0
        ));
        questions.add(new TextQuestion(
                "¿En qué país nació Max Verstappen?",
                Arrays.asList("Países Bajos", "Bélgica", "Alemania", "Francia"),
                1
        ));
        questions.add(new TextQuestion(
                "¿Qué escudería ganó el campeonato de constructores en 2010?",
                Arrays.asList("Red Bull", "Ferrari", "McLaren", "Mercedes"),
                0
        ));
        questions.add(new TextQuestion(
                "¿Quién fue el primer piloto en superar los 100 podios?",
                Arrays.asList("Michael Schumacher", "Lewis Hamilton", "Sebastian Vettel", "Kimi Räikkönen"),
                1
        ));
        questions.add(new TextQuestion(
                "¿Qué circuito es conocido como 'El Templo de la Velocidad'?",
                Arrays.asList("Monza", "Silverstone", "Spa-Francorchamps", "Hockenheim"),
                0
        ));
        questions.add(new TextQuestion(
                "¿En qué año debutó Fernando Alonso en F1?",
                Arrays.asList("2001", "2003", "1999", "2005"),
                0
        ));


        return questions;
    }
}
