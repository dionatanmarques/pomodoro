package br.com.dionatanmarques.pomodoro.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.dionatanmarques.pomodoro.entity.Task;
import br.com.dionatanmarques.pomodoro.helper.DBPomodoroHelper;

public class TaskDao {
    public static final String TABLE_NAME = "task";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    public static final String COLUMN_NAME_POMODORO = "pomodoro";
    public static final String COLUMN_NAME_STATUS = "status";

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER NOT NULL PRIMARY KEY," +
                    COLUMN_NAME_TITLE + " TEXT NOT NULL," +
                    COLUMN_NAME_DESCRIPTION + " TEXT NOT NULL," +
                    COLUMN_NAME_POMODORO + " INTEGER NOT NULL," +
                    COLUMN_NAME_STATUS + " INTEGER NOT NULL DEFAULT 0" +
                    ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    private DBPomodoroHelper dbPomodoroHelper;

    public TaskDao(Context context) {
        this.dbPomodoroHelper = new DBPomodoroHelper(context);
    }

    public long save(Task task) {
        SQLiteDatabase database = dbPomodoroHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, task.getTitle());
        values.put(COLUMN_NAME_DESCRIPTION, task.getDescription());
        values.put(COLUMN_NAME_POMODORO, task.getPomodoro());
        if (task.getId() == null) {
            return database.insert(TABLE_NAME, null, values);
        }
        String selection = COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(task.getId()) };
        return database.update(TABLE_NAME, values, selection, selectionArgs);
    }

    public Task findById(int id) {
        SQLiteDatabase database = dbPomodoroHelper.getWritableDatabase();
        String[] projection = {
                COLUMN_NAME_ID,
                COLUMN_NAME_TITLE,
                COLUMN_NAME_DESCRIPTION,
                COLUMN_NAME_POMODORO,
                COLUMN_NAME_STATUS
        };
        String selection = COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(id) };
        Cursor cursor = database.query(TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            Task task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
            return task;
        }
        return null;
    }

    public void done(int id) {
        SQLiteDatabase database = dbPomodoroHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_STATUS, 1);
        String selection = COLUMN_NAME_ID + " LIKE ?";
        String[] selectionArgs = { String.valueOf(id) };
        database.update(TABLE_NAME, values, selection, selectionArgs);

    }

    public List<Task> findAll() {
        SQLiteDatabase database = dbPomodoroHelper.getReadableDatabase();
        String[] projection = {
                COLUMN_NAME_ID,
                COLUMN_NAME_TITLE,
                COLUMN_NAME_DESCRIPTION,
                COLUMN_NAME_POMODORO,
                COLUMN_NAME_STATUS
        };
        String sortOrder = COLUMN_NAME_STATUS;

        Cursor cursor = database.query(TABLE_NAME, projection, null, null, null, null, sortOrder);
        List<Task> tasks = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4));
                tasks.add(task);
            } while (cursor.moveToNext());
        }
        return  tasks;
    }

    public void delete(int rowId) {
        SQLiteDatabase database = dbPomodoroHelper.getWritableDatabase();
        String selection = COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = { String.valueOf(rowId) };
        database.delete(TABLE_NAME, selection, selectionArgs);
    }
}