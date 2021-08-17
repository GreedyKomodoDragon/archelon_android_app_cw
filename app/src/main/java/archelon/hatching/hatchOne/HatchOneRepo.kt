package archelon.hatching.hatchOne

import android.app.Application
import android.content.Context
import android.util.Log
import archelon.dataModels.Room.hatching.HatchingOneDB
import archelon.database.TurtleDatabaseDao
import archelon.restAPI.ArchelonAPIService

class HatchOneRepo(private val database: TurtleDatabaseDao,
                   private val restAPIService: ArchelonAPIService,
                   private val application: Application) {

    suspend fun getNestCodeName(): List<String>{

        //How to access api key
        val sharedPref = application.getSharedPreferences("archelon", Context.MODE_PRIVATE)
        val key = sharedPref.getString("key", "a")

        if (key != "a"){
            return try{
                val nests = restAPIService.getAllNests("Token " + key!!)

                //get the codes
                val nestCodes = ArrayList<String>()
                nestCodes.add("Choose the Nest Code")
                for (nest in nests){
                    nestCodes.add(nest.nestCode)
                }

                nestCodes

            }catch (e:Exception){
                ArrayList()
            }
        }else{
            return ArrayList()
        }

    }

    suspend fun getLastMS(): Int? {
        return database.getCurrentMS()
    }

    suspend fun uploadHatchOne(hatchOne: HatchingOneDB){
        database.insertHatchOne(hatchOne)
    }

}