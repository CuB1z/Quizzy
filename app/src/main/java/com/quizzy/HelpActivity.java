package com.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.quizzy.utils.SoundPlayer;

public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_help);
    }

    public void navigateToMainActivity(View view) {
        SoundPlayer.playSound(this, R.raw.cuak_sound);
        Intent intent = new Intent(HelpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}