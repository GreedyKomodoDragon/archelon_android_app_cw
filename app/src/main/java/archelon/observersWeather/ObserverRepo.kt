package archelon.observersWeather

import archelon.dataModels.Room.ObserversMSPartialDB
import archelon.database.TurtleDatabaseDao

class ObserverRepo(private val database: TurtleDatabaseDao) {

    suspend fun getLastMS(): Int? {
        return database.getCurrentMS()
    }

    suspend fun uploadObser(ob:ObserversMSPartialDB){
        database.insertObservers(ob)
    }

    suspend fun getLastOB():Int?{
        return database.getLastOB()
    }

    suspend fun removeCurrentMS() {
        database.removeCurrentMorningSurvey()
    }

}