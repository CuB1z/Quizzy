package com.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreText = findViewById(R.id.finalScoreText);
        Button restartButton = findViewById(R.id.restartButton);

        int finalScore = getIntent().getIntExtra("FINAL_SCORE", 0);
        scoreText.setText("Puntuación Final: " + finalScore);

        restartButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
