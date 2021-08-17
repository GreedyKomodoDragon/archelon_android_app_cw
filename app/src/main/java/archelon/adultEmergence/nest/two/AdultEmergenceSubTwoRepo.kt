package archelon.adultEmergence.nest.two

import archelon.dataModels.Room.adult.Nest.AdultNestScreenTwoDB
import archelon.database.TurtleDatabaseDao

class AdultEmergenceSubTwoRepo(private val database: TurtleDatabaseDao) {
    suspend fun getNestOneID(): Long {
        return database.getNestOneID()!!
    }

    suspend fun uploadNestTwo(dataModel: AdultNestScreenTwoDB) {
        database.insertNestTwo(dataModel)
    }

    suspend fun removeLastAdultMain() {
        database.removeLastAdultMain()
    }

    suspend fun removeNestOneByID(id:Long){
        database.removeNestOne(id)
    }

}