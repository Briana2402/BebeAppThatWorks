package com.example.bebeappthatworks;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.bebeappthatworks.R;
import com.example.bebeappthatworks.ui.login.LoginActivity;

public class LoginActivityInstrumentedTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        // The activity is already launched by the ActivityScenarioRule
        ActivityScenario.launch(LoginActivity.class);
    }

    @Test
    public void testLoginButtonDisplayed() {
        // Check if the login button is displayed
        Espresso.onView(withId(R.id.Loggingin))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testLoginSuccess() {
        // Enter email and password and click login button
        Espresso.onView(withId(R.id.username))
                .perform(ViewActions.typeText("maratest10@yahoo.com"));
        Espresso.onView(withId(R.id.password))
                .perform(ViewActions.clearText(), ViewActions.replaceText("Password10!"));
        Espresso.onView(withId(R.id.Loggingin))
                .perform(ViewActions.click());

        // Check if the correct activity is launched after successful login
        Espresso.onView(withId(R.id.Events))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testLoginFailure() {
        // Enter invalid email and password and click login button
        Espresso.onView(withId(R.id.username))
                .perform(ViewActions.typeText("invalid@example.com"));
        Espresso.onView(withId(R.id.password))
                .perform(ViewActions.clearText(), ViewActions.replaceText("Password10!"));
        Espresso.onView(withId(R.id.Loggingin))
                .perform(ViewActions.click());

        // Check if the error message is displayed
        Espresso.onView(withText("Authentication failed."))
                .check(matches(isDisplayed()));
    }
}
