package com.agm91.brapp

import android.os.Build
import android.provider.Settings
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agm91.brapp.util.MyApp
import com.agm91.brapp.view.activity.MainActivity
import com.agm91.brapp.view.adapter.StoresAdapter
import com.agm91.brapp.viewmodel.StoresViewModel
import org.hamcrest.Matchers.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun networkMessageIsDisplayed() {
        // GIVEN
        val scenario =
            ActivityScenario.launch(MainActivity::class.java)

        scenario.onActivity { activity ->
            // WHEN
            val isNetworkOn = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
                Settings.System.getInt(
                    MyApp.instance.contentResolver,
                    Settings.System.AIRPLANE_MODE_ON, 0
                ) != 0
            } else {
                Settings.Global.getInt(
                    MyApp.instance.contentResolver,
                    Settings.Global.AIRPLANE_MODE_ON, 0
                ) != 0
            }

            // THEN
            if (isNetworkOn) {
                onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("You are back online!")))
            } else {
                onView(withId(com.google.android.material.R.id.snackbar_text))
                    .check(matches(withText("You are offline")))
            }
        }
    }

    @Test
    fun testAPI() {
        // GIVEN
        val scenario =
            ActivityScenario.launch(MainActivity::class.java)
        val viewModel = StoresViewModel()

        // WHEN
        scenario.onActivity { activity ->
            viewModel.getStores().observe(activity, Observer { apiResponse ->
                // THEN
                Assert.assertTrue(apiResponse.error == null && apiResponse.data != null)
                scenario.onActivity { activity ->
                    val recycler = activity.findViewById<RecyclerView>(R.id.recycler)
                    Assert.assertTrue(recycler.adapter?.itemCount != 0)
                }
            })
        }
    }

    @Test
    fun testRecycler() {
        // GIVEN
        val scenario =
            ActivityScenario.launch(MainActivity::class.java)
        val viewModel = StoresViewModel()

        // WHEN
        scenario.onActivity { activity ->
            viewModel.getStores().observe(activity, Observer { apiResponse ->
                // THEN
                Assert.assertTrue(apiResponse.error == null && apiResponse.data != null)
                scenario.onActivity { activity ->
                    val recycler = activity.findViewById<RecyclerView>(R.id.recycler)
                    Assert.assertTrue(recycler.adapter?.itemCount != 0)
                    recycler.adapter?.itemCount?.let { itemCount ->
                        if (itemCount > 0) {
                            for (i in 0 until itemCount) {
                                //click
                                onView(withId(R.id.item))
                                    .perform(
                                        actionOnItemAtPosition<StoresAdapter.ViewHolder>(
                                            i,
                                            click()
                                        )
                                    )

                                //check if displayed
                                onView(
                                    RecyclerViewMatcher(R.id.item)
                                        .atPositionOnView(i, R.id.constraint)
                                )
                                    .check(matches(isDisplayed()))

                                // checking for the text of the first one item
                                if (i == 0) {
                                    onView(
                                        RecyclerViewMatcher(R.id.item)
                                            .atPositionOnView(i, R.id.phone)
                                    )
                                        .check(matches(withText("813-926-7300")))
                                }

                            }
                        }
                    }

                }
            })
        }
    }

    @Test
    fun testApp() {
        // GIVEN
        val scenario =
            ActivityScenario.launch(MainActivity::class.java)
        val viewModel = StoresViewModel()

        // WHEN
        scenario.onActivity { activity ->
            viewModel.getStores().observe(activity, Observer { apiResponse ->
                // THEN
                Assert.assertTrue(apiResponse.error == null && apiResponse.data != null)
                scenario.onActivity { activity ->
                    val recycler = activity.findViewById<RecyclerView>(R.id.recycler)
                    Assert.assertTrue(recycler.adapter?.itemCount != 0)
                    // select view with text "813-926-7300"
                    onData(allOf(`is`(instanceOf(String::class.java)), `is`("813-926-7300")))
                        .perform(click())
                    onView(withId(R.id.phone))
                        .check(matches(withText("813-926-7300")))
                }
            })
        }
    }
}
