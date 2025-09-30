package com.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.quizzy.models.MultipleChoiceQuestion;
import com.quizzy.models.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout rootContainer;
    private TextView scoreText;
    private Button confirmButton;
    private Button restartButton;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int currentScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.initializeUIComponents();
        this.initializeQuestions();
        this.displayCurrentQuestion();
        this.updateScore();
        this.setupButtonListeners();
    }

    private void initializeUIComponents() {
        this.rootContainer = findViewById(R.id.root);
        this.scoreText = findViewById(R.id.score_text);
        this.confirmButton = findViewById(R.id.confirm_button);
        this.restartButton = findViewById(R.id.restart_button);
    }

    private void updateScore() {
        scoreText.setText("Score: " + currentScore + "/" + questions.size());
    }

    private void setupButtonListeners() {
        confirmButton.setOnClickListener(v -> {
            if (currentQuestionIndex < questions.size()) {
                currentQuestionIndex++;
                displayCurrentQuestion();

                if (currentQuestionIndex < questions.size()) {
                    Toast.makeText(this, "Siguiente pregunta", Toast.LENGTH_SHORT).show();
                }
            }
        });

        restartButton.setOnClickListener(v -> {
            currentQuestionIndex = 0;
            currentScore = 0;
            displayCurrentQuestion();
            updateScore();
            Toast.makeText(this, "Quiz reiniciado", Toast.LENGTH_SHORT).show();
        });
    }


    private void initializeQuestions() {
        this.questions = new ArrayList<>();

        this.questions.add(new MultipleChoiceQuestion(
            "Which operating system is developed by Google?",
            List.of("Android", "iOS", "Windows", "Linux"),
                0
        ));

        this.questions.add(new MultipleChoiceQuestion(
            "What does CPU stand for?",
            List.of("Central Processing Unit", "Computer Personal Unit", "Central Program Unit", "Computer Processing Unit"),
            0
        ));

        this.questions.add(new MultipleChoiceQuestion(
            "Which programming language is used for Android development?",
            List.of("Swift", "Java", "Python", "C#"),
            1
        ));
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            displayCurrentQuestion(questions.get(currentQuestionIndex));
        } else {
            navigateToGameResult();
        }
    }
    private void displayCurrentQuestion(Question currentQuestion) {
        rootContainer.removeAllViews();
        rootContainer.addView(currentQuestion.render(this));
    }

    private void navigateToGameResult() {
        Intent intent = new Intent(MainActivity.this, GameResultActivity.class);
        intent.putExtra("FINAL_SCORE", currentScore);
        startActivity(intent);
        finish();
    }
}