package com.github.leoschleier.purepomodoro.data;

import android.content.Context;
import com.github.leoschleier.purepomodoro.data.db.DbHelper;
import com.github.leoschleier.purepomodoro.data.db.model.PomodoroSetup;
import com.github.leoschleier.purepomodoro.data.prefs.PreferencesHelper;
import com.github.leoschleier.purepomodoro.di.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppDataManager implements DataManager {

    private final Context context;
    private final DbHelper dbHelper;
    private final PreferencesHelper preferencesHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context, DbHelper dbHelper, PreferencesHelper preferencesHelper){
        this.context = context;
        this.dbHelper = dbHelper;
        this.preferencesHelper = preferencesHelper;
    }

    @Override
    public void updateTimerState(long timerDurationSec,
                                 long remainingTimerRuntimeMSec,
                                 long timerStopTimeMSec,
                                 boolean timerRunning,
                                 int pomodoroState,
                                 int nIntervalsCompleted) {
        setTimerDurationSec(timerDurationSec);
        setRemainingTimerRuntimeMSec(remainingTimerRuntimeMSec);
        setTimerStopTimeMSec(timerStopTimeMSec);
        setTimerRunning(timerRunning);
        setPomodoroState(pomodoroState);
        setNIntervalsCompleted(nIntervalsCompleted);
    }

    @Override
    public Long getTimerDurationSec() {
        return preferencesHelper.getTimerDurationSec();
    }

    @Override
    public void setTimerDurationSec(Long timerDurationSec) {
        preferencesHelper.setTimerDurationSec(timerDurationSec);
    }

    @Override
    public Long getRemainingTimerRuntimeMSec() {
        return preferencesHelper.getRemainingTimerRuntimeMSec();
    }

    @Override
    public void setRemainingTimerRuntimeMSec(Long remainingTimerRuntimeMSec) {
        preferencesHelper.setRemainingTimerRuntimeMSec(remainingTimerRuntimeMSec);
    }

    @Override
    public Long getTimerStopTimeMSec() {
        return preferencesHelper.getTimerStopTimeMSec();
    }

    @Override
    public void setTimerStopTimeMSec(Long timerStopTimeMSec) {
        preferencesHelper.setTimerStopTimeMSec(timerStopTimeMSec);
    }

    @Override
    public Boolean getTimerRunning() {
        return preferencesHelper.getTimerRunning();
    }

    @Override
    public void setTimerRunning(Boolean timerRunning) {
        preferencesHelper.setTimerRunning(timerRunning);
    }

    @Override
    public Integer getPomodoroState() {
        return preferencesHelper.getPomodoroState();
    }

    @Override
    public void setPomodoroState(Integer pomodoroState) {
        preferencesHelper.setPomodoroState(pomodoroState);
    }

    @Override
    public Integer getNIntervalsCompleted() {
        return preferencesHelper.getNIntervalsCompleted();
    }

    @Override
    public void setNIntervalsCompleted(Integer nIntervalsCompleted) {
        preferencesHelper.setNIntervalsCompleted(nIntervalsCompleted);
    }

    @Override
    public boolean savePomodoroSetup(PomodoroSetup pomodoroSetup) {
        return dbHelper.savePomodoroSetup(pomodoroSetup);
    }

    @Override
    public PomodoroSetup getPomodoroSetup(int id) {
        return dbHelper.getPomodoroSetup(id);
    }

    @Override
    public PomodoroSetup getCustomOrDefaultSetup() {
        return dbHelper.getCustomOrDefaultSetup();
    }
}
