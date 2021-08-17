package archelon.adultEmergence

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import archelon.dataModels.Room.adult.AdultEmergenceMain
import com.decouikit.atom.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class AdultEmergenceViewModel(application: Application, private val repo: AdultEmergenceRepo) : AndroidViewModel(application) {

    private var _imageName = MutableLiveData("None")
    var imageName: LiveData<String> = _imageName

    //To handle Button disability and current button clicked
    var _currentButtonClicked = MutableLiveData<String?>(null)

    var location = MutableLiveData("")

    var Operations = MutableLiveData<ArrayList<String>>(ArrayList())

    val timeStamp = MutableLiveData(getTimeStamp())

    var textShown = MutableLiveData("")

    var numberOfEntries = MutableLiveData(0)

    private var numberOfAttempts = MutableLiveData(0)
    var numberOfAttemptsString = MutableLiveData("")

    private var _missingInformation = MutableLiveData(false)
    var missingInformation : LiveData<Boolean> = _missingInformation




    fun getNumberOfEntries() {
        viewModelScope.launch {
            val currentMSID = repo.getLastMSID()
            numberOfEntries.value = repo.getNumberOfEntries(currentMSID)+1
        }
    }

    fun onNumberOfAttemptsChanged(s : CharSequence,
                                  @Suppress("UNUSED_PARAMETER")start:Int,
                                  @Suppress("UNUSED_PARAMETER")end:Int,
                                  @Suppress("UNUSED_PARAMETER")count:Int)
    {
        viewModelScope.launch {
            //convert to int if possible
            val number = s.toString().toIntOrNull()

            numberOfAttemptsString.value = s.toString()

            //Check is value number
            if (number != null && number >= 0) {
                numberOfAttempts.value =number
            }else{
                numberOfAttempts.value = 0
            }
        }

    }

    private fun getTimeStamp(): Long {
        return System.currentTimeMillis() / 1000L
    }

    fun addImage(id: String) {
        viewModelScope.launch {
            _imageName.value = id
        }
    }

    fun buttonPress(v: View) {
        viewModelScope.launch {
            //using the id to get the Text
            when (v.id) {
                R.id.NA_button -> {
                    _currentButtonClicked.value = "NA"
                }
                R.id.BP_button -> {
                    _currentButtonClicked.value = "BP"
                }
                R.id.AEC_button -> {
                    _currentButtonClicked.value = "AEC"
                }
                R.id.SWIM_button -> {
                    _currentButtonClicked.value = "SWIM"
                }
                R.id.SWIM_BP_button -> {
                    _currentButtonClicked.value = "SWIM->BP"
                }
                R.id.SWIM_AEC_button -> {
                    _currentButtonClicked.value = "SWIM->AEC"
                }
                R.id.NEST_button -> {
                    _currentButtonClicked.value = "NEST"
                }
                R.id.SWIM_NEST_button -> {
                    _currentButtonClicked.value = "SWIM->NEST"
                }
            }
        }
    }

    fun onPlusClick(@Suppress("UNUSED_PARAMETER") view: View) {
        viewModelScope.launch {
            //check if one of the selection button has been clicked

            if (_currentButtonClicked.value != null) {

                //added that value to the operations
                Operations.value!!.add(_currentButtonClicked.value!!)

                //update operations list
                textShown.value = Operations.value!!.joinToString(separator = "-")

                //set last button clicked to null so it cannot be repeatably added
                _currentButtonClicked.value = null



            }
        }
    }

    fun onNextButtonClick(@Suppress("UNUSED_PARAMETER") view: View) {
        viewModelScope.launch {
            _missingInformation.value = false

            if (hasFilledInAllInformation()) {

                //Go to normal page
                val currentMSID = repo.getLastMSID()

                val dataModel = AdultEmergenceMain(
                    0L,
                    currentMSID,
                    textShown.value!!,
                    imageName.value!!,
                    numberOfAttempts.value!!
                )

                repo.uploadAdultMain(dataModel)

                //Paths from Wireframe
                if (isNormalPathWay()) {

                    location.value = "normal"

                } else if (isNestPathWay()) {

                    location.value = "nest"

                }

            }
            else {

                _missingInformation.value = true

            }
        }
    }

    fun isNormalPathWay(): Boolean {
        return (Operations.value!!.size == 1 &&
                Operations.value!![0] == "NA")
                ||
                (Operations.value!!.size == 3 &&
                        Operations.value!![0] == "BP" &&
                        Operations.value!![1] == "AEC" &&
                        Operations.value!![2] == "SWIM->BP"
                        )
    }

    fun isNestPathWay(): Boolean {
        return (Operations.value!!.size == 2 &&
                Operations.value!![0] == "BP" &&
                Operations.value!![1] == "NEST"
                )
                ||
                (Operations.value!!.size == 2 &&
                        Operations.value!![0] == "BP" &&
                        Operations.value!![1] == "SWIM->NEST"
                        )
    }


    fun hasFilledInAllInformation():Boolean{
        return Operations.value != null &&
                Operations.value!!.size > 0 &&
                textShown.value != null &&
                textShown.value!! != "" &&
                imageName.value != null &&
                imageName.value!! != "None" &&
                numberOfAttempts.value!! > 0 &&
                (isNestPathWay() ||  isNormalPathWay())
    }

    fun resetResponse(){
        viewModelScope.launch {
            location.value = ""
        }
    }

    fun onClickClearButton(){
        viewModelScope.launch {
            //check that there is any path
            if(Operations.value != null && Operations.value!!.size > 0){
                //remove last operation and update the UI
                Operations.value!!.removeAt(Operations.value!!.size -1)
                textShown.value = Operations.value!!.joinToString(separator = "-")
            }
        }
    }


}