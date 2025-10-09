package com.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.quizzy.config.Constants;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Reference UI elements
        TextView scoreText = findViewById(R.id.finalScoreText);
        Button restartButton = findViewById(R.id.restartButton);

        // Get final score from intent and display it
        int finalScore = getIntent().getIntExtra(Constants.FINAL_SCORE, 0);
        scoreText.setText(getString(R.string.result_score, finalScore));
    }

    /**
     * Called when the restart button is clicked. Restarts the quiz.
     */
    public void restartGame(View view) {
        Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
        startActivity(intent);
        finish();
    }
}
