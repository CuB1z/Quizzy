package com.quizzy;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.quizzy.config.Constants;
import com.quizzy.repositories.SQLiteService;

public class ResultActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_result);

        // Reference UI elements
        TextView scoreText = findViewById(R.id.finalScoreText);

        SQLiteService sqLiteService = new SQLiteService(this);

        // Get final score from intent and display it
        int finalScore = getIntent().getIntExtra(Constants.FINAL_SCORE, 0);
        int maxScore = sqLiteService.getMaxScore();
        System.out.println("Max Score from DB: " + maxScore);

        scoreText.setText(getString(R.string.result_score, finalScore));
    }

    /**
     * Called when the restart button is clicked. Restarts the quiz.
     */
    public void restartGame(View view) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
        startActivity(intent, options.toBundle());
        finish();
    }
}
