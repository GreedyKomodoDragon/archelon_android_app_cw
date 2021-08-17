package archelon.morningSurvey.chooseEvent

import archelon.database.TurtleDatabaseDao

class ChooseEventRepo(private val database: TurtleDatabaseDao) {

    suspend fun removeMS() {
        database.removeCurrentMorningSurvey()
    }

}