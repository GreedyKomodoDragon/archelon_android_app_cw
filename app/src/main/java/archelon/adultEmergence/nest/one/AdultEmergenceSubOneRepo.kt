package archelon.adultEmergence.nest.one

import archelon.dataModels.Room.adult.Nest.AdultNestScreenOneDB
import archelon.database.TurtleDatabaseDao

class AdultEmergenceSubOneRepo(private val database: TurtleDatabaseDao) {

    suspend fun getCurrentAdultMain():Long{
        return database.getCurrentEmergence()!!
    }

    suspend fun uploadNestOne(dataModel: AdultNestScreenOneDB) {
        database.insertNestOne(dataModel)
    }

    suspend fun removeLastAdultMain() {
        database.removeLastAdultMain()
    }

}