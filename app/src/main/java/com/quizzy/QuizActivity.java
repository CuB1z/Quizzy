package com.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.quizzy.config.Constants;
import com.quizzy.fragments.ImageAnswerQuestionFragment;
import com.quizzy.fragments.ImageQuestionFragment;
import com.quizzy.fragments.SpinnerAnswerQuestionFragment;
import com.quizzy.fragments.TextQuestionFragment;
import com.quizzy.models.ImageAnswerQuestion;
import com.quizzy.models.ImageQuestion;
import com.quizzy.models.Question;
import com.quizzy.models.SpinnerAnswerQuestion;
import com.quizzy.models.TextQuestion;
import com.quizzy.repositories.QuestionRepository;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView scoreTextView;
    private Button nextButton;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Reference UI elements
        this.scoreTextView = findViewById(R.id.scoreTextView);
        this.nextButton = findViewById(R.id.nextButton);

        // Load questions
        this.questions = QuestionRepository.getQuestions();

        // Initialize score display and load the first question
        updateScore();
        loadQuestion();
    }

    /**
     * Loads the current question into the fragment container.
     * If there are no more questions, it finishes the quiz.
     */
    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            Fragment fragment;

            if (question instanceof ImageQuestion) {
                fragment = ImageQuestionFragment.newInstance((ImageQuestion) question);
            } else if (question instanceof TextQuestion) {
                fragment = TextQuestionFragment.newInstance((TextQuestion) question);
            } else if (question instanceof ImageAnswerQuestion) {
                fragment = ImageAnswerQuestionFragment.newInstance((ImageAnswerQuestion) question);
            } else  if (question instanceof SpinnerAnswerQuestion) {
                fragment = SpinnerAnswerQuestionFragment.newInstance((SpinnerAnswerQuestion) question);
            } else {
                throw new IllegalArgumentException("Unknown question type");
            }

            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();

            this.nextButton.setEnabled(false);
        } else {
            finishQuiz();
        }
    }

    /**
     * Called by the question fragments when an answer is selected.
     *
     * @param isCorrect True if the selected answer is correct, false otherwise.
     */
    public void onAnswerSelected(boolean isCorrect) {

        if (isCorrect) {
            score += Constants.QUESTION_VALUE;
            showMessage(getString(R.string.quiz_toast_correct, Constants.QUESTION_VALUE));
        } else {
            score -= Constants.ERROR_PENALTY;
            showMessage(getString(R.string.quiz_toast_incorrect, Constants.ERROR_PENALTY));
        }

        updateScore();
        this.nextButton.setEnabled(true);
    }

    /**
     * Called when the "Next" button is clicked.
     *
     * @param view The view that was clicked.
     */
    public void nextQuestion(View view) {
        currentQuestionIndex++;
        loadQuestion();
    }

    /**
     * Updates the score display.
     */
    private void updateScore() {
        this.scoreTextView.setText(getString(R.string.quiz_score_value, this.score));
    }

    /**
     * Displays a short toast message to the user.
     *
     * @param message The message to display.
     */
    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Finishes the quiz and navigates to the ResultActivity.
     */
    private void finishQuiz() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(Constants.FINAL_SCORE, score);
        startActivity(intent);
        finish();
    }
}
