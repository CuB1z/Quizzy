package com.quizzy.fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.quizzy.QuizActivity;
import com.quizzy.R;
import com.quizzy.config.Constants;
import com.quizzy.models.ImageAnswerQuestion;

import java.util.List;

public class ImageAnswerQuestionFragment extends Fragment {

    private ImageAnswerQuestion question;
    public static ImageAnswerQuestionFragment newInstance(ImageAnswerQuestion question) {
        ImageAnswerQuestionFragment fragment = new ImageAnswerQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = (ImageAnswerQuestion) getArguments().getSerializable(Constants.ARG_QUESTION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_answer_question, container, false);

        TextView questionText = view.findViewById(R.id.questionTextView);
        List<ImageButton> imageButtons = List.of(
                view.findViewById(R.id.image1),
                view.findViewById(R.id.image2),
                view.findViewById(R.id.image3),
                view.findViewById(R.id.image4)
        );

        if (question != null) {
            List<Integer> imageResources = question.getImageAnswers();
            List<String> imageDescriptions = question.getOptions();

            questionText.setText(question.getQuestionText());

            for (int i = 0; i < imageButtons.size(); i++) {
                ImageButton answerImageButton = imageButtons.get(i);

                if (i < imageResources.size()) {
                    final int index = i; // Variable final para usar en el lambda
                    answerImageButton.setImageResource(imageResources.get(i));
                    answerImageButton.setContentDescription(imageDescriptions.get(i));
                    answerImageButton.setOnClickListener(v -> {
                        boolean isCorrect = index == question.getCorrectOptionIndex();
                        QuizActivity activity = (QuizActivity) getActivity();

                        if (activity != null) {
                            activity.onAnswerSelected(isCorrect);
                        }
                        for (ImageButton button : imageButtons) {
                            button.setEnabled(false);

                            if (button != answerImageButton) {
                                button.setAlpha(0.4f);
                            }
                        }
                    });
                } else {
                    answerImageButton.setVisibility(View.GONE);
                }
            }
        }

        return view;
    }

}