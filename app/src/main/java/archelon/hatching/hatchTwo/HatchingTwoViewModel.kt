package archelon.hatching.hatchTwo

import android.app.Application
import android.location.Location
import android.service.autofill.Validators.not
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import archelon.GPS.ViewModelGPSListener
import archelon.dataModels.Room.hatching.HatchingTwoDB
import archelon.dataModels.Room.hatching.PairsHatch
import kotlinx.coroutines.launch

class HatchingTwoViewModel(application: Application, val repo: HatchingTwoRepo) : AndroidViewModel(application), ViewModelGPSListener  {

    private var _numberOfTracks = MutableLiveData<Int?>(null)
    var numberOfTracks: LiveData<Int?> = _numberOfTracks

    fun onTrackNumberChange(s:CharSequence,
                            @Suppress("UNUSED_PARAMETER") start:Int,
                            @Suppress("UNUSED_PARAMETER") end:Int,
                            @Suppress("UNUSED_PARAMETER") count:Int)
    {
        viewModelScope.launch {
            //convert to int if possible
            val number = s.toString().toIntOrNull()

            //Check is value number
            if (number != null && number >= 0) {
                _numberOfTracks.value =number
            }else{
                _numberOfTracks.value = null
            }
        }
    }


    private var _hasMass = MutableLiveData(false)
    var hasMass : LiveData<Boolean> = _hasMass

    fun onClickMassCheckBox(view: View){
        viewModelScope.launch {
            val bool = _hasMass.value

            //if it was false before, now to be true
            //we need to remove the track numbers
            if(!bool!!){
                _numberOfTracks.value = null
            }

            _hasMass.value = !bool
        }
    }

    private var _hatchingFate = MutableLiveData(0)
    var hatchingFate : LiveData<Int> = _hatchingFate

    fun updateHatchingFate(pos: Int){
        viewModelScope.launch {
            _hatchingFate.value = pos
        }
    }

    private var _otherPred = MutableLiveData("")
    var otherPred : LiveData<String> = _otherPred

    fun onPredChange(s:CharSequence, start:Int, end:Int, count:Int){
        viewModelScope.launch {
            _otherPred.value = s.toString()
        }
    }

    private var _trackTrajectory = MutableLiveData(0)
    var trackTrajectory : LiveData<Int> = _trackTrajectory

    fun updateTrackTrajectory(pos: Int){
        viewModelScope.launch {
            _trackTrajectory.value = pos
        }
    }


    //Photo Record
    private var _photoID = MutableLiveData("None")
    var photoID: LiveData<String> = _photoID

    fun addImage(id:String){
        viewModelScope.launch {
            _photoID.value = id
        }
    }

    //Response
    //movement controls
    private var _response = MutableLiveData(false)
    var response : LiveData<Boolean> = _response

    //allows user go back using back button
    fun resetResponse(){
        viewModelScope.launch {
            _response.value = false
        }
    }


    //GPS location -range from -90 to 90 for latitude and -180 to 180 for longitude
    private var _gpsLong = MutableLiveData(181.0)
    var gpsLong : LiveData<Double> = _gpsLong

    private var _gpsLat = MutableLiveData(-91.0)
    var gpsLat : LiveData<Double> = _gpsLat

    //Sector
    private var _sector = MutableLiveData("")
    var sector: LiveData<String> = _sector

    fun onSectorChange(s:CharSequence,
                       @Suppress("UNUSED_PARAMETER")start:Int,
                       @Suppress("UNUSED_PARAMETER")end:Int,
                       @Suppress("UNUSED_PARAMETER") count:Int)
    {
        viewModelScope.launch {
            _sector.value = s.toString()
        }
    }

    //comment
    private var _comment = MutableLiveData("")
    var comment: LiveData<String> = _comment


    fun onCommentChange(s:CharSequence, start:Int, end:Int, count:Int){
        viewModelScope.launch {
            _comment.value = s.toString()
        }
    }


    //Text box: Pair<left of arrow, right of arrow>00

    private var _boxEntries = MutableLiveData<ArrayList<Pair<String,String>>>(ArrayList())
    var area = MutableLiveData("")

    fun onPlusButtonClick(@Suppress("UNUSED_PARAMETER")view: View){
        viewModelScope.launch {
            //Determine which mass or track number
            if(_hasMass.value!! &&
                _hatchingFate.value != null && _hatchingFate.value != 0 &&
                _trackTrajectory.value != null && _trackTrajectory.value != 0   ){

                val pair = Pair("Mass" + getrackTrajectory(_trackTrajectory.value!!),
                    getFateFromInt(_hatchingFate.value!!))

                _boxEntries.value!!.add(pair)

                area.value = convertPairToString(_boxEntries.value!!)

                clearAllFields()

            }else if(_hatchingFate.value != null && _hatchingFate.value != 0 &&
                _trackTrajectory.value != null && _trackTrajectory.value != 0&&
                _numberOfTracks.value != null && _numberOfTracks.value != 0){

                val pair = Pair(_numberOfTracks.value.toString() + " d" + getrackTrajectory(_trackTrajectory.value!!),
                    getFateFromInt(_hatchingFate.value!!))

                _boxEntries.value!!.add(pair)

                area.value = convertPairToString(_boxEntries.value!!)

                clearAllFields()

            }


        }
    }

    //When plus is clicked it needs to wipe the fields
    private fun clearAllFields(){
        _numberOfTracks.value = null
        _hasMass.value = false
        _otherPred.value = ""
    }

    private fun convertPairToString(pairs: ArrayList<Pair<String, String>>):String{
        var string  = ""

        for (pair in pairs){
            string = string + pair.first + "-->" + pair.second + "\n"
        }

        return string
    }

    private fun getrackTrajectory(pos: Int):String{

        return when(pos){
            1 -> ""
            2 -> " (E)"
            3 -> " (W)"
            4 -> " (N)"
            5 -> " (S)"
            6 -> " (Irr)"
            else -> "None"
        }

    }

    fun submitHatchTwoToDB(@Suppress("UNUSED_PARAMETER")view: View){
        viewModelScope.launch {
            //check has a pair
            //check gps is within valid range
            if(_boxEntries.value!!.size > 0 &&
                gpsLat.value!! >= -90 &&
                gpsLat.value!! <= 90 &&
                gpsLong.value!! >= -180 &&
                gpsLong.value!! <= 180 &&
                sector.value!!.isNotEmpty() &&
                        photoID.value!! != "None"
            ){


                //get last UIs database ID
                val lastHatchOneID = repo.getLastHatchOneID()

                val dataEntry = HatchingTwoDB(
                    0,
                    lastHatchOneID,
                    gpsLat.value!!,
                    gpsLong.value!!,
                    sector.value!!,
                    comment.value!!,
                    photoID.value!!
                )

                Log.i("test", "$dataEntry")

                //upload the data model
                val lastHatchTwoID = repo.uploadHatchTwo(dataEntry)

                for (pair in _boxEntries.value!!){
                    val dataPair = PairsHatch(
                        0,
                        lastHatchTwoID,
                        pair.first,
                        pair.second
                    )

                    repo.uploadHatchTwoPair(dataPair)
                }

                _response.value = true

                Log.i("test", "successful upload")

            }else{
                Log.i("test", "not valid inputs")
            }
        }
    }

    private fun getFateFromInt(pos: Int):String {
        return if(otherPred.value!!.isNotEmpty()){
            otherPred.value!!
        }else{
            when(pos){
                1 -> "Sea"
                2 -> "Seagull"
                3 -> "Cat"
                4 -> "Dog"
                5 -> "Fox"
                6 -> "Lost"
                7 -> "Dead"
                8 -> "Other"
                else -> "None"
            }
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

    fun removeHatchOne() {
        viewModelScope.launch {
            repo.removeHatchOne()
        }
    }

}