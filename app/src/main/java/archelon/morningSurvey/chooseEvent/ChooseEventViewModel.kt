package archelon.morningSurvey.chooseEvent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ChooseEventViewModel(application: Application, private val repo: ChooseEventRepo) : AndroidViewModel(application) {

    fun removeCurrentMS() {
        viewModelScope.launch {
            repo.removeMS()
        }
    }

}