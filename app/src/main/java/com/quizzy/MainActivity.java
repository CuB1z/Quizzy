package com.quizzy;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.quizzy.utils.SoundPlayer;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_main);
    }

    public void onStart(View view) {
        SoundPlayer.playSound(this, R.raw.cuak_sound);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent, options.toBundle());
    }

    public void helpClick(View view) {
        SoundPlayer.playSound(this, R.raw.cuak_sound);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this);
        Intent intent = new Intent(MainActivity.this, HelpActivity.class);
        startActivity(intent, options.toBundle());
    }
}
