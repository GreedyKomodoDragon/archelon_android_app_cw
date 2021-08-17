package archelon.login.activity

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LogViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var vm: LogViewModel

    @Before
    fun createViewModel(){
        vm = LogViewModel(LoginRepository(MockLoginAPI()) , ApplicationProvider.getApplicationContext())
    }

    @Test
    fun onUsernameChanged() {
        vm.onUsernameChanged("Jake", 0,0,0)
        assertEquals("Jake", vm.username.value)
    }

    @Test
    fun onPasswordChange() {
        vm.onPasswordChange("password", 0,0,0)
        assertEquals("password", vm.password.value)

    }
}