package archelon.morningSurvey.chooseEvent

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase

class ChooseEventViewModelFactory(private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChooseEventViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(app).turtleDatabaseDao

            return ChooseEventViewModel(app, ChooseEventRepo(dataSource) ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}