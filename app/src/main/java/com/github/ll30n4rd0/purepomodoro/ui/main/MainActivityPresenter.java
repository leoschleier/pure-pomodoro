package com.github.ll30n4rd0.purepomodoro.ui.main;

import android.os.CountDownTimer;
import androidx.annotation.NonNull;
import com.github.ll30n4rd0.purepomodoro.data.DataManager;
import com.github.ll30n4rd0.purepomodoro.ui.base.BaseMVP;
import com.github.ll30n4rd0.purepomodoro.ui.base.BasePresenter;


public class MainActivityPresenter<V extends MainActivityContract.IMainView> extends BasePresenter<V> implements MainActivityContract.IMainPresenter<V> {


    private Timer timer;

    public MainActivityPresenter(DataManager dataManager) {
        super(dataManager);
    }

    /*@Override
    public void subscribe(@NonNull MainActivityContract.IMainView view, @Nullable MainActivityContract.IMainState state) {
        this.view = view;
        if (state != null) {
            HashMap<MainActivityContract.IMainState.StateItems, Object> stateItems = (HashMap<MainActivityContract.IMainState.StateItems, Object>) state.getStateItems();
            long durationSeconds = (long) stateItems.get(MainActivityMainState.StateItems.DURATION_SECONDS);
            long timeLeftMillis = (long) stateItems.get(MainActivityMainState.StateItems.TIME_LEFT_MILLIS);
            boolean timerRunning = (boolean) stateItems.get(MainActivityMainState.StateItems.TIMER_RUNNING);
            long stopTimeMillis = (long) stateItems.get(MainActivityMainState.StateItems.STOP_TIME_MILLIS);

            timer = new Timer(durationSeconds, timeLeftMillis, timerRunning);

            if (timerRunning) {
                timeLeftMillis = timeLeftMillis - (System.currentTimeMillis() - stopTimeMillis);
                timer = new Timer(durationSeconds, timeLeftMillis, true);
                onStartButtonClicked();
            } else if (durationSeconds*1000 != timeLeftMillis) {
                onPauseButtonClicked();
            } else {
                onStopButtonClicked();
            }
        } else {
            //Todo: Load Pomodoro
            timer = new Timer(model.getWorkDurationSeconds());
        }
    }*/

    private Timer restoreTimerState(long defaultTimerDurationSec){
        DataManager dataManager = getDataManager();

        Long timerDurationSec = dataManager.getTimerDurationSec();
        Long remainingTimerRuntimeMSec = dataManager.getRemainingTimerRuntimeMSec();
        Long timerStopTimeMSec = dataManager.getTimerStopTimeMSec();
        Boolean timerRunning = dataManager.getTimerRunning();
        //Todo: Load Pomodoro
        //Todo: Place Timer initialization somewhere else
        timer = new Timer(defaultTimerDurationSec);;

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
