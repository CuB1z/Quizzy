package com.quizzy.models;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.quizzy.R;
import com.quizzy.ui.QuestionTextView;

import java.util.List;

public class ImageChoiceQuestion extends Question {
    private List<Integer> imageResources;

    // [Constructors] ====================================
    public ImageChoiceQuestion(String questionText, List<String> imageDescriptions, List<Integer> imageResources, int correctOptionIndex) {
        super(questionText, imageDescriptions, correctOptionIndex);
        this.imageResources = imageResources;
    }

    public ImageChoiceQuestion(String questionText, int points, int errorPenalty, List<String> imageDescriptions, List<Integer> imageResources, int correctOptionIndex) {
        super(questionText, points, errorPenalty, imageDescriptions, correctOptionIndex);
        this.imageResources = imageResources;
    }

    // [Public Methods] ==================================
    @Override
    public View render(Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(16, 16, 16, 16);

        QuestionTextView questionTextView = new QuestionTextView(context);
        questionTextView.setText(this.questionText);
        layout.addView(questionTextView);

        RadioGroup radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(RadioGroup.VERTICAL);

        for (int i = 0; i < imageResources.size(); i++) {
            LinearLayout optionLayout = new LinearLayout(context);
            optionLayout.setOrientation(LinearLayout.HORIZONTAL);
            optionLayout.setPadding(12, 12, 12, 12);
            optionLayout.setBackgroundResource(R.drawable.option_selector);

            RadioButton radioButton = new RadioButton(context);
            radioButton.setId(i);
            radioButton.setButtonDrawable(null);
            radioButton.setBackgroundResource(android.R.color.transparent);

            ImageView imageView = new ImageView(context);
            imageView.setImageResource(imageResources.get(i));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(200, 200);
            imageParams.setMargins(16, 0, 16, 0);
            imageView.setLayoutParams(imageParams);

            optionLayout.addView(radioButton);
            optionLayout.addView(imageView);

            LinearLayout.LayoutParams optionParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            optionParams.setMargins(0, 0, 0, 12);
            optionLayout.setLayoutParams(optionParams);

            optionLayout.setOnClickListener(v -> radioButton.setChecked(true));

            radioGroup.addView(optionLayout);
        }

        layout.addView(radioGroup);
        return layout;
    }

    // [Getters and Setters] =========================
    public List<Integer> getImageResources() {
        return imageResources;
    }

    public void setImageResources(List<Integer> imageResources) {
        this.imageResources = imageResources;
    }
}
