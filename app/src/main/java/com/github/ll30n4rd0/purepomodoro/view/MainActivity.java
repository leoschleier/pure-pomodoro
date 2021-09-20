package com.github.ll30n4rd0.purepomodoro.view;

import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.ll30n4rd0.purepomodoro.R;
import com.github.ll30n4rd0.purepomodoro.interfaces.MainActivityContract;
import com.github.ll30n4rd0.purepomodoro.presenter.MainActivityPresenter;
import com.github.ll30n4rd0.purepomodoro.presenter.MainActivityState;

import javax.inject.Inject;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements MainActivityContract.IView {

    @Inject
    MainActivityContract.IPresenter presenter;

    //private EditText editTextInput;
    //private Button setButton;
    //private Button resetButton;

    private TextView timerText;
    private Button startButton;
    private Button stopButton;
    private Button pauseButton;
    private Button skipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //editTextInput = findViewById(R.id.edit_text_input);
        //setButton = findViewById(R.id.set_button);
        //resetButton = findViewById(R.id.reset_button);
        timerText = findViewById(R.id.timer_text);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        pauseButton = findViewById(R.id.pause_button);
        skipButton = findViewById(R.id.skip_button);

        presenter = new MainActivityPresenter();

        /*setButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editTextInput.getText().toString();
                if (input.length() == 0) {
                    Toast.makeText(MainActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                long millisInput = Long.parseLong(input) * 60000;
                if (millisInput == 0) {
                    Toast.makeText(MainActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
                }

                setTime(millisInput);
                editTextInput.setText("");
            }
        });*/

        startButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.startButtonClicked();
            }
        });

        stopButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.stopButtonClicked();
            }
        });

        pauseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { presenter.pauseButtonClicked(); }
        });

        skipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { presenter.skipButtonClicked(); }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        MainActivityState state = StateRepositoryHelper.restoreOnStart(prefs);
        presenter.subscribe(this, state);
    }

    /*private void setTime(long milliseconds) {
        startTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }*/

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void setTimerText(String timerTextFormatted) {
        timerText.setText(timerTextFormatted);
    }

    @Override
    public void setTimerRunningButtonInterface() {
        startButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
        skipButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTimerPausedButtonInterface() {
        startButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        skipButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTimerStoppedButtonInterface() {
        startButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        skipButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setTimerSkippedButtonInterface() {
        setTimerRunningButtonInterface();
    }

    /*@Override public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Pass state when subscribing; it can be null.
        presenter.subscribe(this, savedInstanceState != null ? StateRepositoryHelper.restoreInstanceState(savedInstanceState) : null);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        MainActivityContract.IState presenterState = presenter.getState();
        StateRepositoryHelper.saveInstanceState(outState, presenterState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MainActivityState state = StateRepositoryHelper.restoreInstanceState(savedInstanceState);
        presenter.subscribe(this, state);
    }*/

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        MainActivityContract.IState presenterState = presenter.getState();
        StateRepositoryHelper.saveOnStop(editor, presenterState);
        presenter.unsubscribe();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onMainActivityStop();
    }

    private static class StateRepositoryHelper {
        //TODO: Simplify save and restore

        private static void saveInstanceState(@NonNull Bundle outState, MainActivityContract.IState state) {
            HashMap<MainActivityState.StateItems, Object> stateItems = (HashMap<MainActivityState.StateItems, Object>) state.getStateItems();
            long durationSeconds = (long) stateItems.get(MainActivityState.StateItems.DURATION_SECONDS);
            long timeLeftMillis = (long) stateItems.get(MainActivityState.StateItems.TIME_LEFT_MLLIS);
            long stopTimeMillis = (long) stateItems.get(MainActivityState.StateItems.STOP_TIME_MILLIS);
            boolean timerRunning = (boolean) stateItems.get(MainActivityState.StateItems.TIMER_RUNNING);

            outState.putLong(String.valueOf(MainActivityState.StateItems.DURATION_SECONDS), durationSeconds);
            outState.putLong(String.valueOf(MainActivityState.StateItems.TIME_LEFT_MLLIS), timeLeftMillis);
            outState.putLong(String.valueOf(MainActivityState.StateItems.STOP_TIME_MILLIS), stopTimeMillis);
            outState.putBoolean(String.valueOf(MainActivityState.StateItems.TIMER_RUNNING), timerRunning);
        }

        private static MainActivityState restoreInstanceState(@NonNull Bundle savedInstanceState) {
            long durationSeconds = savedInstanceState.getLong(String.valueOf(MainActivityState.StateItems.DURATION_SECONDS));
            long timeLeftMillis = savedInstanceState.getLong(String.valueOf(MainActivityState.StateItems.TIME_LEFT_MLLIS));
            long stopTimeMillis = savedInstanceState.getLong(String.valueOf(MainActivityState.StateItems.STOP_TIME_MILLIS));
            boolean timerRunning = savedInstanceState.getBoolean(String.valueOf(MainActivityState.StateItems.TIMER_RUNNING));

            return createState(durationSeconds, timeLeftMillis, stopTimeMillis, timerRunning);
        }

        private static void saveOnStop(@NonNull SharedPreferences.Editor editor, MainActivityContract.IState state) {
            HashMap<MainActivityState.StateItems, Object> stateItems = (HashMap<MainActivityState.StateItems, Object>) state.getStateItems();
            long durationSeconds = (long) stateItems.get(MainActivityState.StateItems.DURATION_SECONDS);
            long timeLeftMillis = (long) stateItems.get(MainActivityState.StateItems.TIME_LEFT_MLLIS);
            long stopTimeMillis = (long) stateItems.get(MainActivityState.StateItems.STOP_TIME_MILLIS);
            boolean timerRunning = (boolean) stateItems.get(MainActivityState.StateItems.TIMER_RUNNING);

            editor.putLong(String.valueOf(MainActivityState.StateItems.DURATION_SECONDS), durationSeconds);
            editor.putLong(String.valueOf(MainActivityState.StateItems.TIME_LEFT_MLLIS), timeLeftMillis);
            editor.putLong(String.valueOf(MainActivityState.StateItems.STOP_TIME_MILLIS), stopTimeMillis);
            editor.putBoolean(String.valueOf(MainActivityState.StateItems.TIMER_RUNNING), timerRunning);
            editor.apply();
        }

        private static MainActivityState restoreOnStart(SharedPreferences prefs) {
            MainActivityState mainActivityState = null;

            long defDuration = 1000000;
            long durationSeconds = prefs.getLong(String.valueOf(MainActivityState.StateItems.DURATION_SECONDS), defDuration);

            if (durationSeconds!=defDuration){
                long timeLeftMillis = prefs.getLong(String.valueOf(MainActivityState.StateItems.TIME_LEFT_MLLIS), durationSeconds);
                long stopTimeMillis = prefs.getLong(String.valueOf(MainActivityState.StateItems.STOP_TIME_MILLIS), 0);
                boolean timerRunning = prefs.getBoolean(String.valueOf(MainActivityState.StateItems.TIMER_RUNNING), false);

                mainActivityState = createState(durationSeconds, timeLeftMillis, stopTimeMillis, timerRunning);
            }

            return mainActivityState;
        }

        private static void clearState(SharedPreferences prefs){
            prefs.edit().clear().apply();
        }

        private static MainActivityState createState(long durationSeconds, long timeLeftMillis, long stopTimeMillis, boolean timerRunning){
            HashMap<MainActivityState.StateItems, Object> stateItems = new HashMap<>();
            stateItems.put(MainActivityState.StateItems.DURATION_SECONDS, durationSeconds);
            stateItems.put(MainActivityState.StateItems.TIME_LEFT_MLLIS, timeLeftMillis);
            stateItems.put(MainActivityState.StateItems.STOP_TIME_MILLIS, stopTimeMillis);
            stateItems.put(MainActivityState.StateItems.TIMER_RUNNING, timerRunning);

            return new MainActivityState(stateItems);
        }
    }
}