package archelon.observersWeather

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import archelon.dataModels.Room.ObserversMSPartialDB
import kotlinx.coroutines.launch

class ObserverViewModel(application: Application, val repo: ObserverRepo) : AndroidViewModel(application) {

    //All the observers
    var msLeader = MutableLiveData("")
    var obTwo = MutableLiveData("")
    var obThree = MutableLiveData("")
    var obFour = MutableLiveData("")

    //Weather aspects (positional data)
    var sky = MutableLiveData(0)
    var precipitation = MutableLiveData(0)
    var wIntensity = MutableLiveData(0)
    var wDirection = MutableLiveData(0)
    var surf = MutableLiveData(0)

    //Control nav
    private var _response = MutableLiveData(false)
    var response: LiveData<Boolean> = _response

    //Error message control
    var error = MutableLiveData(false)


    //One function to handle all the entry points of the observers (cleaner than individual)
    fun updateObserver(
        s: CharSequence,
        idObser:Int
    ) {
        viewModelScope.launch {
            //additional ID is required to tell which mutable value to update
            when(idObser){
                1 -> updateStringMutable(s,msLeader)
                2 -> updateStringMutable(s,obTwo)
                3 -> updateStringMutable(s, obThree)
                4 -> updateStringMutable(s, obFour)
            }

        }
    }

    //using pointers to update any of the string mutable data instances
    private fun updateStringMutable(s: CharSequence, mut:MutableLiveData<String>) {
        if (s.toString().isNotEmpty()) {
            mut.value = s.toString()
        } else {
            mut.value = ""
        }
    }


    //One function to manage the entry of spinners
    fun updateSpinner(position: Int, id: Int){
        viewModelScope.launch {
            when(id){
                1 -> sky.value = position
                2 -> precipitation.value = position
                3 -> wIntensity.value = position
                4 -> wDirection.value = position
                5 -> surf.value = position
            }
        }

    }

    fun submitObserverFragmentToDB(@Suppress("UNUSED_PARAMETER")view: View){
        viewModelScope.launch {
            //reset error
            error.value = false
            if(containsAllObservers() && selectedNonDefaults()){
                //get the ms id
                val idMS = repo.getLastMS()

                //create data model
                val obserDB = ObserversMSPartialDB(
                    0,
                    idMS!!.toLong(),
                    msLeader.value!!,
                    obTwo.value!!,
                    obThree.value!!,
                    obFour.value!!,
                    sky.value!!.toString() ,
                    precipitation.value!!.toString(),
                    wIntensity.value!!.toString(),
                    wDirection.value!!.toString(),
                    surf.value!!.toString()
                )

                //upload obserDB
                repo.uploadObser(obserDB)

                _response.value = true


            }else{
                //show error to the user
                error.value = true
            }
        }
    }

    //A check to make sure that all of the observers have been listed
    private fun containsAllObservers():Boolean{
        return !msLeader.value.isNullOrEmpty() &&
                !obTwo.value.isNullOrEmpty() &&
                !obThree.value.isNullOrEmpty() &&
                !obFour.value.isNullOrEmpty()

    }

    //checks that all the spinners have moved off the title
    private fun selectedNonDefaults():Boolean{
        return sky.value != 0 &&
                precipitation.value != 0 &&
                wIntensity.value != 0 &&
                wDirection.value != 0 &&
                surf.value != 0

    }

    //allows user go back using back button
    fun resetResponse(){
        viewModelScope.launch {
            _response.value = false
        }
    }

    fun removeLastMorningSurvey() {
        viewModelScope.launch {
            repo.removeCurrentMS()
        }
    }

}