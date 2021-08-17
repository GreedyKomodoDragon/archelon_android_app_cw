package archelon.hatching.hatchTwo

import archelon.dataModels.Room.hatching.HatchingTwoDB
import archelon.dataModels.Room.hatching.PairsHatch
import archelon.database.TurtleDatabaseDao

class HatchingTwoRepo(private val database: TurtleDatabaseDao) {
    suspend fun getLastHatchOneID(): Long {
        return database.getLastHatchOne()!!
    }

    suspend fun uploadHatchTwo(dataEntry: HatchingTwoDB) : Long {
        database.insertHatchTwo(dataEntry)
        return database.getLastHatchTwoID()!!
    }

    suspend fun uploadHatchTwoPair(hPair: PairsHatch){
        database.insertHatchPair(hPair)
    }

    suspend fun removeHatchOne() {
        database.removeHatchOne()
    }
}