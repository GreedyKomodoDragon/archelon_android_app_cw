package archelon.morningSurvey.subMenu

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase
import archelon.restAPI.ArchelonApi

class MorningSurveySubMenuViewModelFactory(private val app: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MorningSurveySubMenuViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(app).turtleDatabaseDao

            return MorningSurveySubMenuViewModel(app, MorningSurveySubMenuRepo(dataSource,
                ArchelonApi.retrofitService, app) ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}