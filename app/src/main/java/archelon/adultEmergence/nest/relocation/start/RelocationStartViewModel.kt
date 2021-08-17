package archelon.adultEmergence.nest.relocation.start

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import archelon.adultEmergence.nest.relocation.start.RelocationStartRepo
import kotlinx.coroutines.launch

class RelocationStartViewModel (application: Application, private val repo: RelocationStartRepo) : AndroidViewModel(application) {

    //Start Timer
    private var timerHasBeenStarted = false
    private var startTime = 0L
    private var endTime = 0L

    //comments
    private var _eggFoundComment = MutableLiveData("")
    val eggFoundComment: LiveData<String> = _eggFoundComment

    private var _eggTransplantedComment = MutableLiveData("")
    val eggTransplantedComment: LiveData<String> = _eggTransplantedComment


    //cm distances
    private var _originalDistance = MutableLiveData(0f)
    private var _relocationDistance = MutableLiveData(0f)


    fun onOriginalDistance(s: CharSequence, start: Int, before: Int, count: Int) {
        viewModelScope.launch {
            updatingDistances(s, _originalDistance)
        }
    }

    fun onRelocationDistance(s: CharSequence, start: Int, before: Int, count: Int) {
        viewModelScope.launch {
            updatingDistances(s, _relocationDistance)
        }
    }

    private fun updatingDistances(s: CharSequence, distanceMut:MutableLiveData<Float>) {

        if(s.toString().isEmpty()){
            distanceMut.value = 0f
        }else{
            val distance = s.toString().toFloatOrNull()
            if (distance == null) {
                distanceMut.value = 0f
            } else {
                distanceMut.value = distance
            }
        }

    }

    fun onStartButtonClicked(view: View){
        viewModelScope.launch {
            if(!timerHasBeenStarted){
                startTime = System.currentTimeMillis()
            }
        }
    }

    fun plusEggFoundButtonClicked(view: View){
        viewModelScope.launch {
            if(timerHasBeenStarted){

            }
        }
    }

}