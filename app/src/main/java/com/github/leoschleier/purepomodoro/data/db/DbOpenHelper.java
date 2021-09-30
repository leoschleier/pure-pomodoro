package com.github.leoschleier.purepomodoro.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.github.leoschleier.purepomodoro.di.ApplicationContext;
import com.github.leoschleier.purepomodoro.di.DatabaseInfo;
import com.github.leoschleier.purepomodoro.utils.AppConstants;
import com.github.leoschleier.purepomodoro.utils.DbConstants;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    @Inject
    public DbOpenHelper(@ApplicationContext Context context, @DatabaseInfo String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INT, %s INT, %s INT, %s INT)",
                DbConstants.TABLE_POMODORO_SETUP, DbConstants.COL_ID, DbConstants.COL_NAME, DbConstants.COL_N_INTERVALS,
                DbConstants.COL_WORK_DURATION, DbConstants.COL_SHORT_BREAK_DURATION, DbConstants.COL_LONG_BREAK_DURATION);

        db.execSQL(createTableStatement);

        if(!insertDefaultSetup(db)){
            throw new SQLException("Could not insert default setup.");
        }

    }

    private boolean insertDefaultSetup(SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(DbConstants.COL_NAME, AppConstants.DEFAULT_SETUP_NAME);
        cv.put(DbConstants.COL_N_INTERVALS, AppConstants.DEFAULT_N_INTERVALS);
        cv.put(DbConstants.COL_WORK_DURATION, AppConstants.DEFAULT_WORK_DURATION);
        cv.put(DbConstants.COL_SHORT_BREAK_DURATION, AppConstants.DEFAULT_SHORT_BREAK_DURATION);
        cv.put(DbConstants.COL_LONG_BREAK_DURATION, AppConstants.DEFAULT_LONG_BREAK_DURATION);

        long insert = db.insert(DbConstants.TABLE_POMODORO_SETUP, null, cv);

        return insert != -1;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
