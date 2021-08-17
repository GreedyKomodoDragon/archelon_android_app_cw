package archelon.hatching.hatchOne

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import archelon.dataModels.Room.hatching.HatchingOneDB
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HatchOneViewModel(application: Application, val repo: HatchOneRepo) : AndroidViewModel(application) {

    //movement controls
    private var _response = MutableLiveData(false)
    var response : LiveData<Boolean> = _response

    //allows user go back using back button
    fun resetResponse(){
        viewModelScope.launch {
            _response.value = false
        }
    }

    //Fields
    private var _isFirstHatch = MutableLiveData(false)
    var isFirstHatch : LiveData<Boolean> = _isFirstHatch

    var timestamp = MutableLiveData(getTimeStamp())

    private fun getTimeStamp(): Long{
        return System.currentTimeMillis() / 1000L
    }

    private var _hatchCode = MutableLiveData("")
    var hatchCode : LiveData<String> = _hatchCode

    private var _hasStonesNestToNest = MutableLiveData(false)
    var hasStonesNestToNest : LiveData<Boolean> = _hasStonesNestToNest

    private var _hasLBM = MutableLiveData(true)
    var hasLBM : LiveData<Boolean> = _hasLBM

    private var _hasRBM = MutableLiveData(false)
    var hasRBM : LiveData<Boolean> = _hasRBM

    private var _has3BM = MutableLiveData(false)
    var has3BM : LiveData<Boolean> = _has3BM


    //distance measures
    private var _distanceToSea = MutableLiveData(0f)
    var distanceToSea : LiveData<Float> = _distanceToSea
    private var _distanceToLBM = MutableLiveData(0f)
    var distanceToLBM : LiveData<Float> = _distanceToLBM
    private var _distanceToRBM = MutableLiveData(0f)
    var distanceToRBM : LiveData<Float> = _distanceToRBM
    private var _distanceTo3BM = MutableLiveData(0f)
    var distanceTo3BM : LiveData<Float> = _distanceTo3BM

    //Nest diagram
    private var _photoID = MutableLiveData("None")
    var photoID: LiveData<String> = _photoID


    //Spinner
    var entriesNestCodes = MutableLiveData<List<String>>(ArrayList(0))
    private var selectedIndex = MutableLiveData(0)

    fun getNestCode() {
        viewModelScope.launch {
            entriesNestCodes.value = repo.getNestCodeName()
        }
    }

    fun updateNestCode(pos:Int){
        viewModelScope.launch {
            selectedIndex.value = pos
        }
    }

    fun updateFirstHatch(isFirstHatch: Boolean){
        viewModelScope.launch {
            _isFirstHatch.value = isFirstHatch
        }
    }

    fun onHatchCodeChange(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
        viewModelScope.launch {
            if(s.toString().isNotEmpty()){
                _hatchCode.value = s.toString()
            }else{
                _hatchCode.value = ""
            }
        }
    }

    fun updateNestToStone(hasStoneNestToNest: Boolean){
        viewModelScope.launch {
            _hasStonesNestToNest.value = hasStoneNestToNest
        }
    }

    fun onDistanceToSeaChange(s: CharSequence, start: Int, before: Int, count: Int) {
        updatingDistances(s, _distanceToSea)
    }

    fun onDistanceToLBMChange(s: CharSequence, start: Int, before: Int, count: Int) {
        updatingDistances(s, _distanceToLBM)
    }

    fun onDistanceToRBMChange(s: CharSequence, start: Int, before: Int, count: Int) {
        updatingDistances(s, _distanceToRBM)
    }

    fun onDistanceTo3BMChange(s: CharSequence, start: Int, before: Int, count: Int) {
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


    //We only need to two of the 3 as this can save space
    //Similar to how dumpy_variables - 1 in Data Science reduce dimensions
    fun updateBM(hasLBM : Boolean, hasRBM : Boolean){
        viewModelScope.launch {
            //For function completeness
            if(hasLBM && hasRBM){
                _hasLBM.value = true
                _hasRBM.value = false
                _has3BM.value = false
            }else{
                _hasLBM.value = hasLBM
                _hasRBM.value = hasRBM
                _has3BM.value = !hasLBM && !hasRBM
            }

        }
    }

    fun addImage(id:String){
        viewModelScope.launch {
            _photoID.value = id
        }
    }

    fun submitToDBThenNextUI(@Suppress("UNUSED_PARAMETER") view: View){
        viewModelScope.launch {

            //check inputs
            if(allLiveDataObtained() && !entriesNestCodes.value.isNullOrEmpty()){

                val MSID = repo.getLastMS()

                //create data model
                val hatchOne = HatchingOneDB(
                    0,
                    MSID!!.toLong(),
                    isFirstHatch.value!!,
                    timestamp.value!!,
                    hatchCode.value!!,
                    hasStonesNestToNest.value!!,
                    entriesNestCodes.value!![selectedIndex.value!!],
                    hasLBM.value!!,
                    hasRBM.value!!,
                    has3BM.value!!,
                    distanceToSea.value!!,
                    distanceToLBM.value!!,
                    distanceToRBM.value!!,
                    distanceTo3BM.value!!,
                    photoID.value!!
                )

                repo.uploadHatchOne(hatchOne)

                //move to the next page
                _response.value = true


            }else{
                //TODO: add some error message
                Log.i("test", "invalid inputs")
            }

        }
    }

    fun allLiveDataObtained(): Boolean {
        return selectedIndex.value != 0 &&
                !hatchCode.value.isNullOrEmpty() &&
                distanceTo3BM.value != 0f &&
                distanceToLBM.value != 0f &&
                distanceToRBM.value != 0f &&
                distanceToSea.value != 0f &&
                photoID.value != "None"
    }


}