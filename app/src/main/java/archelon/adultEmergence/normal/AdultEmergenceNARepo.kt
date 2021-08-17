package archelon.adultEmergence.normal

import archelon.dataModels.Room.adult.Normal.EmergenceNormalDB
import archelon.database.TurtleDatabaseDao

class AdultEmergenceNARepo(private val database: TurtleDatabaseDao) {

    suspend fun getEmergenceID(): Long {
        return database.getCurrentEmergence()!!
    }

    suspend fun uploadEmergenceNormal(dataModel: EmergenceNormalDB) {
        //This will be true if the use has done a back button to this fragment again
        if(database.containHowManyEmergenceNormaWithAdultID(dataModel.adult_ID) == 1L){
            val lastModel = database.getNormalByAdultID(dataModel.adult_ID)

            dataModel.normalID = lastModel.normalID

            database.updateEmergenceNormal(dataModel)
        }
        else{
            database.insertEmergenceNormal(dataModel)
        }


    }

    suspend fun removeLastAdultMain() {
        database.removeLastAdultMain()
    }

}