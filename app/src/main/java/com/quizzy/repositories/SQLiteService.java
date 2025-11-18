package com.quizzy.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.quizzy.models.ImageAnswerQuestion;
import com.quizzy.models.ImageQuestion;
import com.quizzy.models.Question;
import com.quizzy.models.SpinnerAnswerQuestion;
import com.quizzy.models.TextQuestion;

import java.util.ArrayList;
import java.util.List;

public class SQLiteService {
    private final SQLiteAdmin admin;

    public SQLiteService(Context context) {
        this.admin = new SQLiteAdmin(context);
    }

    public int getMaxScore() {
        SQLiteDatabase db = this.admin.getReadableDatabase();
        int maxScore = 0;

        String maxScoreQuery = "SELECT MAX(score) FROM score";
        Cursor cursor = db.rawQuery(maxScoreQuery, null);
        if (cursor.moveToFirst()) {
            maxScore = cursor.getInt(0);
        }

        cursor.close();
        db.close();
        return maxScore;
    }

    public void insertScore(int score) {
        SQLiteDatabase db = this.admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("score", score);

        db.insert("score", null, values);
        db.close();
    }

    public Question getQuestionById(long questionId) {
        SQLiteDatabase db = this.admin.getReadableDatabase();
        Question question = null;

        String questionQuery = "SELECT * FROM question WHERE id = ?";
        Cursor cursor = db.rawQuery(questionQuery, new String[]{String.valueOf(questionId)});

        if (cursor.moveToFirst()) {
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            String questionText = cursor.getString(cursor.getColumnIndexOrThrow("question"));
            int correctAnswerIndex = cursor.getInt(cursor.getColumnIndexOrThrow("correct_answer_index"));
            Integer imageRes = null;
            if (!cursor.isNull(cursor.getColumnIndexOrThrow("image_res"))) {
                imageRes = cursor.getInt(cursor.getColumnIndexOrThrow("image_res"));
            }

            // Fetch answers
            String answerQuery = "SELECT * FROM answer WHERE question_id = ? ORDER BY position ASC";
            Cursor answerCursor = db.rawQuery(answerQuery, new String[]{String.valueOf(questionId)});
            List<String> options = new ArrayList<>();
            List<Integer> imageAnswers = new ArrayList<>();

            while (answerCursor.moveToNext()) {
                options.add(answerCursor.getString(answerCursor.getColumnIndexOrThrow("answer_text")));
                if (!answerCursor.isNull(answerCursor.getColumnIndexOrThrow("image_res"))) {
                    imageAnswers.add(answerCursor.getInt(answerCursor.getColumnIndexOrThrow("image_res")));
                } else {
                    imageAnswers.add(null);
                }
            }
            answerCursor.close();

            // Create appropriate Question object
            switch (type) {
                case "IMAGE":
                    if (imageRes != null) {
                        question = new ImageQuestion(questionText, options, correctAnswerIndex, imageRes);
                    }
                    break;
                case "IMAGE_ANSWER":
                    question = new ImageAnswerQuestion(questionText, options, correctAnswerIndex, imageAnswers);
                    break;
                case "SPINNER_ANSWER":
                    question = new SpinnerAnswerQuestion(questionText, options, correctAnswerIndex);
                    break;
                case "TEXT":
                    question = new TextQuestion(questionText, options, correctAnswerIndex);
                default:
                    break;
            }
        }

        cursor.close();
        db.close();

        return question;
    }

    public long getQuestionCount() {
        SQLiteDatabase db = this.admin.getReadableDatabase();
        long count = 0;

        String countQuery = "SELECT COUNT(*) FROM question";
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor.moveToFirst()) {
            count = cursor.getLong(0);
        }

        cursor.close();
        db.close();
        return count;
    }

    public void insertBatchQuestions(List<Question> questions) {
        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    public void insertQuestion(Question question) {
        SQLiteDatabase db = this.admin.getWritableDatabase();

        Integer imageRes = null;
        if (question instanceof ImageQuestion) {
            imageRes = ((ImageQuestion) question).getImageResId();
        }

        ContentValues values = new ContentValues();
        values.put("type", question.getType());
        values.put("question", question.getQuestionText());
        values.put("correct_answer_index", question.getCorrectOptionIndex());
        values.put("image_res", imageRes);

        long questionId = db.insert("question", null, values);
        db.close();

        for (int i = 0; i < question.getOptions().size(); i++) {
            String answerText = question.getOptions().get(i);
            Integer answerImageRes = null;

            if (question instanceof ImageAnswerQuestion) {
                answerImageRes = ((ImageAnswerQuestion) question).getImageAnswers().get(i);
            }

            insertAnswer(questionId, answerText, answerImageRes, i);
        }
    }

    private void insertAnswer(long questionId, String answerText, Integer imageRes, int position) {
        SQLiteDatabase db = this.admin.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("question_id", questionId);
        values.put("answer_text", answerText);
        values.put("image_res", imageRes);
        values.put("position", position);

        db.insert("answer", null, values);
        db.close();
    }
}
