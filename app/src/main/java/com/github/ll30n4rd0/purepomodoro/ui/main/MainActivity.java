package com.github.ll30n4rd0.purepomodoro.ui.main;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import com.github.ll30n4rd0.purepomodoro.R;
import com.github.ll30n4rd0.purepomodoro.data.AppDataManager;
import com.github.ll30n4rd0.purepomodoro.data.DataManager;
import com.github.ll30n4rd0.purepomodoro.data.prefs.AppPreferencesHelper;
import com.github.ll30n4rd0.purepomodoro.data.prefs.PreferencesHelper;
import com.github.ll30n4rd0.purepomodoro.ui.base.BaseActivity;


public class MainActivity extends BaseActivity implements MainActivityContract.IMainView {

    private MainActivityContract.IMainPresenter<MainActivityContract.IMainView> presenter;

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

        //Todo: Injection
        Context context = getApplicationContext();
        PreferencesHelper preferencesHelper = new AppPreferencesHelper(context, "prefs");
        DataManager dataManager = new AppDataManager(context, preferencesHelper);
        presenter = new MainActivityPresenter(dataManager);

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

        startButton.setOnClickListener(v -> presenter.onStartButtonClicked());

        stopButton.setOnClickListener(v -> presenter.onStopButtonClicked());

        pauseButton.setOnClickListener(v -> presenter.onPauseButtonClicked());

        continueButton.setOnClickListener(v -> presenter.onContinueButtonClicked());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe(this);
        presenter.onMainActivityResume();
    }

    /*private void setTime(long milliseconds) {
        startTimeInMillis = milliseconds;
        resetTimer();
        closeKeyboard();
    }*/

    /*private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }*/

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

    @Override
    protected void onPause(){
        super.onPause();
        presenter.onMainActivityPause();
        presenter.unsubscribe();
    }
}