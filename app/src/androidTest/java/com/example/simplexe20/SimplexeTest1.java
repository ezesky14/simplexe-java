package com.example.simplexe20;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SimplexeTest1 {


    public static final String STRING_TO_BE_TYPED = "Espresso";

    /**
     * Use {@link ActivityScenarioRule} to create and launch the activity under test, and close it
     * after test completes. This is a replacement for {@link androidx.test}.
     */
    @Rule public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.txt_e1x1))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txt_e1x2))
                .perform(typeText("0.5"), closeSoftKeyboard());
        onView(withId(R.id.txt_e1x3))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txt_be1))
                .perform(typeText("280"), closeSoftKeyboard());

        onView(withId(R.id.txt_e2x1))
                .perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.txt_e2x2))
                .perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.txt_e2x3))
                .perform(typeText("4"), closeSoftKeyboard());
        onView(withId(R.id.txt_be2))
                .perform(typeText("400"), closeSoftKeyboard());

        onView(withId(R.id.txt_e3x1))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txt_e3x2))
                .perform(typeText("3"), closeSoftKeyboard());
        onView(withId(R.id.txt_e3x3))
                .perform(typeText("0"), closeSoftKeyboard());
        onView(withId(R.id.txt_be3))
                .perform(typeText("600"), closeSoftKeyboard());


        onView(withId(R.id.txt_zx1))
                .perform(typeText("350"), closeSoftKeyboard());
        onView(withId(R.id.txt_zx2))
                .perform(typeText("280"), closeSoftKeyboard());
        onView(withId(R.id.txt_zx3))
                .perform(typeText("300"), closeSoftKeyboard());




        onView(withText("Proceder au calcul")) .perform(scrollTo()).perform(click());

        // Check that the text was changed.

    }




}

