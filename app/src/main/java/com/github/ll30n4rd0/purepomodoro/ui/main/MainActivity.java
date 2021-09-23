package com.github.ll30n4rd0.purepomodoro.ui.main;

import android.content.SharedPreferences;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.github.ll30n4rd0.purepomodoro.R;
import com.github.ll30n4rd0.purepomodoro.ui.main.MainActivityContract.IMainState;
import com.github.ll30n4rd0.purepomodoro.ui.main.MainActivityContract.IMainView;
import com.github.ll30n4rd0.purepomodoro.ui.main.MainActivityContract.IMainPresenter;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity implements IMainView {

    IMainPresenter presenter;

    //private EditText editTextInput;
    //private Button setButton;
    //private Button resetButton;

    private TextView timerText;
    private Button startButton;
    private Button stopButton;
    private Button pauseButton;
    private Button continueButton;

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
        continueButton = findViewById(R.id.continue_button);

        presenter = new MainActivityMainPresenter();

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

        continueButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { presenter.continueButtonClicked(); }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        IMainState state = StateRepositoryHelper.restoreOnStart(prefs);
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
        continueButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTimerPausedButtonInterface() {
        startButton.setVisibility(View.INVISIBLE);
        continueButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTimerStoppedButtonInterface() {
        startButton.setVisibility(View.VISIBLE);
        continueButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
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
        IMainState presenterState = presenter.getState();
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
        static final IMainState.StateItems DURATION = IMainState.StateItems.DURATION_SECONDS;
        static final IMainState.StateItems TIME_LEFT = IMainState.StateItems.TIME_LEFT_MILLIS;
        static final IMainState.StateItems STOP_TIME = IMainState.StateItems.STOP_TIME_MILLIS;
        static final IMainState.StateItems TIMER_RUNNING = IMainState.StateItems.TIMER_RUNNING;


        private static void saveInstanceState(@NonNull Bundle outState, IMainState state) {
            HashMap<IMainState.StateItems, Object> stateItems = state.getStateItems();
            long durationSeconds = (long) stateItems.get(DURATION);
            long timeLeftMillis = (long) stateItems.get(TIME_LEFT);
            long stopTimeMillis = (long) stateItems.get(STOP_TIME);
            boolean timerRunning = (boolean) stateItems.get(TIMER_RUNNING);

            outState.putLong(String.valueOf(DURATION), durationSeconds);
            outState.putLong(String.valueOf(TIME_LEFT), timeLeftMillis);
            outState.putLong(String.valueOf(STOP_TIME), stopTimeMillis);
            outState.putBoolean(String.valueOf(TIMER_RUNNING), timerRunning);
        }

        private static IMainState restoreInstanceState(@NonNull Bundle savedInstanceState) {
            long durationSeconds = savedInstanceState.getLong(String.valueOf(DURATION));
            long timeLeftMillis = savedInstanceState.getLong(String.valueOf(TIME_LEFT));
            long stopTimeMillis = savedInstanceState.getLong(String.valueOf(STOP_TIME));
            boolean timerRunning = savedInstanceState.getBoolean(String.valueOf(TIMER_RUNNING));

            return createState(durationSeconds, timeLeftMillis, stopTimeMillis, timerRunning);
        }

        private static void saveOnStop(@NonNull SharedPreferences.Editor editor, IMainState state) {
            HashMap<IMainState.StateItems, Object> stateItems = state.getStateItems();
            long durationSeconds = (long) stateItems.get(DURATION);
            long timeLeftMillis = (long) stateItems.get(TIME_LEFT);
            long stopTimeMillis = (long) stateItems.get(STOP_TIME);
            boolean timerRunning = (boolean) stateItems.get(TIMER_RUNNING);

            editor.putLong(String.valueOf(DURATION), durationSeconds);
            editor.putLong(String.valueOf(TIME_LEFT), timeLeftMillis);
            editor.putLong(String.valueOf(STOP_TIME), stopTimeMillis);
            editor.putBoolean(String.valueOf(TIMER_RUNNING), timerRunning);
            editor.apply();
        }

        private static IMainState restoreOnStart(SharedPreferences prefs) {
            IMainState mainActivityState = null;

            long defDuration = 1000000;
            long durationSeconds = prefs.getLong(String.valueOf(DURATION), defDuration);

            if (durationSeconds!=defDuration){
                long timeLeftMillis = prefs.getLong(String.valueOf(TIME_LEFT), durationSeconds);
                long stopTimeMillis = prefs.getLong(String.valueOf(STOP_TIME), 0);
                boolean timerRunning = prefs.getBoolean(String.valueOf(TIMER_RUNNING), false);

                mainActivityState = createState(durationSeconds, timeLeftMillis, stopTimeMillis, timerRunning);
            }

            return mainActivityState;
        }

        private static void clearState(SharedPreferences prefs){
            prefs.edit().clear().apply();
        }

        private static IMainState createState(long durationSeconds, long timeLeftMillis, long stopTimeMillis, boolean timerRunning){
            HashMap<IMainState.StateItems, Object> stateItems = new HashMap<>();
            stateItems.put(DURATION, durationSeconds);
            stateItems.put(TIME_LEFT, timeLeftMillis);
            stateItems.put(STOP_TIME, stopTimeMillis);
            stateItems.put(TIMER_RUNNING, timerRunning);

            return new MainActivityMainState(stateItems);
        }
    }
}