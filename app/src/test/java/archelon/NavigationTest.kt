package archelon

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.decouikit.atom.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @Before
    fun createNavHost(){
        // Create the Activity
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun startsOnLogin(){
        onView(ViewMatchers.withId(R.id.btnLogin)).perform(scrollTo())
        onView(ViewMatchers.withId(R.id.btnLogin)).check(matches(isDisplayed()))
    }

    //Tests were deleted as using REST API makes these tests no longer accurate to the situation
    //Adding Delays with used to make tests pass but this makes test non-deterministic
    //Hence Removal was needed

}