package com.github.leoschleier.purepomodoro.ui.main;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import androidx.annotation.NonNull;
import com.github.leoschleier.purepomodoro.R;
import com.github.leoschleier.purepomodoro.ui.base.BaseActivity;
import com.github.leoschleier.purepomodoro.ui.settings.SettingsActivity;

import javax.inject.Inject;


public class MainActivity extends BaseActivity implements MainActivityContract.IMainView {

    @Inject
    MainActivityContract.IMainPresenter<MainActivityContract.IMainView> presenter;

    private TextView timerText;
    private Button startButton;
    private Button stopButton;
    private Button pauseButton;
    private Button continueButton;

    public static Intent getStartIntent(Context context){
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timer_text);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        pauseButton = findViewById(R.id.pause_button);
        continueButton = findViewById(R.id.continue_button);

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

    @Override
    protected void onPause(){
        super.onPause();
        presenter.onMainActivityPause();
        presenter.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.settings_option:
                presenter.onSettingsItemClicked();
                return true;
            case R.id.about:
                presenter.onAboutItemClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

    @Override
    public void openSettingsActivity() {
        startActivity(SettingsActivity.getStartIntent(MainActivity.this));
        finish();
    }
}