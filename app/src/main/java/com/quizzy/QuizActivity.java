package com.quizzy;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
import com.quizzy.repositories.SQLiteService;
import com.quizzy.utils.GameTimer;
import com.quizzy.utils.RandomUtils;
import com.quizzy.utils.SoundPlayer;

import java.util.HashSet;
import java.util.Set;

public class QuizActivity extends BaseActivity {
    private final int MAX_QUESTIONS = QuestionRepository.MAX_QUESTIONS;

    private TextView scoreTextView;
    private TextView timerTextView;
    private Button nextButton;

    private MediaPlayer backgroundMusicPlayer;
    private GameTimer gameTimer;

    private SQLiteService sqliteService;
    private long questionCount;
    private final Set<Integer> askedQuestionIds = new HashSet<>();

    private int currentQuestionIndex = 0;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_quiz);

        // Start background music
        this.backgroundMusicPlayer = SoundPlayer.playBackgroundMusic(
            this, R.raw.background_music
        );

        // Reference UI elements
        this.scoreTextView = findViewById(R.id.scoreTextView);
        this.nextButton = findViewById(R.id.nextButton);
        this.timerTextView = findViewById(R.id.timerTextView);

        this.sqliteService = new SQLiteService(this);
        this.questionCount = this.sqliteService.getQuestionCount();
        if (this.questionCount <= 0) {
            this.sqliteService.insertBatchQuestions(QuestionRepository.getQuestions());
            this.questionCount = this.sqliteService.getQuestionCount();
        }

        // Initialize and start game timer (tick every 1s)
        this.gameTimer = new GameTimer(1000);
        this.gameTimer.setListener(elapsedMillis -> runOnUiThread(() -> {
            if (timerTextView != null) {
                timerTextView.setText(
                    GameTimer.formatTime(elapsedMillis)
                );
            }
        }));
        this.gameTimer.start();

        updateScore();
        loadQuestion();
    }

    /**
     * Loads the current question into the fragment container.
     * If there are no more questions, it finishes the quiz.
     */
    private void loadQuestion() {
        if (currentQuestionIndex < MAX_QUESTIONS - 1) {
            Question randomQuestion = null;
            int questionId = -1;
            while (randomQuestion == null || this.askedQuestionIds.contains(questionId)) {
                questionId = RandomUtils.getRandomInt(0, (int) this.questionCount - 1);
                randomQuestion = this.sqliteService.getQuestionById(questionId);
            }

            this.askedQuestionIds.add(questionId);

            Fragment fragment;

            if (randomQuestion instanceof ImageQuestion) {
                fragment = ImageQuestionFragment.newInstance((ImageQuestion) randomQuestion);
            } else if (randomQuestion instanceof TextQuestion) {
                fragment = TextQuestionFragment.newInstance((TextQuestion) randomQuestion);
            } else if (randomQuestion instanceof ImageAnswerQuestion) {
                fragment = ImageAnswerQuestionFragment.newInstance((ImageAnswerQuestion) randomQuestion);
            } else  if (randomQuestion instanceof SpinnerAnswerQuestion) {
                fragment = SpinnerAnswerQuestionFragment.newInstance((SpinnerAnswerQuestion) randomQuestion);
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
            showMessage(getString(R.string.quiz_toast_correct, Constants.QUESTION_VALUE), true);
        } else {
            score -= Constants.ERROR_PENALTY;
            showMessage(getString(R.string.quiz_toast_incorrect, Constants.ERROR_PENALTY), false);
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
     * Called when the "Restart" button is clicked.
     *
     * @param view The view that was clicked.
     */
    public void restartGame(View view) {
        this.backgroundMusicPlayer.stop();

        if (this.gameTimer != null && this.gameTimer.isRunning()) {
            this.gameTimer.stop();
        }

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent, options.toBundle());
        finish();
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
     * @param correct True if the message is for a correct answer, false for incorrect.
     */
    private void showMessage(String message, boolean correct) {
        @SuppressLint("InflateParams")
        View layout = getLayoutInflater().inflate(R.layout.custom_toast, null, false);

        TextView toastView = layout.findViewById(R.id.toast);

        toastView.setText(message);
        int iconRes = correct ? R.drawable.ic_check : R.drawable.ic_cross;

        // Set compound drawable at runtime
        toastView.setCompoundDrawablesRelativeWithIntrinsicBounds(iconRes, 0, 0, 0);

        Toast toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        toast.setView(layout);

        // Position toast at top-right corner
        toast.setGravity(android.view.Gravity.TOP | android.view.Gravity.END, 32, -16);

        toast.show();
    }

    /**
     * Finishes the quiz and navigates to the ResultActivity.
     */
    private void finishQuiz() {
        this.sqliteService.insertScore(score);
        this.backgroundMusicPlayer.stop();

        if (this.gameTimer != null && this.gameTimer.isRunning()) {
            this.gameTimer.stop();
        }

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(Constants.FINAL_SCORE, score);
        startActivity(intent, options.toBundle());
        finish();
    }
}
