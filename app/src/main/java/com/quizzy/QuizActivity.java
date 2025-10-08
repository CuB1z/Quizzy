package com.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.quizzy.fragments.ImageQuestionFragment;
import com.quizzy.fragments.TextQuestionFragment;
import com.quizzy.models.ImageQuestion;
import com.quizzy.models.Question;
import com.quizzy.models.TextQuestion;
import com.quizzy.repositories.QuestionRepository;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private TextView scoreTextView;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        scoreTextView = findViewById(R.id.scoreTextView);
        nextButton = findViewById(R.id.nextButton);

        questions = QuestionRepository.getQuestions(this);

        updateScore();
        loadQuestion();

        nextButton.setOnClickListener(v -> nextQuestion());
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            Fragment fragment;

            if (question instanceof ImageQuestion) {
                fragment = ImageQuestionFragment.newInstance((ImageQuestion) question);
            } else {
                fragment = TextQuestionFragment.newInstance((TextQuestion) question);
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .commit();

            nextButton.setEnabled(false);
        } else {
            finishQuiz();
        }
    }

    public void onAnswerSelected(boolean isCorrect) {
        if (isCorrect) {
            score += 3;
            showMessage("¡Correcto! +3 puntos");
        } else {
            score -= 2;
            showMessage("Incorrecto. -2 puntos");
        }
        updateScore();
        nextButton.setEnabled(true);
    }

    private void nextQuestion() {
        currentQuestionIndex++;
        loadQuestion();
    }

    private void updateScore() {
        scoreTextView.setText("Puntuación: " + score);
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void finishQuiz() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("FINAL_SCORE", score);
        startActivity(intent);
        finish();
    }
}
