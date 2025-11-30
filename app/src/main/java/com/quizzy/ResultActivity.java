package com.quizzy;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.quizzy.config.Constants;
import com.quizzy.repositories.SQLiteService;
import com.quizzy.utils.GameTimer;
import com.quizzy.utils.SoundPlayer;

public class ResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_result);

        // Reference UI elements
        TextView scoreText = findViewById(R.id.finalScoreText);
        TextView maxScoreText = findViewById(R.id.maxScoreText);
        TextView timerTextView = findViewById(R.id.timerTextView);


        SQLiteService sqLiteService = new SQLiteService(this);

        // Get final score from intent and display it
        int finalScore = getIntent().getIntExtra(Constants.FINAL_SCORE, 0);
        String timerValue = getIntent().getStringExtra(Constants.TIMER_VALUE);
        int maxScore = sqLiteService.getMaxScore();

        scoreText.setText(getString(R.string.result_score, finalScore));
        maxScoreText.setText(getString(R.string.max_score, maxScore));
        timerTextView.setText(getString(R.string.timer, timerValue));


    }

    /**
     * Called when the restart button is clicked. Restarts the quiz.
     */
    public void restartGame(View view) {
        SoundPlayer.playSound(this, R.raw.cuak_sound);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
        startActivity(intent, options.toBundle());
        finish();
    }

    public void navigatePhotoActivity(View view) {
        SoundPlayer.playSound(this, R.raw.cuak_sound);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(ResultActivity.this, PhotoActivity.class);
        //intent.putExtra(Constants.FINAL_SCORE, timer);
        //intent.putExtra(Constants.TIMER_VALUE, GameTimer.formatTime(elapsedMillis));
        startActivity(intent, options.toBundle());
        finish();
    }

}
