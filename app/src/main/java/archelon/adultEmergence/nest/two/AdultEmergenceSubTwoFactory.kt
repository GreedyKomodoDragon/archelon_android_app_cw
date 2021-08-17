package archelon.adultEmergence.nest.two

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase

class AdultEmergenceSubTwoFactory (private val app: Application)  : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdultEmergenceSubTwoViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(app).turtleDatabaseDao

            return AdultEmergenceSubTwoViewModel(app, AdultEmergenceSubTwoRepo(dataSource)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}