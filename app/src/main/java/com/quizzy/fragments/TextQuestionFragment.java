package com.quizzy.fragments;

import android.os.Build;
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
import com.quizzy.models.TextQuestion;

public class TextQuestionFragment extends Fragment {
    private TextQuestion question;
    private RadioGroup optionsGroup;

    public static TextQuestionFragment newInstance(TextQuestion question) {
        TextQuestionFragment fragment = new TextQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable("question", question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                question = getArguments().getSerializable("question", TextQuestion.class);
            } else {
                question = (TextQuestion) getArguments().getSerializable("question");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_text_question, container, false);

        TextView questionText = view.findViewById(R.id.questionText);
        optionsGroup = view.findViewById(R.id.optionsGroup);

        if (question != null) {
            questionText.setText(question.getQuestionText());

            for (int i = 0; i < question.getOptions().size(); i++) {
                RadioButton radioButton = new RadioButton(getContext());
                radioButton.setText(question.getOptions().get(i));
                radioButton.setId(i);
                optionsGroup.addView(radioButton);
            }

            optionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
                boolean isCorrect = checkedId == question.getCorrectOptionIndex();
                ((QuizActivity) getActivity()).onAnswerSelected(isCorrect);
                for (int i = 0; i < optionsGroup.getChildCount(); i++) {
                    optionsGroup.getChildAt(i).setEnabled(false);
                }
            });
        }

        return view;
    }
}
