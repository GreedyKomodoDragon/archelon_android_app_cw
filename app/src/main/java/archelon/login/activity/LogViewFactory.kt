package archelon.login.activity

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.restAPI.ArchelonApiLogin.retrofitServiceLogin

class LogViewFactory(private val app:Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogViewModel::class.java)) {
            return LogViewModel(LoginRepository(retrofitServiceLogin), app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}