package archelon.morningSurvey

import android.app.Application
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.TimePicker
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import archelon.dataModels.Room.MorningSurveyDB
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MorningViewModel(application: Application, private val repo: MorningSurveyStartRepo) : AndroidViewModel(application) {

    //Time properties
    var year = MutableLiveData(Calendar.getInstance()[Calendar.YEAR])
    var month = MutableLiveData(Calendar.getInstance()[Calendar.MONTH] + 1)
    var date = MutableLiveData(Calendar.getInstance()[Calendar.DATE])
    var hour = MutableLiveData(Calendar.getInstance()[Calendar.HOUR])
    var min = MutableLiveData(Calendar.getInstance()[Calendar.MINUTE])

    //Location properties
    var area = MutableLiveData(0)
    var sector = MutableLiveData(0)

    //Control nav
    private var _response = MutableLiveData(false)
    var response: LiveData<Boolean> = _response

    //Error message
    var error = MutableLiveData(false)

    fun isValidDate(date:String): Boolean{
        return try {
            val df = SimpleDateFormat("dd-MM-yyyy");
            df.isLenient = false
            df.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun updateDate(_year: Int, _month: Int, _dayOfMonth: Int){
        viewModelScope.launch {
            if(!isValidDate("${_dayOfMonth}-${_month}-${_year}")){
                year.value = Calendar.getInstance()[Calendar.YEAR]
                month.value = Calendar.getInstance()[Calendar.MONTH] + 1
                date.value = Calendar.getInstance()[Calendar.DATE]
            }else{
                year.value = _year
                month.value = _month + 1
                date.value = _dayOfMonth
            }
        }
    }

    fun updateBeach(@Suppress("UNUSED_PARAMETER")parent: AdapterView<*>?,
                    @Suppress("UNUSED_PARAMETER")view: View?,
                    position: Int,
                    @Suppress("UNUSED_PARAMETER")id: Long){
        viewModelScope.launch {
            area.value = position
        }
    }

    fun updateSector(@Suppress("UNUSED_PARAMETER")parent: AdapterView<*>?,
                     @Suppress("UNUSED_PARAMETER")view: View?,
                     position: Int,
                     @Suppress("UNUSED_PARAMETER")id: Long){
        viewModelScope.launch {
            sector.value = position
        }
    }

    fun updateTime(@Suppress("UNUSED_PARAMETER")view: TimePicker?, _hour:Int, _min:Int){
        viewModelScope.launch {
            if(_hour in 0..23 && _min in 0..59){
                hour.value = _hour
                min.value = _min
            }
        }
    }

    fun moveToNextPage(@Suppress("UNUSED_PARAMETER")view: View) {
        //Check that all inputs are in and valid
        viewModelScope.launch {
            //Reset Error Message
            error.value = false

            if(isValidDate("${date.value}-${month.value}-${year.value}") &&
                    sector.value != 0 &&
                    area.value != 0 &&
                    hasValidTime()){

                //create MS DB entity
                val ms = MorningSurveyDB(0,
                    getPositionToStringBeach(area.value!!),
                    getPositiontoStringSector(sector.value!!),
                    date.value!!,
                    month.value!!,
                    year.value!!,
                    hour.value!!,
                    min.value!!
                )

                //upload the data entity to the database
                repo.uploadMorningSurvery(ms)

                //allow movement to the next fragment
                _response.value = true

            }else{
                //Show message on RES
                error.value = true
            }
        }

    }

    private fun hasValidTime(): Boolean {
        return hour.value!! in 0..23 && min.value!! in 0..59
    }

    private fun getPositionToStringBeach(value:Int):String{
        return when(value){
            1 -> "Mavrovouni"
            2 -> "Selinitsa"
            3 -> "Vathi"
            4 -> "Valtaki"
            else -> throw java.lang.Exception()
        }
    }

    fun resetResponse(){
        viewModelScope.launch {
            _response.value = false
        }
    }

    private fun getPositiontoStringSector(value:Int):String{
        return when(value){
            1 -> "East"
            2 -> "West"
            else -> throw java.lang.Exception()
        }
    }

}
