package com.github.leoschleier.purepomodoro.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.github.leoschleier.purepomodoro.R;
import com.github.leoschleier.purepomodoro.ui.base.BaseActivity;
import com.github.leoschleier.purepomodoro.ui.main.MainActivity;

import javax.inject.Inject;

public class SettingsActivity extends BaseActivity implements SettingsActivityContract.ISettingsView{

    @Inject
    SettingsActivityContract.ISettingsPresenter<SettingsActivityContract.ISettingsView> presenter;

    //private EditText editTextInput;
    //private Button setButton;
    //private Button resetButton;

    public static Intent getStartIntent(Context context){
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        setContentView(R.layout.activity_settings);

        //editTextInput = findViewById(R.id.edit_text_input);
        //setButton = findViewById(R.id.set_button);
        //resetButton = findViewById(R.id.reset_button);

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
}
