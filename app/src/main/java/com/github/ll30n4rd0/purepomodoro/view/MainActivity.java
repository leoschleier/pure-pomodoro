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

        presenter.subscribe(this);

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
            public void onClick(View v) {
                presenter.pauseButtonClicked();
            }
        });

        skipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        MainActivityState state = StateRepositoryHelper.restoreOnStart(prefs);
        presenter.subscribe(this, state);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe(this);
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
    public void setTimerRunningButtonInterface(){
        startButton.setVisibility(View.INVISIBLE);
        pauseButton.setVisibility(View.VISIBLE);
        skipButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTimerPausedButtonInterface(){
        startButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        skipButton.setVisibility(View.VISIBLE);
        stopButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void setTimerStoppedButtonInterface(){
        startButton.setVisibility(View.VISIBLE);
        pauseButton.setVisibility(View.INVISIBLE);
        skipButton.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setTimerSkippedButtonInterface(){
        setTimerRunningButtonInterface();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        MainActivityContract.IState presenterState = presenter.getState();
        StateRepositoryHelper.saveInstanceState(outState, presenterState);
        presenter.unsubscribe();
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MainActivityState state = StateRepositoryHelper.restoreInstanceState(savedInstanceState);
        presenter.subscribe(this, state);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        MainActivityContract.IState presenterState = presenter.getState();
        StateRepositoryHelper.saveOnStop(editor, presenterState);

        presenter.onMainActivityStop();
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private static class StateRepositoryHelper{

        private static void saveInstanceState(@NonNull Bundle outState, MainActivityContract.IState state){
            HashMap<String, Object> stateItems = state.getStateItems();
            long durationSeconds = (long) stateItems.get("durationSeconds");
            long timeLeftMillis = (long) stateItems.get("timeLeftMillis");
            long endTimeMillis = (long) stateItems.get("endTimeMillis");
            boolean timerRunning = (boolean) stateItems.get("timerRunning");

            outState.putLong("timeLeftMillis", timeLeftMillis);
            outState.putBoolean("timerRunning", timerRunning);
            outState.putLong("endTimeMillis", endTimeMillis);
            outState.putLong("durationSeconds", durationSeconds);
        }

        private static MainActivityState restoreInstanceState(@NonNull Bundle savedInstanceState){
            long durationSeconds = savedInstanceState.getLong("durationSeconds");
            boolean timerRunning = savedInstanceState.getBoolean("timerRunning");
            long endTimeMillis = savedInstanceState.getLong("endTimeMillis");
            long timeLeftMillis = savedInstanceState.getLong("timeLeftMillis");

            HashMap<String, Object> stateItems = new HashMap<>();
            stateItems.put("durationSeconds", durationSeconds);
            stateItems.put("timerRunning", timerRunning);
            stateItems.put("endTimeMillis", endTimeMillis);
            stateItems.put("timeLeftMillis", timeLeftMillis);

            return new MainActivityState(stateItems);
        }

        private static void saveOnStop(@NonNull SharedPreferences.Editor editor, MainActivityContract.IState state){
            HashMap<String, Object> stateItems = state.getStateItems();
            long durationSeconds = (long) stateItems.get("durationSeconds");
            long timeLeftMillis = (long) stateItems.get("timeLeftMillis");
            long endTimeMillis = (long) stateItems.get("endTimeMillis");
            boolean timerRunning = (boolean) stateItems.get("timerRunning");

            editor.putLong("durationSeconds", durationSeconds);
            editor.putLong("millisLeft", timeLeftMillis);
            editor.putBoolean("timerRunning", timerRunning);
            editor.putLong("endTimeMillis", endTimeMillis);
            editor.apply();
        }

        private static MainActivityState restoreOnStart(SharedPreferences prefs){
            long durationSeconds = prefs.getLong("durationSeconds", 600000);
            long timeLeftMillis = prefs.getLong("timeLeftMillis", durationSeconds);
            long endTimeMillis = prefs.getLong("endTimeMillis", 0);
            boolean timerRunning = prefs.getBoolean("timerRunning", false);

            HashMap<String, Object> stateItems = new HashMap<>();
            stateItems.put("durationSeconds", durationSeconds);
            stateItems.put("timerRunning", timerRunning);
            stateItems.put("endTimeMillis", endTimeMillis);
            stateItems.put("timeLeftMillis", timeLeftMillis);

            return new MainActivityState(stateItems);
        }
    }
}