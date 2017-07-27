package com.main.testdatabaseapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by shuqiao on 2017/7/24.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    public static final String DATABASE_NAME = "student.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "table_student";
    public static final String TABLE_COLUMN_ID = "_id";
    public static final String TABLE_COLUMN_NAME = "name";
    public static final String TABLE_COLUMN_AGE = "age";
    public static final String TABLE_COLUMN_SEX = "sex";
    public static final String TABLE_COLUMN_SCORE = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");

        execSQL(db, "CREATE TABLE IF NOT EXISTS " + TABLE_NAME
                + "(" + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "
                + TABLE_COLUMN_NAME + " VARCHAR , "
                + TABLE_COLUMN_AGE + " INTEGER , "
                + TABLE_COLUMN_SEX + " VARCHAR , "
                + TABLE_COLUMN_SCORE + " VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onUpgrade , oldV=" + oldVersion + " , newV=" + newVersion);

        execSQL(db, "DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "onDowngrade , oldV=" + oldVersion + " , newV=" + newVersion);
    }

    private void execSQL(SQLiteDatabase db, String sql) {
        try {
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean insert(String name, String age, String sex, String score) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("age", age);
        cv.put("sex", sex);
        cv.put("score", score);

        long result = db.insert("table_student", null, cv);

        return result != -1;
    }

    public String getAll() {
        StringBuilder data = new StringBuilder();

        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.query("table_student", null, null, null, null, null, null);
        try {
            if (c != null) {
                while (c.moveToNext()) {
                    data.append("Id : " + c.getString(c.getColumnIndex("_id")) + "\n");
                    data.append("Name : " + c.getString(c.getColumnIndex("name")) + "\n");
                    data.append("Age : " + c.getString(c.getColumnIndex("age")) + "\n");
                    data.append("Sex : " + c.getString(c.getColumnIndex("sex")) + "\n");
                    data.append("Score : " + c.getString(c.getColumnIndex("score")) + "\n");
                    data.append("\n");
                }
            }
        } finally {
            if (c != null) {
                c.close();
            }
        }

        return data.toString();
    }

    public void update(String id, String name, String age, String sex, String score) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("_id", id);
        cv.put("name", name);
        cv.put("age", age);
        cv.put("sex", sex);
        cv.put("score", score);

        db.update("table_student", cv, "_id = ?", new String[]{id});
    }

    public int delete(String name) {
        SQLiteDatabase db = getWritableDatabase();

        int num = db.delete("table_student", "name = ? ", new String[]{name});
        return num;
    }

    public String getPath() {
        SQLiteDatabase db = getWritableDatabase();

        return db.getPath();
    }
}
