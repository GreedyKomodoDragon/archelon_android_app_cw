package archelon.morningSurvey.subMenu

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import archelon.dataModels.REST.MorningSurveyProperty
import archelon.dataModels.REST.NestDataEntryProperty
import archelon.dataModels.Room.adult.AdultEmergenceMain
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MorningSurveySubMenuViewModel(application: Application, private val repo: MorningSurveySubMenuRepo) : AndroidViewModel(application)  {

    fun removeCurrentMS() {
        viewModelScope.launch {
            repo.removeCurrentMorningSurvey()
        }
    }

    private val _response = MutableLiveData("started")
    val response: LiveData<String> = _response


    fun uploadAllEntriesToRest(@Suppress("UNUSED_PARAMETER")view: View){
        viewModelScope.launch {

            //Get the current morning survey
            val morningSurveyData = repo.getCurrentMS()

            //Get all of the adult main made
            val adultMainList = repo.getAllAdult(morningSurveyData.surveyID)

            var result = false

            //check that there is any surveys made
            if(adultMainList.isNotEmpty()){

                //for each adult we need to check if it was a nest or normal entry
                for(adult in adultMainList){

                    if(isNest(adult)){
                        val nestEntryOne = repo.getNestOneByAdultID(adult.adultID)
                        Log.i("test", "$nestEntryOne")
                        val nestEntryTwo = repo.getNestTwoByNestOneID(nestEntryOne.nestID)
                        Log.i("test", "$nestEntryTwo")


                        val morningSurvey = MorningSurveyProperty(
                            "LAK",
                            "${morningSurveyData.year}-${morningSurveyData.month}-${morningSurveyData.day}",
                            morningSurveyData.beach,
                            morningSurveyData.sector,
                            "fdd",
                            adult.path,
                            1,
                            nestEntryTwo.distanceToSea,
                            nestEntryOne.trackType.toString(),
                            0,
                            nestEntryTwo.gpsLat,
                            nestEntryTwo.gpsLong,
                            nestEntryTwo.tag,
                            nestEntryTwo.comment,
                            adult.photoID
                        )

                        val nestData = NestDataEntryProperty(
                            1,
                            "${morningSurveyData.year}-${morningSurveyData.month}-${morningSurveyData.day}",
                            "${morningSurveyData.year}-${morningSurveyData.month}-${morningSurveyData.day}T12:49:44.164000Z",
                            "${morningSurveyData.year}-${morningSurveyData.month}-${morningSurveyData.day}T12:49:44.164000Z",
                            "BoB",
                            "I",
                            nestEntryOne.distanceToTopEgg,
                            40,
                            nestEntryTwo.distanceToSea.toDouble(),
                            20,
                            nestEntryTwo.gpsLat,
                            nestEntryTwo.gpsLong
                        )

                        result = repo.uploadMorningSurvey(morningSurvey)

                        //end upload process
                        if(!result){
                            break
                        }

                        result = repo.uploadNest(nestData)

                        //end upload process
                        if(!result){
                            break
                        }



                    }
                    else{

                        val normal = repo.getNormalByAdultID(adult.adultID)

                        val morningSurvey = MorningSurveyProperty(
                            "LAK",
                            "${morningSurveyData.year}-${morningSurveyData.month+1}-${morningSurveyData.day}",
                            morningSurveyData.beach,
                            morningSurveyData.sector,
                            "fdd",
                            adult.path,
                            1,
                            normal.distanceToSea,
                            normal.trackType.toString(),
                            0,
                            normal.gpsLat,
                            normal.gpsLong,
                            normal.tag,
                            normal.comments,
                            adult.photoID
                        )

                        result = repo.uploadMorningSurvey(morningSurvey)

                        //end upload process
                        if(!result){
                            break
                        }

                    }

                }
            }



            //Only delete if all have been successfully uploaded
            if(result){
                repo.clearDB()
            }

            //Update UI
            updateResponse(result)

        }
    }

    private fun updateResponse(result: Boolean) {
        if (result) {
            _response.value = "passed"
        } else {
            _response.value = "failed"
        }
    }

    fun resetResponse(){
        viewModelScope.launch {
            _response.value = "started"
        }
    }


    private fun isNest(adMain: AdultEmergenceMain):Boolean{
        return when(adMain.path){
            "NA" -> false
            "BP-AEC-SWIM->BP" -> false
            "BP-NEST" -> true
            "BP-SWIM->BP" -> true
            else -> throw IllegalArgumentException()
        }
    }

}