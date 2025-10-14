package com.quizzy.fragments;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.quizzy.R;
import com.quizzy.config.Constants;
import com.quizzy.models.ImageAnswerQuestion;

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

        return view;
    }

}