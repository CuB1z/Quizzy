package com.quizzy.ui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;

import com.quizzy.R;

public class QuestionTextView extends AppCompatTextView {

    public QuestionTextView(Context context) {
        super(context);
        initStyle();
    }

    public QuestionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initStyle();
    }

    public QuestionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle();
    }

    private void initStyle() {
        setTextSize(18);
        setTypeface(null, Typeface.BOLD);
        setPadding(0, 0, 0, 16);
        setTextColor(getResources().getColor(R.color.black, null));
        setLineSpacing(4, 1.2f);
    }
}
