package com.ichsanalfian.mygithubuser.ui.activity

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Before
import org.junit.Test
import com.ichsanalfian.mygithubuser.R

class MainActivityTest {
    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun assertScrollRecyclerView() {
        onView(withId(R.id.rvUser))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rvUser))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(20))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(0))
    }
}