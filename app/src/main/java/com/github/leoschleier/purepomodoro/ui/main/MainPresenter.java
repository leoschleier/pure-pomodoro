package com.github.leoschleier.purepomodoro.ui.main;

import android.os.CountDownTimer;
import com.github.leoschleier.purepomodoro.data.DataManager;
import com.github.leoschleier.purepomodoro.ui.base.BasePresenter;

import javax.inject.Inject;


public class MainPresenter<V extends MainActivityContract.IMainView> extends BasePresenter<V> implements MainActivityContract.IMainPresenter<V> {

    private Timer timer;

    @Inject
    public MainPresenter(DataManager dataManager) {
        super(dataManager);
    }

    private Timer restoreTimerState(long defaultTimerDurationSec){
        DataManager dataManager = getDataManager();

        Long timerDurationSec = dataManager.getTimerDurationSec();
        Long remainingTimerRuntimeMSec = dataManager.getRemainingTimerRuntimeMSec();
        Long timerStopTimeMSec = dataManager.getTimerStopTimeMSec();
        Boolean timerRunning = dataManager.getTimerRunning();
        //Todo: Load Pomodoro
        //Todo: Place Timer initialization somewhere else
        timer = new Timer(defaultTimerDurationSec);

        if (timerDurationSec != null) {
            if(timerRunning){
                remainingTimerRuntimeMSec = remainingTimerRuntimeMSec - (System.currentTimeMillis() - timerStopTimeMSec);
                timer = new Timer(timerDurationSec, remainingTimerRuntimeMSec, true);
                onStartButtonClicked();
            }else if (timerDurationSec*1000 != remainingTimerRuntimeMSec){
                onPauseButtonClicked();
            }else{
                onStopButtonClicked();
            }
        }

        // Todo: Complete Initialization with StopTime
        return timer;
    }

    private void saveTimerState(){
        DataManager dataManager = getDataManager();

        dataManager.updateTimerState(timer.timerDurationSec,
                timer.remainingTimerRuntimeMSec,
                System.currentTimeMillis(),
                timer.timerRunning);
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
        getView().setTimerStoppedButtonInterface();
    }

    @Override
    public void onSettingsItemClicked() {

    }

    @Override
    public void onMainActivityResume(){
        //TODO: Change defaultDuration
        timer = restoreTimerState(25*60);
    }

    @Override
    public void onMainActivityPause(){
        saveTimerState();
        timer.cancelCountDownTimer();
    }

    private void timerFinished() {
        getView().setTimerStoppedButtonInterface();
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

        private long remainingTimerRuntimeMSec;
        private final long timerDurationSec;
        private boolean timerRunning;
        private CountDownTimer countDownTimer;

        private Timer(long timerDurationSec) {
            this.timerDurationSec = timerDurationSec;
            remainingTimerRuntimeMSec = timerDurationSec * 1000;
            timerRunning = false;
            initCountDownTimer();
        }

        private Timer(long timerDurationSec, long remainingTimerRuntimeMSec, boolean timerRunning) {
            this.timerDurationSec = timerDurationSec;
            this.remainingTimerRuntimeMSec = remainingTimerRuntimeMSec;
            this.timerRunning = timerRunning;
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
                    timerFinished();
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

        public void resetTimer() {
            setTimer(timerDurationSec);
        }

        private void setTimer(long durationSeconds) {
            remainingTimerRuntimeMSec = durationSeconds * 1000;
            updateTimer(remainingTimerRuntimeMSec);
        }
    }

}
