package com.quizzy.repositories;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteAdmin extends SQLiteOpenHelper {
    public static final String DB_NAME = "quizzy.db";
    public static final int DB_VERSION = 1;

    public SQLiteAdmin(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Questions Table (type: {TEXT, SPINNER, IMAGE, IMAGE_ANSWER}))
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS question (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type TEXT NOT NULL," +
                "question TEXT NOT NULL," +
                "correct_answer_index INTEGER NOT NULL," +
                "image_res INTEGER" +
            ");"
        );

        // Answer Table referencing questions
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS answer (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "question_id INTEGER NOT NULL," +
                "answer_text TEXT NOT NULL," +
                "image_res INTEGER," +
                "position INTEGER NOT NULL," +
                "FOREIGN KEY(question_id) REFERENCES question(id) ON DELETE CASCADE" +
            ");"
        );

        // Score Table referencing users
        db.execSQL(
            "CREATE TABLE IF NOT EXISTS score (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "score INTEGER NOT NULL," +
                "date TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
            ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS answer;");
        db.execSQL("DROP TABLE IF EXISTS question;");
        db.execSQL("DROP TABLE IF EXISTS score;");
        onCreate(db);
    }
}
