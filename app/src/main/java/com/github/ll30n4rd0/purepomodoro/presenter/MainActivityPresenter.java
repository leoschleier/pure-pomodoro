package com.github.ll30n4rd0.purepomodoro.presenter;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.github.ll30n4rd0.purepomodoro.interfaces.BaseMVP;
import com.github.ll30n4rd0.purepomodoro.interfaces.MainActivityContract;

import java.util.HashMap;

public class MainActivityPresenter implements MainActivityContract.IPresenter {

    @Nullable
    private MainActivityContract.IModel model;
    private MainActivityContract.IView view;

    private Timer timer;

    public MainActivityPresenter(MainActivityContract.IModel model) {
        this.model = model;
    }

    @Override
    public void subscribe(@NonNull MainActivityContract.IView view) {
        subscribe(view, null);
    }

    @Override
    public void subscribe(@NonNull MainActivityContract.IView view, @Nullable MainActivityContract.IState state) {
        this.view = view;
        if(state != null){
            HashMap<String, Object> stateItems = state.getStateItems();
            long durationSeconds = (long) stateItems.get("durationSeconds");
            long timeLeftMillis = (long) stateItems.get("timeLeftMillis");
            boolean timerRunning = (boolean) stateItems.get("timerRunning");
            long endTimeMillis = (long) stateItems.get("endTimeMillis");

            timer = new Timer(durationSeconds, timeLeftMillis, timerRunning);
            if(timerRunning){
                timeLeftMillis = endTimeMillis - System.currentTimeMillis();
                timer = new Timer(durationSeconds, timeLeftMillis, timerRunning);
                startButtonClicked();
            }else if(durationSeconds!=timeLeftMillis){
                pauseButtonClicked();
            }else{
                stopButtonClicked();
            }
        }else{
            //Todo: Load Pomodoro
            timer = new Timer(10);
        }
    }

    @Override
    public void unsubscribe() {
        view = null;
    }



    @NonNull
    @Override
    public MainActivityContract.IState getState() {
        HashMap<String, Object> stateItems = new HashMap<>();
        stateItems.put("timeLeftMillis", timer.timeLeftMillis);
        stateItems.put("durationSeconds", timer.durationSeconds);
        stateItems.put("timerRunning", timer.timerRunning);
        stateItems.put("stopTimeMillis", System.currentTimeMillis());

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
    public void stopButtonClicked() {
        timer.stop();
        view.setTimerStoppedButtonInterface();
    }

    @Override
    public void skipButtonClicked() {
        //Todo: Add skip functionality
        //view.setTimerSkippedButtonInterface();
    }

    private void timerFinished(){
        view.setTimerStoppedButtonInterface();
    }


    private void updateTimer(long timeMillis){
        int hours = (int) timeMillis / 1000 / 3600;
        int minutes = (int) timeMillis / 1000 % 3600 / 60;
        int seconds = (int) timeMillis / 1000 % 60;

        String timerTextFormatted = formatTimerText(hours, minutes, seconds);

        view.setTimerText(timerTextFormatted);
    }

    private String formatTimerText(int hours, int minutes, int seconds){
        String timerTextFormatted;
        if (hours > 0) {
            timerTextFormatted = String.format("%d:%02d:%02d", hours, minutes, seconds);
        } else {
            timerTextFormatted = String.format("%02d:%02d", minutes, seconds);
        }
        return timerTextFormatted;
    }
    @Override
    public void onMainActivityStop(){
        if (timer.countDownTimer != null) {
            timer.countDownTimer.cancel();
        }
    }

    private class Timer{

        private long timeLeftMillis;
        private final long durationSeconds;
        private long endTime;
        private boolean timerRunning;
        private CountDownTimer countDownTimer;

        private Timer(long durationSeconds){
            this.durationSeconds = durationSeconds;
            timeLeftMillis = durationSeconds*1000;
            timerRunning = false;
        }

        private Timer(long durationSeconds, long timLeftMillis, boolean timerRunning){
            this.durationSeconds = durationSeconds;
            this.timeLeftMillis = timLeftMillis;
            this.timerRunning = timerRunning;
        }

        private void start() {
            endTime = System.currentTimeMillis() + timeLeftMillis;
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
            }.start();
            timerRunning = true;
        }

        private void stop() {
            countDownTimer.cancel();
            timerRunning = false;
            resetTimer();
        }

        private void pause(){
            countDownTimer.cancel();
            timerRunning = false;
        }

        public void resetTimer() {
            setTimer(durationSeconds);
        }

        private void setTimer(long durationSeconds) {
            timeLeftMillis = durationSeconds*1000;
            updateTimer(timeLeftMillis);
        }
    }

}
