package com.quizzy.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.quizzy.QuizActivity;
import com.quizzy.R;
import com.quizzy.config.Constants;
import com.quizzy.models.SpinnerAnswerQuestion;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAnswerQuestionFragment extends Fragment {
    private SpinnerAnswerQuestion question;
    private boolean hasAnswered = false;

    public static SpinnerAnswerQuestionFragment newInstance(SpinnerAnswerQuestion question) {
        SpinnerAnswerQuestionFragment fragment = new SpinnerAnswerQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.question = (SpinnerAnswerQuestion) getArguments().getSerializable(Constants.ARG_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinner_answer, container, false);

        TextView questionText = view.findViewById(R.id.questionText);
        Spinner optionsSpinner = view.findViewById(R.id.optionsSpinner);

        if (optionsSpinner == null) {
            throw new IllegalStateException("optionsSpinner is null - check layout file");
        }

        if (this.question != null) {
            questionText.setText(this.question.getQuestionText());

            List<String> options = new ArrayList<>();
            options.add(getString(R.string.spinner_prompt));
            options.addAll(this.question.getOptions());

            android.util.Log.d("SpinnerDebug", "Options size: " + options.size());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                options
            );
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            optionsSpinner.setAdapter(adapter);

            optionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (position > 0 && !hasAnswered) {
                        hasAnswered = true;
                        int selectedIndex = position - 1;
                        boolean isCorrect = selectedIndex == question.getCorrectOptionIndex();
                        ((QuizActivity) requireActivity()).onAnswerSelected(isCorrect);
                        optionsSpinner.setEnabled(false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // No action needed
                }
            });
        }

        return view;
    }
}
