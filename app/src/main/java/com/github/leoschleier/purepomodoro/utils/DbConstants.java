package com.github.leoschleier.purepomodoro.utils;

public final class DbConstants {

    public static final String DB_NAME = "pomodoro.db";

    public static final String TABLE_POMODORO_SETUP = "pomodoro_setup";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_N_INTERVALS = "n_intervals";
    public static final String COL_WORK_DURATION = "work_duration_min";
    public static final String COL_SHORT_BREAK_DURATION = "short_break_duration_min";
    public static final String COL_LONG_BREAK_DURATION = "long_break_duration_min";

    private DbConstants(){}
}
