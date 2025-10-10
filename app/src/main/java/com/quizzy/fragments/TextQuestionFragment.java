package com.quizzy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.quizzy.QuizActivity;
import com.quizzy.R;
import com.quizzy.config.Constants;
import com.quizzy.models.TextQuestion;

import java.util.List;

public class TextQuestionFragment extends Fragment {
    private TextQuestion question;

    public static TextQuestionFragment newInstance(TextQuestion question) {
        TextQuestionFragment fragment = new TextQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.question = (TextQuestion) getArguments().getSerializable(Constants.ARG_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_question, container, false);

        // Retrieve UI elements
        TextView questionText = view.findViewById(R.id.questionText);
        RadioGroup optionsGroup = view.findViewById(R.id.optionsGroup);

        // Populate UI with question data
        if (this.question != null) {
            questionText.setText(this.question.getQuestionText());

            List<String> options = this.question.getOptions();

            for (int i = 0; i < options.size(); i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(options.get(i));
                radioButton.setId(i);
                optionsGroup.addView(radioButton);
            }

            optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
                boolean isCorrect = checkedId == this.question.getCorrectOptionIndex();
                ((QuizActivity) getActivity()).onAnswerSelected(isCorrect);
                for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                    optionsGroup.getChildAt(i).setEnabled(false);
                }
            });
        }

        return view;
    }
}
