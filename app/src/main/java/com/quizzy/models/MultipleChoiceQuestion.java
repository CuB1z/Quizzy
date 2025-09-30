package com.quizzy.models;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.quizzy.R;
import com.quizzy.ui.QuestionTextView;

import java.util.List;

public class MultipleChoiceQuestion extends Question {

    // [Constructors] ====================================
    public MultipleChoiceQuestion(String questionText, List<String> options, int correctOptionIndex) {
        super(questionText, options, correctOptionIndex);
    }

    public MultipleChoiceQuestion(String questionText, int points, int errorPenalty, List<String> options, int correctOptionIndex) {
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

        // Options
        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(RadioGroup.VERTICAL);

        for (int i = 0; i < options.size(); i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(options.get(i));
            radioButton.setId(i);
            radioButton.setPadding(24, 20, 24, 20);
            radioButton.setTextSize(16);
            radioButton.setTextColor(context.getResources().getColor(android.R.color.black));

            // Custom drawable for RadioButton
            radioButton.setButtonDrawable(null);
            radioButton.setBackgroundResource(R.drawable.option_selector);
            radioButton.setMinHeight(60);

            // Layout parameters
            LinearLayout.LayoutParams radioParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            radioParams.setMargins(0, 0, 0, 12);
            radioButton.setLayoutParams(radioParams);

            radioGroup.addView(radioButton);
        }

        layout.addView(radioGroup);

        return layout;
    }
}
