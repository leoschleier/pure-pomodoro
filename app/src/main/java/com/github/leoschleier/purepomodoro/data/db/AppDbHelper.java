package com.github.leoschleier.purepomodoro.data.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import com.github.leoschleier.purepomodoro.data.db.model.PomodoroSetup;
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
}
