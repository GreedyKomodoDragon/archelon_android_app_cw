package archelon.morningSurvey

import archelon.dataModels.Room.MorningSurveyDB
import archelon.database.TurtleDatabaseDao

class MorningSurveyStartRepo(private val database: TurtleDatabaseDao) {


    suspend fun uploadMorningSurvery(ms: MorningSurveyDB){
        database.insertMS(ms)
    }


}