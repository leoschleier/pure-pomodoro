package com.github.leoschleier.purepomodoro.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.github.leoschleier.purepomodoro.data.db.model.PomodoroSetup;
import com.github.leoschleier.purepomodoro.utils.AppConstants;
import com.github.leoschleier.purepomodoro.utils.DbConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDbHelper implements DbHelper{

    private final SQLiteDatabase database;

    @Inject
    public AppDbHelper(DbOpenHelper dbOpenHelper){
        database = dbOpenHelper.getWritableDatabase();
    }

    @Override
    public boolean savePomodoroSetup(PomodoroSetup pomodoroSetup) {
        ContentValues cv = new ContentValues();

        cv.put(DbConstants.COL_NAME, pomodoroSetup.getName());
        cv.put(DbConstants.COL_N_INTERVALS, pomodoroSetup.getnIntervals());
        cv.put(DbConstants.COL_WORK_DURATION, pomodoroSetup.getWorkDurationMin());
        cv.put(DbConstants.COL_SHORT_BREAK_DURATION, pomodoroSetup.getShortBreakDurationMin());
        cv.put(DbConstants.COL_LONG_BREAK_DURATION, pomodoroSetup.getLongBreakDurationMin());

        long insert = database.insert(DbConstants.TABLE_POMODORO_SETUP, null, cv);

        return insert != -1;
    }

    @Override
    public PomodoroSetup getPomodoroSetup(String setupName) {
        String queryString = String.format("SELECT * FROM %s WHERE %s = '%s'", DbConstants.TABLE_POMODORO_SETUP,
                DbConstants.COL_NAME, setupName);

        Cursor cursor = database.rawQuery(queryString, null);

        PomodoroSetup pomodoroSetup = null;

        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex(DbConstants.COL_ID));
            String name = cursor.getString(cursor.getColumnIndex(DbConstants.COL_NAME));
            int nIntervals = cursor.getInt(cursor.getColumnIndex(DbConstants.COL_N_INTERVALS));
            int workDuration = cursor.getInt(cursor.getColumnIndex(DbConstants.COL_WORK_DURATION));
            int shortBreakDuration = cursor.getInt(cursor.getColumnIndex(DbConstants.COL_SHORT_BREAK_DURATION));
            int longBreakDuration = cursor.getInt(cursor.getColumnIndex(DbConstants.COL_LONG_BREAK_DURATION));

            pomodoroSetup = new PomodoroSetup(id, name, nIntervals, workDuration, shortBreakDuration, longBreakDuration);

        }

        return pomodoroSetup;
    }
}
