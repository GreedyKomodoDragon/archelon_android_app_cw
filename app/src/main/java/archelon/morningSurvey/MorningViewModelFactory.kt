package archelon.morningSurvey

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase
import archelon.database.TurtleDatabaseDao

class MorningViewModelFactory(private val app: Application): ViewModelProvider.Factory {


    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MorningViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(app).turtleDatabaseDao

            return MorningViewModel(app, MorningSurveyStartRepo(dataSource) ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}