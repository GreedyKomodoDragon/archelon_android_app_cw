package archelon.adultEmergence.nest.two

import android.app.Application
import android.location.Location
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import archelon.GPS.ViewModelGPSListener
import archelon.dataModels.Room.adult.Nest.AdultNestScreenTwoDB
import kotlinx.coroutines.launch

class AdultEmergenceSubTwoViewModel(application: Application, private val repo: AdultEmergenceSubTwoRepo)
    : AndroidViewModel(application), ViewModelGPSListener {

    //Radio
    private var _hasLBM = MutableLiveData(true)
    var hasLBM : LiveData<Boolean> = _hasLBM

    private var _hasRBM = MutableLiveData(false)
    var hasRBM : LiveData<Boolean> = _hasRBM

    private var _has3BM = MutableLiveData(false)
    var has3BM : LiveData<Boolean> = _has3BM

    //PhotoID
    private var _photoID = MutableLiveData("None")
    var photoID : LiveData<String> = _photoID


    //distance measures
    private var _distanceToSea = MutableLiveData(0f)
    var distanceToSea : LiveData<Float> = _distanceToSea
    private var _distanceToLBM = MutableLiveData(0f)
    var distanceToLBM : LiveData<Float> = _distanceToLBM
    private var _distanceToRBM = MutableLiveData(0f)
    var distanceToRBM : LiveData<Float> = _distanceToRBM
    private var _distanceTo3BM = MutableLiveData(0f)
    var distanceTo3BM : LiveData<Float> = _distanceTo3BM

    //text boxes
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


    //Location control
    private var _response = MutableLiveData(false)
    var response : LiveData<Boolean> = _response

    //Get PhotoID
    fun addImage(id:String){
        viewModelScope.launch {
            _photoID.value = id
        }
    }

    fun onDistanceToSeaChange(s: CharSequence,
                              @Suppress("UNUSED_PARAMETER")start:Int,
                              @Suppress("UNUSED_PARAMETER")end:Int,
                              @Suppress("UNUSED_PARAMETER")count:Int)
    {
        updatingDistances(s, _distanceToSea)
    }

    fun onDistanceToLBMChange(s: CharSequence,
                              @Suppress("UNUSED_PARAMETER")start:Int,
                              @Suppress("UNUSED_PARAMETER")end:Int,
                              @Suppress("UNUSED_PARAMETER")count:Int)
    {
        updatingDistances(s, _distanceToLBM)
    }

    fun onDistanceToRBMChange(s: CharSequence,
                              @Suppress("UNUSED_PARAMETER")start:Int,
                              @Suppress("UNUSED_PARAMETER")end:Int,
                              @Suppress("UNUSED_PARAMETER")count:Int)
    {
        updatingDistances(s, _distanceToRBM)
    }

    fun onDistanceTo3BMChange(s: CharSequence,
                              @Suppress("UNUSED_PARAMETER")start:Int,
                              @Suppress("UNUSED_PARAMETER")end:Int,
                              @Suppress("UNUSED_PARAMETER")count:Int)
    {
        updatingDistances(s, _distanceTo3BM)
    }

    private fun updatingDistances(s: CharSequence, distanceMut:MutableLiveData<Float>) {
        viewModelScope.launch {
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

    fun hasFilledInAllInformation(): Boolean{
        return photoID.value!! != "None" &&
                gpsValid() &&
                distanceToSea.value != null && distanceToSea.value != 0f &&
                distanceToLBM.value != null && distanceToLBM.value != 0f &&
                distanceToRBM.value != null && distanceToRBM.value != 0f &&
                distanceTo3BM.value != null && distanceTo3BM.value != 0f &&
                sectorName.value != "" &&
                tag.value != ""
    }

    private fun gpsValid():Boolean{
        return gpsLat.value!! >= -90 &&
                gpsLat.value!! <= 90 &&
                gpsLong.value!! >= -180 &&
                gpsLong.value!! <= 180
    }

    fun onSubmitButtonClick(@Suppress("UNUSED_PARAMETER")view:View){
        viewModelScope.launch {
            if(hasFilledInAllInformation()){

                //Get the nest one id
                val idNestOne = repo.getNestOneID()

                //Create Datamodel
                val dataModel = AdultNestScreenTwoDB(
                    0,
                    idNestOne,
                    hasLBM.value!!,
                    hasRBM.value!!,
                    has3BM.value!!,
                    distanceToSea.value!!,
                    distanceToLBM.value!!,
                    distanceToRBM.value!!,
                    distanceTo3BM.value!!,
                    gpsLat.value!!,
                    gpsLong.value!!,
                    sectorName.value!!,
                    tag.value!!,
                    comment.value!!
                )

                repo.uploadNestTwo(dataModel)

                _response.value = true

            }else{
                Log.i("test", "not all information has been filled in")
            }
        }
    }

    override fun setLocation(location: Location?) {
        viewModelScope.launch {
            if(location != null){
                _gpsLat.value = location.latitude
                _gpsLong.value = location.longitude
            }
        }
    }

    fun resetResponse(){
        viewModelScope.launch {
            _response.value = false
        }
    }

    fun removeLastAdultMain() {
        viewModelScope.launch {
            repo.removeLastAdultMain()
        }
    }

    fun removeNestOne(){
        viewModelScope.launch {
            val id = repo.getNestOneID()
            repo.removeNestOneByID(id)
        }
    }

}