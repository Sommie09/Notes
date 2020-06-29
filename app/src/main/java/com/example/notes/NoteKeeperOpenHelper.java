package com.example.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NoteKeeperOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Notes.db";
    public static final int DATABASE_VERSION = 1;


    public NoteKeeperOpenHelper(@Nullable Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NoteKeeperDatabaseContract.CourseInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(NoteKeeperDatabaseContract.NoteInfoEntry.SQL_CREATE_TABLE);

        DatabaseDataWorker databaseDataWorker = new DatabaseDataWorker(db);
        databaseDataWorker.insertCourses();
        databaseDataWorker.insertSampleNotes();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }
}
