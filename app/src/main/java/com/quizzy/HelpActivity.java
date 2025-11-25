package com.quizzy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActivityContent(R.layout.activity_help);
    }

    public void navigateToMainActivity(View view) {
        Intent intent = new Intent(HelpActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}