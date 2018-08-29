package org.sairaa.newsultimate;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class HeaderRVTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);
    private IdlingResource idlingResource;
    final int POSITION = 1;
    @Before
    public void registerIdlingResource() {
        idlingResource = mainActivityActivityTestRule.getActivity().getIdlingResource();
        // To prove that the test fails, omit this call:
        IdlingRegistry.getInstance().register(idlingResource);
    }
    @Test
    public void itemList_hasSpecialText() {
        // First, scroll to the view holder using the isInTheMiddle() matcher.
        onView(ViewMatchers.withId(R.id.header_recycler_view))
                .perform(RecyclerViewActions.<HeaderRecyclerAdapter.viewHolder>scrollToPosition(1));

        // Check that the item has the special text.
        String middleElementText =
                mainActivityActivityTestRule.getActivity().getResources()
                        .getString(R.string.sports);
        onView(withText(middleElementText)).check(matches(isDisplayed()));
    }
    @Test
    public void itemList_onClick_hasFixedLengthOfRV() {
        // First, scroll to the view holder using the isInTheMiddle() matcher.
        onView(ViewMatchers.withId(R.id.header_recycler_view))
                .perform(RecyclerViewActions.<HeaderRecyclerAdapter.viewHolder>actionOnItemAtPosition(POSITION,click()));

        onView(ViewMatchers.withId(R.id.body_recycler_view)).check(matches(isDisplayed()));
//                .perform(RecyclerViewActions.<BodyRecyclerAdapter.viewHolder>actionOnItemAtPosition(POSITION,click()));

        // Check that the item has the special text.
//        String middleElementText =
//                mainActivityActivityTestRule.getActivity().getResources()
//                        .getString(R.string.sports);
//        onView(withText(middleElementText)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
//            Espresso.unregisterIdlingResources(mIdlingResource);
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }

//    private static Matcher<HeaderRecyclerAdapter.viewHolder> isInTheMiddle() {
//        return new TypeSafeMatcher<HeaderRecyclerAdapter.viewHolder>() {
//            @Override
//            protected boolean matchesSafely(HeaderRecyclerAdapter.viewHolder customHolder) {
//                return customHolder.getIsInTheMiddle();
//            }
//
//            @Override
//            public void describeTo(Description description) {
//                description.appendText("item in the middle");
//            }
//        };
//    }
}
