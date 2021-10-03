package com.github.leoschleier.purepomodoro.ui.main;

import android.os.CountDownTimer;
import com.github.leoschleier.purepomodoro.data.DataManager;
import com.github.leoschleier.purepomodoro.data.db.model.PomodoroSetup;
import com.github.leoschleier.purepomodoro.ui.base.BasePresenter;
import com.github.leoschleier.purepomodoro.utils.AppConstants;

import javax.inject.Inject;


public class MainPresenter<V extends MainActivityContract.IMainView> extends BasePresenter<V> implements MainActivityContract.IMainPresenter<V> {

    private Timer timer;
    private PomodoroSetup pomodoroSetup;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Timer restoreTimerState(){
        DataManager dataManager = getDataManager();
        loadPomodoroSetup();

        Long timerDurationSec = dataManager.getTimerDurationSec();
        Long remainingTimerRuntimeMSec = dataManager.getRemainingTimerRuntimeMSec();
        Long timerStopTimeMSec = dataManager.getTimerStopTimeMSec();
        Boolean timerRunning = dataManager.getTimerRunning();
        Integer pomodoroState = dataManager.getPomodoroState();
        Integer nIntervalsCompleted = dataManager.getNIntervalsCompleted();

        timer = new Timer(pomodoroSetup.getWorkDurationMin()*60);

        if (timerDurationSec != null) {
            if(timerRunning){
                remainingTimerRuntimeMSec = remainingTimerRuntimeMSec - (System.currentTimeMillis() - timerStopTimeMSec);
                timer = new Timer(timerDurationSec, remainingTimerRuntimeMSec, true, pomodoroState,
                        nIntervalsCompleted);
                onStartButtonClicked();
            }else if (timerDurationSec*1000 != remainingTimerRuntimeMSec){
                timer = new Timer(timerDurationSec, remainingTimerRuntimeMSec, false, pomodoroState,
                        nIntervalsCompleted);
                onPauseButtonClicked();
            }else{
                onStopButtonClicked();
            }
        }

        updateTimer(timer.remainingTimerRuntimeMSec);

        return timer;
    }

    private void saveTimerState(){
        getDataManager().updateTimerState(timer.timerDurationSec,
                timer.remainingTimerRuntimeMSec,
                System.currentTimeMillis(),
                timer.timerRunning,
                timer.pomodoroState,
                timer.nIntervalsCompleted);
    }

    private void loadPomodoroSetup(){
        pomodoroSetup = getDataManager().getCustomOrDefaultSetup();
    }

    @Override
    public void onStartButtonClicked() {
        timer.start();
        getView().setTimerRunningButtonInterface();
    }

    @Override
    public void onPauseButtonClicked() {
        timer.pause();
        getView().setTimerPausedButtonInterface();
    }

    @Override
    public void onContinueButtonClicked() {
        onStartButtonClicked();
    }

    @Override
    public void onStopButtonClicked() {
        timer.stop();
        loadPomodoroSetup();
        timer = new Timer(pomodoroSetup.getWorkDurationMin()*60);
        updateTimer(timer.timerDurationSec*1000);
        getView().setTimerStoppedButtonInterface();
    }

    @Override
    public void onSettingsItemClicked() {
        getView().openSettingsActivity();
    }

    @Override
    public void onAboutItemClicked() {

    }

    @Override
    public void onMainActivityResume(){
        //TODO: Try load custom setup
        loadPomodoroSetup();
        timer = restoreTimerState();
    }

    @Override
    public void onMainActivityPause(){
        saveTimerState();
        timer.cancelCountDownTimer();
    }

    private void onTimerFinished() {
        //getView().setTimerStoppedButtonInterface();
        // Todo timer finished button interface
        PomodoroSetup pomodoroSetup = getDataManager().getCustomOrDefaultSetup();
        int intervalsSetup = pomodoroSetup.getnIntervals();
        int nIntervalsCompleted = timer.nIntervalsCompleted + 1;

        int nextState;
        long duration;

        if(timer.pomodoroState == Timer.POMODORO_STATE_WORK){
            if(nIntervalsCompleted < intervalsSetup){
                nextState = Timer.POMODORO_STATE_SHORT_BREAK;
                duration = pomodoroSetup.getShortBreakDurationMin()*60;
            }else {
                nextState = Timer.POMODORO_STATE_LONG_BREAK;
                duration = pomodoroSetup.getLongBreakDurationMin()*60;
            }
        } else{
            nextState = Timer.POMODORO_STATE_WORK;
            duration = pomodoroSetup.getWorkDurationMin();
            nIntervalsCompleted = 0;
        }

        timer = new Timer(duration, nextState, nIntervalsCompleted);
        timer.start();
    }

    private void updateTimer(long timeMillis) {
        int hours = (int) timeMillis / 1000 / 3600;
        int minutes = (int) timeMillis / 1000 % 3600 / 60;
        int seconds = (int) timeMillis / 1000 % 60;

        String timerTextFormatted = formatTimerText(hours, minutes, seconds);

        getView().setTimerText(timerTextFormatted);
    }

    private String formatTimerText(int hours, int minutes, int seconds) {
        String timerTextFormatted;
        if (hours > 0) {
            timerTextFormatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timerTextFormatted = String.format("%02d:%02d", minutes, seconds);
        }
        return timerTextFormatted;
    }



    private class Timer {

        private static final int POMODORO_STATE_WORK = 0;
        private static final int POMODORO_STATE_SHORT_BREAK = 1;
        private static final int POMODORO_STATE_LONG_BREAK = 2;

        private long remainingTimerRuntimeMSec;
        private final long timerDurationSec;
        private boolean timerRunning;
        private int pomodoroState;
        private int nIntervalsCompleted;
        private CountDownTimer countDownTimer;

        private Timer(long timerDurationSec) {
            this.timerDurationSec = timerDurationSec;
            remainingTimerRuntimeMSec = timerDurationSec * 1000;
            timerRunning = false;
            pomodoroState = POMODORO_STATE_WORK;
            nIntervalsCompleted = 0;
            initCountDownTimer();
        }

        private Timer(long timerDurationSec, int pomodoroState, int nIntervalsCompleted) {
            this.timerDurationSec = timerDurationSec;
            remainingTimerRuntimeMSec = timerDurationSec * 1000;
            timerRunning = false;
            this.pomodoroState = pomodoroState;
            this.nIntervalsCompleted = nIntervalsCompleted;
            initCountDownTimer();
        }

        private Timer(long timerDurationSec, long remainingTimerRuntimeMSec, boolean timerRunning, int pomodoroState,
                      int nIntervalsCompleted) {
            this.timerDurationSec = timerDurationSec;
            this.remainingTimerRuntimeMSec = remainingTimerRuntimeMSec;
            this.timerRunning = timerRunning;
            this.pomodoroState = pomodoroState;
            this.nIntervalsCompleted = nIntervalsCompleted;
            initCountDownTimer();
        }

        private void initCountDownTimer(){
            countDownTimer = new CountDownTimer(remainingTimerRuntimeMSec, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    remainingTimerRuntimeMSec = millisUntilFinished;
                    updateTimer(remainingTimerRuntimeMSec);
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    onTimerFinished();
                    resetTimer();
                }
            };
        }

        private void start() {
            initCountDownTimer();
            countDownTimer = countDownTimer.start();
            timerRunning = true;
        }

        private void stop() {
            countDownTimer.cancel();
            timerRunning = false;
            resetTimer();
        }

        private void pause() {
            countDownTimer.cancel();
            timerRunning = false;
        }

        private void cancelCountDownTimer(){
            if (timer.countDownTimer != null) {
                timer.countDownTimer.cancel();
            }
        }

        private void resetTimer() {
            setTimer(timerDurationSec);
        }

        private void setTimer(long durationSeconds) {
            remainingTimerRuntimeMSec = durationSeconds * 1000;
            updateTimer(remainingTimerRuntimeMSec);
        }
    }

}
