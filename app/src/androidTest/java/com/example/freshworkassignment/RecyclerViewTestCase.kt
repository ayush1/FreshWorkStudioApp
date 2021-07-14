package com.example.freshworkassignment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.freshworkassignment.view.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class RecyclerViewTestCase {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    //Test case - Gif displayed on screen
    @Test
    fun testRecyclerViewDisplayed(){
        onView(withId(R.id.rv_gif)).check(matches(isDisplayed()))
    }

    //Test case - loader display on screen
    @Test
    fun testLoaderDisplayed() {
        onView(withId(R.id.pg_bar)).check(matches(isDisplayed()))
    }

    //Test case - searching gif
    @Test
    fun testSearch() {
        onView(withId(R.id.et_search)).perform(typeText("panda"));
    }

    //Test case - Favorite Gif displayed on screen
    @Test
    fun testFavoriteRecyclerViewDisplayed(){
        onView(withId(R.id.rv_grid_gif)).check(matches(isDisplayed()))
    }


}