package com.github.leoschleier.purepomodoro.data.db;

import com.github.leoschleier.purepomodoro.data.db.model.PomodoroSetup;

public interface DbHelper {

    public boolean savePomodoroSetup(PomodoroSetup pomodoroSetup);

    public PomodoroSetup getPomodoroSetup(int setupID);

    public PomodoroSetup getCustomOrDefaultSetup();
}
