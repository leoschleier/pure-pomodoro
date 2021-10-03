package com.github.leoschleier.purepomodoro.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.github.leoschleier.purepomodoro.R;
import com.github.leoschleier.purepomodoro.ui.base.BaseActivity;
import com.github.leoschleier.purepomodoro.ui.main.MainActivity;
import com.github.leoschleier.purepomodoro.utils.AppConstants;

import javax.inject.Inject;

public class SettingsActivity extends BaseActivity implements SettingsActivityContract.ISettingsView{

    @Inject
    SettingsActivityContract.ISettingsPresenter<SettingsActivityContract.ISettingsView> presenter;

    private EditText numIntervalsEditText, workDurationEditText, shortBreakDurationEditText, longBreakDurationEditText;
    private Button applyButton;

    public static Intent getStartIntent(Context context){
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setContentView(R.layout.activity_settings);

        numIntervalsEditText = findViewById(R.id.intervals_edit);
        workDurationEditText = findViewById(R.id.work_duration_edit);
        shortBreakDurationEditText = findViewById(R.id.short_break_duration_edit);
        longBreakDurationEditText = findViewById(R.id.long_break_duration_edit);
        applyButton = findViewById(R.id.apply_button);

        applyButton.setOnClickListener(v -> presenter.onApplyButtonClicked());
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
    protected void onResume() {
        super.onResume();
        presenter.subscribe(this);
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        presenter.onBackPressed();
    }

    @Override
    public void openMainActivity() {
        startActivity(MainActivity.getStartIntent(SettingsActivity.this));
        finish();
    }

    @Override
    public void setPomodoroSetupText(int nIntervals, long workDuration, long shortBreakDuration, long longBreakDuration) {
        numIntervalsEditText.setText(String.valueOf(nIntervals));
        workDurationEditText.setText(String.valueOf(workDuration));
        shortBreakDurationEditText.setText(String.valueOf(shortBreakDuration));
        longBreakDurationEditText.setText(String.valueOf(longBreakDuration));
    }

    @Override
    public int getPomodoroIntervals() {
        String input = getEditTextInput(numIntervalsEditText);

        return Integer.parseInt(input);
    }

    @Override
    public long getPomodoroWorkDuration() {
        String input = getEditTextInput(workDurationEditText);

        long duration = Long.parseLong(input);

        return durationValid(duration) ? duration : AppConstants.LONG_NULL_INDEX;
    }

    @Override
    public long getPomodoroShortBreakDuration() {
        String input = getEditTextInput(shortBreakDurationEditText);

        long duration = Long.parseLong(input);

        return durationValid(duration) ? duration : AppConstants.LONG_NULL_INDEX;
    }

    @Override
    public long getPomodoroLongBreakDuration() {
        String input = getEditTextInput(longBreakDurationEditText);

        long duration = Long.parseLong(input);

        return durationValid(duration) ? duration : AppConstants.LONG_NULL_INDEX;
    }

    private boolean durationValid(long duration){
        boolean validFlag = true;

        if (duration == 0) {
            Toast.makeText(SettingsActivity.this, "Please enter a positive number", Toast.LENGTH_SHORT).show();
            validFlag = false;
        }
        return validFlag;
    }

    private String getEditTextInput(EditText editText){
        String input = editText.getText().toString();

        if(input.length() == 0){
            Toast.makeText(SettingsActivity.this, "Field can't be empty", Toast.LENGTH_SHORT).show();
        }

        return input;
    }
}
