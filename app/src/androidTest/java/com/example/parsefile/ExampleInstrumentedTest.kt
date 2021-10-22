package com.example.parsefile

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.navigation.NavDeepLinkBuilder
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.parsefile.activities.MainActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest{

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity>
            = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.parsefile", appContext.packageName)
    }

    @Test
    fun appLaunchesSuccessfully() {
        launchActivity<MainActivity>()
    }

    @Test
     fun validateButtonClick(){
        onView(withId(R.id.button)).perform(click())
     }

    @Test
    fun stubPick() {
        Intents.init()
        val intent : Intent = Intent()
        intent.setAction(Intent.ACTION_PICK)
        intent.setData(Uri.parse("/enc=JFS_hF1tz7qn8X2N0BfBOFBwuBRNSkaqxLUUuXMlVrZxsyGUwABQDel-L2_z3eVrGIstPySENIIT\n" +
                "    o10pymT1hhM82KZNdzWm1J0rVyec-jSLlEBU3cIPW5aNc537huAK0eblzA=="))
        val result: Instrumentation.ActivityResult = Instrumentation.ActivityResult(Activity.RESULT_OK,
                intent)
        intending(toPackage("com.google.android.apps.docs")).respondWith(result)
        onView(withId(R.id.button)).perform(click())
        intended(toPackage("com.google.android.apps.docs"));
        Thread.sleep(10000)
        Intents.release()
    }

    @Test
    fun testRecyclerView(){

    }

}