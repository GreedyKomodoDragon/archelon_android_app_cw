package archelon.adultEmergence.nest.one

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import archelon.dataModels.Room.adult.Nest.AdultNestScreenOneDB
import kotlinx.coroutines.launch

class AdultEmergenceSubOneViewModel(application: Application, private val repo: AdultEmergenceSubOneRepo) : AndroidViewModel(application) {

    //handling location
    private val _response =  MutableLiveData("")
    val response: LiveData<String> = _response

    //Distance and Nest protected Radio
    private val _distanceToTopEggCMDouble = MutableLiveData<Double?>(null)
     val distanceToTopEggCMDouble : LiveData<Double?> = _distanceToTopEggCMDouble
    val distanceToTopEggCM = MutableLiveData("")

    private val _isNestProtected = MutableLiveData(false)
    val isNestProtected: LiveData<Boolean> = _isNestProtected

    //Check Boxes
    private val _hasCageWooden = MutableLiveData(false)
    val isCageWooden: LiveData<Boolean> = _hasCageWooden

    private val _hasCageIron = MutableLiveData(false)
    val isCageIron: LiveData<Boolean> = _hasCageIron

    private val _hasBambooTripod = MutableLiveData(false)
    val hasBambooTripod: LiveData<Boolean> = _hasBambooTripod

    private val _hasScreen = MutableLiveData(false)
    val hasScreen: LiveData<Boolean> = _hasScreen

    private val _hasBeenRelocated = MutableLiveData(false)
    val hasBeenRelocated: LiveData<Boolean> = _hasBeenRelocated

    private val _hasAlleywayChecked = MutableLiveData(false)
    val hasAlleywayChecked: LiveData<Boolean> = _hasAlleywayChecked


    //Other protections commenting textbox
    private val _protectionsComment = MutableLiveData("")
    val protectionsComment: LiveData<String> = _protectionsComment


    //tagged
    private val _hasNestTagged = MutableLiveData(false)
    val hasNestTagged: LiveData<Boolean> = _hasNestTagged

    //track type selection
    private val _trackType = MutableLiveData('A')
    val trackType: LiveData<Char> = _trackType

    //Nest Code
    private val _nestCode = MutableLiveData("ME")
    val nestCode: LiveData<String> = _nestCode


    fun updateTrackType(track:Char){
        viewModelScope.launch {
            _trackType.value = track
        }
    }

    fun updateTagged(isTagged:Boolean){
        viewModelScope.launch {
            _hasNestTagged.value = isTagged
        }
    }

    fun updateDistanceEgg(s : CharSequence,
                          @Suppress("UNUSED_PARAMETER")start:Int,
                          @Suppress("UNUSED_PARAMETER")end:Int,
                          @Suppress("UNUSED_PARAMETER")count:Int){
        viewModelScope.launch {
            //convert to int if possible
            val stringNumber = s.toString()
            val number = s.toString().toDoubleOrNull()

            distanceToTopEggCM.value = stringNumber

            //Check is value number
            if (number != null && number >= 0.0) {
                _distanceToTopEggCMDouble.value = number
            }else{
                _distanceToTopEggCMDouble.value = null
            }
        }
    }

    fun updateOtherProtections(s : CharSequence,
                          @Suppress("UNUSED_PARAMETER")start:Int,
                          @Suppress("UNUSED_PARAMETER")end:Int,
                          @Suppress("UNUSED_PARAMETER")count:Int){
        viewModelScope.launch {
            _protectionsComment.value = s.toString()
            updateNestCode()
        }
    }

    fun updateProtectedNest(isProtected: Boolean){
        viewModelScope.launch {
            _isNestProtected.value = isProtected
        }
    }

    //Check box functions
    fun updateCageWooden(){
        viewModelScope.launch {
            _hasCageWooden.value = !_hasCageWooden.value!!
            updateNestCode()
        }
    }

    fun updateCageIron(){
        viewModelScope.launch {
            _hasCageIron.value = !_hasCageIron.value!!
            updateNestCode()
        }
    }

    fun updateHasBamboo(){
        viewModelScope.launch {
            _hasBambooTripod.value = !_hasBambooTripod.value!!
            updateNestCode()
        }
    }

    fun updateHasScreen(){
        viewModelScope.launch {
            _hasScreen.value = !_hasScreen.value!!
            updateNestCode()
        }
    }

    fun updateRelocated(){
        viewModelScope.launch {
            _hasBeenRelocated.value = !_hasBeenRelocated.value!!
            updateNestCode()
        }
    }

    fun updateAlleywayChecked(){
        viewModelScope.launch {
            _hasAlleywayChecked.value = !_hasAlleywayChecked.value!!
            updateNestCode()
        }
    }

    private fun updateNestCode(){
        _nestCode.value = "ME"

        //Add Cage code
        if(_hasCageIron.value!! || _hasCageWooden.value!!){
            _nestCode.value += "C"
        }

        //Add Screen code
        if(_hasBambooTripod.value!! || _hasScreen.value!!){
            _nestCode.value += "S"
        }

        //Add Screen code
        if(_hasBeenRelocated.value!!){
            _nestCode.value += "R"
        }

        //Add Alleyway code
        if(_hasAlleywayChecked.value!!){
            _nestCode.value += "A"
        }

        //Add other protection code
        if(protectionsComment.value!!.isNotEmpty()){
            _nestCode.value += "O"
        }
    }

    //function to check that all information has been filled in
    //Made to a function so if requirements change, logic is un-affect only function changes
    fun allRequiredInformationFilledIn(): Boolean{
        return _distanceToTopEggCMDouble.value != null &&
                _distanceToTopEggCMDouble.value!! > 0.0
    }

    fun onNextButtonClicked(view: View){
        viewModelScope.launch {
            if(allRequiredInformationFilledIn()){

                //Get last Adult Main ID
                val ADMN = repo.getCurrentAdultMain()

                val dataModel = AdultNestScreenOneDB(
                    0 ,
                    ADMN,
                    _distanceToTopEggCMDouble.value!!,
                    isNestProtected.value!!,
                    isCageWooden.value!!,
                    isCageIron.value!!,
                    hasBambooTripod.value!!,
                    hasScreen.value!!,
                    hasBeenRelocated.value!!,
                    hasAlleywayChecked.value!!,
                    protectionsComment.value!!,
                    hasNestTagged.value!!,
                    trackType.value!!,
                    nestCode.value!!
                )

                repo.uploadNestOne(dataModel)

                if(hasBeenRelocated.value!!){
                    _response.value = "relocated"
                }else{
                    _response.value = "nest"
                }


            }else{
                //TODO: show error message
                Log.i("test", "not filled in all the information")
            }
        }
    }

    fun resetResponse(){
        viewModelScope.launch {
            _response.value = ""
        }
    }

    fun removeLastAdultMain() {
        viewModelScope.launch {
            //tell repo to delete record in database
            repo.removeLastAdultMain()
        }
    }


}