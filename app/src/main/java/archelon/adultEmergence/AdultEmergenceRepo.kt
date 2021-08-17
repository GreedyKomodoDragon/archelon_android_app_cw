package archelon.adultEmergence

import archelon.dataModels.Room.adult.AdultEmergenceMain
import archelon.database.TurtleDatabaseDao

class AdultEmergenceRepo(private val database: TurtleDatabaseDao) {

    suspend fun uploadAdultMain(adultMainData: AdultEmergenceMain){
        database.insertAdultEmergence(adultMainData)
    }

    suspend fun getLastMSID(): Long{
        return database.getCurrentMS()!!.toLong()
    }

    suspend fun getNumberOfEntries(msID:Long):Int{
        return database.getNumberOfEntriesOfAdult(msID)
    }

}