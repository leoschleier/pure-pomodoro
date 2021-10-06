package com.github.leoschleier.purepomodoro;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import com.github.leoschleier.purepomodoro.ui.main.MainActivity;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.not;


public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    static final int timerTextId = R.id.timer_text;
    static final int startButtonId = R.id.start_button;
    static final int stopButtonId = R.id.stop_button;
    static final int pauseButtonId = R.id.pause_button;
    static final int continueButtonId = R.id.continue_button;


    @Test
    public void checkViewsVisibility(){
        onView(withId(timerTextId)).check(matches(isDisplayed()));

        onView(withId(startButtonId)).check(matches(isDisplayed()));

        onView(withId(pauseButtonId)).check(matches(not(isDisplayed())));

        onView(withId(stopButtonId)).check(matches(not(isDisplayed())));

        onView(withId(continueButtonId)).check(matches(not(isDisplayed())));
    }
}
