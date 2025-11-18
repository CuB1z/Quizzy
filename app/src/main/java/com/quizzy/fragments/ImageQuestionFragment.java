package com.quizzy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.quizzy.QuizActivity;
import com.quizzy.R;
import com.quizzy.config.Constants;
import com.quizzy.models.ImageQuestion;

import java.util.List;

public class ImageQuestionFragment extends Fragment {
    private ImageQuestion question;

    public static ImageQuestionFragment newInstance(ImageQuestion question) {
        ImageQuestionFragment fragment = new ImageQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.question = (ImageQuestion) getArguments().getSerializable(Constants.ARG_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_question, container, false);

        TextView questionText = view.findViewById(R.id.questionText);
        ImageView questionImage = view.findViewById(R.id.questionImage);
        RadioGroup optionsGroup = view.findViewById(R.id.optionsGroup);

        if (question != null) {
            questionText.setText(question.getQuestionText());
            questionImage.setImageResource(question.getImageResId());

            List<String> options = this.question.getOptions();

            if (getContext() == null) {
                return view;
            }

            for (int i = 0; i < options.size(); i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(options.get(i));
                radioButton.setId(i);
                radioButton.setButtonDrawable(null);
                radioButton.setBackground(
                        ContextCompat.getDrawable(getContext(), R.drawable.radio_button_selector)
                );

                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                        RadioGroup.LayoutParams.MATCH_PARENT,
                        RadioGroup.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 32);
                radioButton.setLayoutParams(params);
                radioButton.setPadding(32, 32, 32, 32);
                optionsGroup.addView(radioButton);
            }

            optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
                boolean isCorrect = checkedId == question.getCorrectOptionIndex();
                ((QuizActivity) requireActivity()).onAnswerSelected(isCorrect);
                for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                    optionsGroup.getChildAt(i).setEnabled(false);
                }
            });
        }

        return view;
    }
}
