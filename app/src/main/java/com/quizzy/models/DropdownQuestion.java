package com.quizzy.models;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.quizzy.ui.QuestionTextView;

import java.util.List;

public class DropdownQuestion extends Question {

    // [Constructors] ====================================
    public DropdownQuestion(String questionText, List<String> options, int correctOptionIndex) {
        super(questionText, options, correctOptionIndex);
    }

    public DropdownQuestion(String questionText, int points, int errorPenalty, List<String> options, int correctOptionIndex) {
        super(questionText, points, errorPenalty, options, correctOptionIndex);
    }

    // [Public Methods] ==================================
    @Override
    public View render(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        // Question TextView
        QuestionTextView questionTextView = new QuestionTextView(context);
        questionTextView.setText(this.questionText);
        layout.addView(questionTextView);

        // Spinner/Dropdown
        Spinner spinner = new Spinner(context);

        // Create adapter with options
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                options
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setPadding(24, 20, 24, 20);

        // Layout parameters for spinner
        LinearLayout.LayoutParams spinnerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        spinnerParams.setMargins(0, 16, 0, 0);
        spinner.setLayoutParams(spinnerParams);

        layout.addView(spinner);

        return layout;
    }
}
