package com.example.hardeepproject.app;

/**
 * Created by Deep on 2014-07-11.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddDelete {

    private DataBase db;
    private String[] STUDENT_TABLE_COLUMNS = { DataBase.STUDENT_ID, DataBase.STUDENT_NAME };
    private SQLiteDatabase database;

    public AddDelete(Context context) {
        db = new DataBase(context);
    }

    public void open() throws SQLException {
        database = db.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public Student addStudent(String name) {

        ContentValues values = new ContentValues();
        values.put(DataBase.STUDENT_NAME, name);
        long studId = database.insert(DataBase.STUDENTS, null, values);
        Cursor cursor = database.query(DataBase.STUDENTS,
        STUDENT_TABLE_COLUMNS, DataBase.STUDENT_ID + " = "+ studId, null, null, null, null);
        cursor.moveToFirst();
        Student newComment = parseStudent(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteStudent(Student comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(DataBase.STUDENTS, DataBase.STUDENT_ID
                + " = " + id, null);
    }

    public List getAllStudents() {
        List students = new ArrayList();

        Cursor cursor = database.query(DataBase.STUDENTS,
                STUDENT_TABLE_COLUMNS, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Student student = parseStudent(cursor);
            students.add(student);
            cursor.moveToNext();
        }

        cursor.close();
        return students;
    }

    private Student parseStudent(Cursor cursor) {
        Student student = new Student();
        student.setId((cursor.getInt(0)));
        student.setName(cursor.getString(1));
        return student;
    }
}