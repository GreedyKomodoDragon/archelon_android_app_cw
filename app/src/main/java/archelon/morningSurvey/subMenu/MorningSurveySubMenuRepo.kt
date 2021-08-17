package archelon.morningSurvey.subMenu

import android.app.Application
import android.content.Context
import android.util.Log
import archelon.dataModels.REST.MorningSurveyProperty
import archelon.dataModels.REST.NestDataEntryProperty
import archelon.dataModels.Room.MorningSurveyDB
import archelon.dataModels.Room.adult.AdultEmergenceMain
import archelon.dataModels.Room.adult.Nest.AdultNestScreenOneDB
import archelon.dataModels.Room.adult.Nest.AdultNestScreenTwoDB
import archelon.dataModels.Room.adult.Normal.EmergenceNormalDB
import archelon.database.TurtleDatabaseDao
import archelon.restAPI.ArchelonAPIService
import retrofit2.HttpException

class MorningSurveySubMenuRepo(private val database: TurtleDatabaseDao,
                               private val restAPIService: ArchelonAPIService,
                               private val application: Application) {

    suspend fun removeCurrentMorningSurvey() {
        database.removeCurrentMorningSurvey()
    }

    suspend fun getCurrentMS(): MorningSurveyDB {
        return database.getCurrentMSData()
    }

    suspend fun getAllAdult(surveyID: Long): List<AdultEmergenceMain> {
        return database.getAllAdultEmergence(surveyID)
    }

    suspend fun getNestOneByAdultID(adultID: Long): AdultNestScreenOneDB {
        return database.getNestOneByForeignID(adultID)
    }

    suspend fun getNestTwoByNestOneID(nestID: Long): AdultNestScreenTwoDB {
        return database.getNestTwoByNestOne(nestID)
    }

    suspend fun getNormalByAdultID(adultID: Long): EmergenceNormalDB {
        return database.getNormalByAdultID(adultID)
    }

    suspend fun uploadMorningSurvey(morningSurvey: MorningSurveyProperty) : Boolean {
        //How to access api key
        val sharedPref = application.getSharedPreferences("archelon", Context.MODE_PRIVATE)
        val key = sharedPref.getString("key", "a")

        return if (key != "a"){
            try{
                restAPIService.addNewMorningSurvey(morningSurvey, "Token " + key!!)
                true
            }catch (e: HttpException){
                false
            }
        }else{
            false
        }
    }

    suspend fun clearDB() {
        database.clearMS()
    }

    suspend fun uploadNest(nestData: NestDataEntryProperty): Boolean {
        //How to access api key
        val sharedPref = application.getSharedPreferences("archelon", Context.MODE_PRIVATE)
        val key = sharedPref.getString("key", "a")

        return if (key != "a"){
            try{
                restAPIService.addNewNestData(nestData, "Token " + key!!)
                true
            }catch (e: HttpException){
                false
            }
        }else{
            false
        }
    }


}