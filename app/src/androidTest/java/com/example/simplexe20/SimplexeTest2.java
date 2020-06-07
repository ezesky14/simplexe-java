package com.example.simplexe20;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
public class SimplexeTest2 {


    @Rule
    public ActivityScenarioRule<Maxi2vActivity> activityScenarioRule
            = new ActivityScenarioRule<>(Maxi2vActivity.class);

    @Test
    public void testAddingSimplexeProg() {
        onView(withId(R.id.txt_e1x1))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txt_e1x2))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txt_be1))
                .perform(typeText("500"), closeSoftKeyboard());

        onView(withId(R.id.txt_e2x1))
                .perform(typeText("6"), closeSoftKeyboard());
        onView(withId(R.id.txt_e2x2))
                .perform(typeText("4"), closeSoftKeyboard());
        onView(withId(R.id.txt_be2))
                .perform(typeText("1200"), closeSoftKeyboard());

        onView(withId(R.id.txt_e3x1))
                .perform(typeText("10"), closeSoftKeyboard());
        onView(withId(R.id.txt_e3x2))
                .perform(typeText("2"), closeSoftKeyboard());
        onView(withId(R.id.txt_be3))
                .perform(typeText("1800"), closeSoftKeyboard());

        onView(withId(R.id.txt_zx1))
                .perform(typeText("2500"), closeSoftKeyboard());
        onView(withId(R.id.txt_zx2))
                .perform(typeText("2000"), closeSoftKeyboard());

        onView(withText("Proceder au calcul")).perform(scrollTo()).perform(click());

        verifyMatchOfTable(0);


    }

    private void verifyMatchOfTable(int position) {

        onView(withId(R.id.e1x1)).check(matches(withText("")));
        onView(withId(R.id.e1x2)).check(matches(withText("")));
        onView(withId(R.id.p1e1)).check(matches(withText("")));
        onView(withId(R.id.p1e2)).check(matches(withText("")));
        onView(withId(R.id.p1e3)).check(matches(withText("")));
        onView(withId(R.id.p1z)).check(matches(withText("")));
        onView(withId(R.id.be1)).check(matches(withText("")));

        onView(withId(R.id.e2x1)).check(matches(withText("")));
        onView(withId(R.id.e2x2)).check(matches(withText("")));
        onView(withId(R.id.p2e1)).check(matches(withText("")));
        onView(withId(R.id.p2e2)).check(matches(withText("")));
        onView(withId(R.id.p2e3)).check(matches(withText("")));
        onView(withId(R.id.p2z)).check(matches(withText("")));
        onView(withId(R.id.be2)).check(matches(withText("")));

        onView(withId(R.id.e3x1)).check(matches(withText("")));
        onView(withId(R.id.e3x2)).check(matches(withText("")));
        onView(withId(R.id.p3e1)).check(matches(withText("")));
        onView(withId(R.id.p3e2)).check(matches(withText("")));
        onView(withId(R.id.p3e3)).check(matches(withText("")));
        onView(withId(R.id.p3z)).check(matches(withText("")));
        onView(withId(R.id.be3)).check(matches(withText("")));

        onView(withId(R.id.zx1)).check(matches(withText("")));
        onView(withId(R.id.zx2)).check(matches(withText("")));
        onView(withId(R.id.p1z)).check(matches(withText("")));
        onView(withId(R.id.p2z)).check(matches(withText("")));
        onView(withId(R.id.p3z)).check(matches(withText("")));
        onView(withId(R.id.bz)).check(matches(withText("")));
    }

}
