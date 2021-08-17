package archelon.adultEmergence.normal

import android.app.Application
import android.location.Location
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import archelon.GPS.ViewModelGPSListener
import archelon.dataModels.Room.adult.Normal.EmergenceNormalDB
import kotlinx.coroutines.launch

class AdultEmergenceNAViewModel(application: Application, private val repo: AdultEmergenceNARepo): AndroidViewModel(application), ViewModelGPSListener{

    //Radio Controls
    private var _isSuspectedNest = MutableLiveData(false)
    val isSuspectedNest: LiveData<Boolean> = _isSuspectedNest

    private var _hasDugAndNoEggsFound = MutableLiveData(false)
    val hasDugAndNoEggsFound: LiveData<Boolean> = _hasDugAndNoEggsFound

    private var _trackType = MutableLiveData('A')
    val trackType: LiveData<Char> = _trackType

    private var _isLBM = MutableLiveData(true)
    val isLBM: LiveData<Boolean> = _isLBM

    private var _isRBM = MutableLiveData(false)
    val isRBM: LiveData<Boolean> = _isRBM

    private var _is3BM = MutableLiveData(false)
    val is3BM: LiveData<Boolean> = _is3BM

    //
    val response = MutableLiveData(false)


    //Distances
    private var _distanceToSea = MutableLiveData(0f)
    var distanceToSea : LiveData<Float> = _distanceToSea
    private var _distanceToLBM = MutableLiveData(0f)
    var distanceToLBM : LiveData<Float> = _distanceToLBM
    private var _distanceToRBM = MutableLiveData(0f)
    var distanceToRBM : LiveData<Float> = _distanceToRBM
    private var _distanceTo3BM = MutableLiveData(0f)
    var distanceTo3BM : LiveData<Float> = _distanceTo3BM

    //photoid
    private var _photoID = MutableLiveData("None")
    var photoID : LiveData<String> = _photoID


    private var _gpsLong = MutableLiveData(0.0)
    var gpsLong: LiveData<Double> = _gpsLong
    private var _gpsLat = MutableLiveData(0.0)
    var gpsLat: LiveData<Double> = _gpsLat

    private var _sectorName = MutableLiveData("")
    var sectorName: LiveData<String> = _sectorName

    private var _tag = MutableLiveData("")
    var tag: LiveData<String> = _tag

    private var _comment = MutableLiveData("")
    var comment: LiveData<String> = _comment

    fun updateSuspectedNest(suspectNest:Boolean){
        viewModelScope.launch {
            _isSuspectedNest.value = suspectNest
        }
    }

    fun updateDugAndEgg(dugAndEgg:Boolean){
        viewModelScope.launch {
            _hasDugAndNoEggsFound.value = dugAndEgg
        }
    }

    fun updateTrackType(trackType:Char){
        viewModelScope.launch {
            _trackType.value = trackType
        }
    }

    fun updateBM(hasLBM : Boolean, hasRBM : Boolean){
        viewModelScope.launch {
            //For function completeness
            if(hasLBM && hasRBM){
                _isLBM.value = true
                _isRBM.value = false
                _is3BM.value = false
            }else{
                _isLBM.value = hasLBM
                _isRBM.value = hasRBM
                _is3BM.value = !hasLBM && !hasRBM
            }

        }
    }

    fun updateDistanceToLBM(s : CharSequence,
                             @Suppress("UNUSED_PARAMETER")start:Int,
                             @Suppress("UNUSED_PARAMETER")end:Int,
                             @Suppress("UNUSED_PARAMETER")count:Int){
        viewModelScope.launch {
            //convert to int if possible
            val number = s.toString().toFloatOrNull()

            //Check is value number
            if (number != null && number >= 0.0) {
                _distanceToLBM.value =number
            }else{
                _distanceToLBM.value = 0.0f
            }
        }
    }

    fun updateDistanceToRBM(s : CharSequence,
                            @Suppress("UNUSED_PARAMETER")start:Int,
                            @Suppress("UNUSED_PARAMETER")end:Int,
                            @Suppress("UNUSED_PARAMETER")count:Int){
        viewModelScope.launch {
            //convert to int if possible
            val number = s.toString().toFloatOrNull()

            //Check is value number
            if (number != null && number >= 0.0) {
                _distanceToRBM.value =number
            }else{
                _distanceToRBM.value = 0.0f
            }
        }
    }

    fun updateDistanceTo3BM(s : CharSequence,
                            @Suppress("UNUSED_PARAMETER")start:Int,
                            @Suppress("UNUSED_PARAMETER")end:Int,
                            @Suppress("UNUSED_PARAMETER")count:Int){
        viewModelScope.launch {
            //convert to int if possible
            val number = s.toString().toFloatOrNull()

            //Check is value number
            if (number != null && number >= 0.0) {
                _distanceTo3BM.value =number
            }else{
                _distanceTo3BM.value = 0.0f
            }
        }
    }

    fun updateDistanceToSea(s : CharSequence,
                            @Suppress("UNUSED_PARAMETER")start:Int,
                            @Suppress("UNUSED_PARAMETER")end:Int,
                            @Suppress("UNUSED_PARAMETER")count:Int){
        viewModelScope.launch {
            //convert to int if possible
            val number = s.toString().toFloatOrNull()

            //Check is value number
            if (number != null && number >= 0.0) {
                _distanceToSea.value =number
            }else{
                _distanceToSea.value = 0.0f
            }
        }
    }

    fun addImage(id:String){
        viewModelScope.launch {
            _photoID.value = id
        }
    }

    fun resetResponse(){
        viewModelScope.launch {
            response.value = false
        }
    }

    override fun setLocation(location: Location?){
        viewModelScope.launch {
            if(location != null){
                _gpsLat.value = location.latitude
                _gpsLong.value = location.longitude
            }
        }
    }

    fun updateSector(s : CharSequence,
                     @Suppress("UNUSED_PARAMETER")start:Int,
                     @Suppress("UNUSED_PARAMETER")end:Int,
                     @Suppress("UNUSED_PARAMETER")count:Int)
    {
        viewModelScope.launch {
            _sectorName.value = s.toString()
        }
    }

    fun updateTag(s : CharSequence,
                     @Suppress("UNUSED_PARAMETER")start:Int,
                     @Suppress("UNUSED_PARAMETER")end:Int,
                     @Suppress("UNUSED_PARAMETER")count:Int)
    {
        viewModelScope.launch {
            _tag.value = s.toString()
        }
    }

    fun updateComment(s : CharSequence,
                     @Suppress("UNUSED_PARAMETER")start:Int,
                     @Suppress("UNUSED_PARAMETER")end:Int,
                     @Suppress("UNUSED_PARAMETER")count:Int)
    {
        viewModelScope.launch {
            _comment.value = s.toString()
        }
    }

    fun onClickSubmit(@Suppress("UNUSED_PARAMETER")view:View){
        viewModelScope.launch {
            if(hasFilledInAllLiveData()){
               //Get last emergence survey ID
                val idEM = repo.getEmergenceID()

                //Create Data Model
                val dataModel = EmergenceNormalDB(
                    0,
                    idEM,
                    isSuspectedNest.value!!,
                    hasDugAndNoEggsFound.value!!,
                    trackType.value!!,
                    isLBM.value!!,
                    isRBM.value!!,
                    is3BM.value!!,
                    distanceToSea.value!!,
                    distanceToLBM.value!!,
                    distanceToRBM.value!!,
                    distanceTo3BM.value!!,
                    gpsLong.value!!,
                    gpsLat.value!!,
                    sectorName.value!!,
                    tag.value!!,
                    comment.value!!
                )

                repo.uploadEmergenceNormal(dataModel)

                response.value = true

            }else{
                //TODO: show some error message
                Log.i("test", "missing information")
            }
        }
    }

    //Radio is not check as by default they have a value
    fun hasFilledInAllLiveData():Boolean{
        return (sectorName.value != "" &&
                tag.value != "" &&
                gpsValid() &&
                photoID.value != "None"
                )
    }

    private fun gpsValid():Boolean{
        return gpsLat.value!! >= -90 &&
                gpsLat.value!! <= 90 &&
                gpsLong.value!! >= -180 &&
                gpsLong.value!! <= 180
    }

    fun removeLastAdultMain() {
        viewModelScope.launch {
            //tell repo to delete record in database
            repo.removeLastAdultMain()
        }
    }

}
