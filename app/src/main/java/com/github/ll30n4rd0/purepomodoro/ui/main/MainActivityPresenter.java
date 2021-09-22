package com.github.ll30n4rd0.purepomodoro.ui.main;

import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.ll30n4rd0.purepomodoro.data.db.model.Pomodoro;

import java.util.HashMap;

public class MainActivityPresenter implements MainActivityContract.IPresenter {

    @Nullable
    private MainActivityContract.IModel model;
    private MainActivityContract.IView view;

    private Timer timer;

    public MainActivityPresenter() {
        this.model = new Pomodoro();
    }
    public MainActivityPresenter(MainActivityContract.IModel model) {
        this.model = new Pomodoro();
    }

    @Override
    public void subscribe(@NonNull MainActivityContract.IView view) {
        subscribe(view, null);
    }

    @Override
    public void subscribe(@NonNull MainActivityContract.IView view, @Nullable MainActivityContract.IState state) {
        this.view = view;
        if (state != null) {
            HashMap<MainActivityContract.IState.StateItems, Object> stateItems = (HashMap<MainActivityContract.IState.StateItems, Object>) state.getStateItems();
            long durationSeconds = (long) stateItems.get(MainActivityState.StateItems.DURATION_SECONDS);
            long timeLeftMillis = (long) stateItems.get(MainActivityState.StateItems.TIME_LEFT_MILLIS);
            boolean timerRunning = (boolean) stateItems.get(MainActivityState.StateItems.TIMER_RUNNING);
            long stopTimeMillis = (long) stateItems.get(MainActivityState.StateItems.STOP_TIME_MILLIS);

            timer = new Timer(durationSeconds, timeLeftMillis, timerRunning);

            if (timerRunning) {
                timeLeftMillis = timeLeftMillis - (System.currentTimeMillis() - stopTimeMillis);
                timer = new Timer(durationSeconds, timeLeftMillis, true);
                startButtonClicked();
            } else if (durationSeconds*1000 != timeLeftMillis) {
                pauseButtonClicked();
            } else {
                stopButtonClicked();
            }
        } else {
            //Todo: Load Pomodoro
            timer = new Timer(model.getWorkDurationSeconds());
        }
    }

    @Override
    public void unsubscribe() {
        view = null;
    }


    @NonNull
    @Override
    public MainActivityContract.IState getState() {
        HashMap<MainActivityState.StateItems, Object> stateItems = new HashMap<>();
        stateItems.put(MainActivityState.StateItems.TIME_LEFT_MILLIS, timer.timeLeftMillis);
        stateItems.put(MainActivityState.StateItems.DURATION_SECONDS, timer.durationSeconds);
        stateItems.put(MainActivityState.StateItems.TIMER_RUNNING, timer.timerRunning);
        stateItems.put(MainActivityState.StateItems.STOP_TIME_MILLIS, System.currentTimeMillis());

        return new MainActivityState(stateItems);
    }

    @Override
    public void startButtonClicked() {
        timer.start();
        view.setTimerRunningButtonInterface();
    }

    @Override
    public void pauseButtonClicked() {
        timer.pause();
        view.setTimerPausedButtonInterface();
    }

    @Override
    public void continueButtonClicked() {
        startButtonClicked();
    }

    @Override
    public void stopButtonClicked() {
        timer.stop();
        view.setTimerStoppedButtonInterface();
    }

    private void timerFinished() {
        view.setTimerStoppedButtonInterface();
    }


    private void updateTimer(long timeMillis) {
        int hours = (int) timeMillis / 1000 / 3600;
        int minutes = (int) timeMillis / 1000 % 3600 / 60;
        int seconds = (int) timeMillis / 1000 % 60;

        String timerTextFormatted = formatTimerText(hours, minutes, seconds);

        view.setTimerText(timerTextFormatted);
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

    @Override
    public void onMainActivityStop() {
        if (timer.countDownTimer != null) {
            timer.countDownTimer.cancel();
        }
    }

    private class Timer {

        private long timeLeftMillis;
        private final long durationSeconds;
        private boolean timerRunning;
        private CountDownTimer countDownTimer;

        private Timer(long durationSeconds) {
            this.durationSeconds = durationSeconds;
            timeLeftMillis = durationSeconds * 1000;
            timerRunning = false;
            initCountDownTimer();
        }

        private Timer(long durationSeconds, long timLeftMillis, boolean timerRunning) {
            this.durationSeconds = durationSeconds;
            this.timeLeftMillis = timLeftMillis;
            this.timerRunning = timerRunning;
            initCountDownTimer();
        }

        private void initCountDownTimer(){
            countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftMillis = millisUntilFinished;
                    updateTimer(timeLeftMillis);
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

        public void resetTimer() {
            setTimer(durationSeconds);
        }

        private void setTimer(long durationSeconds) {
            timeLeftMillis = durationSeconds * 1000;
            updateTimer(timeLeftMillis);
        }
    }

}
