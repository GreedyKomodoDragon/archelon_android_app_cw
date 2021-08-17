package archelon.adultEmergence.nest.one

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase

class AdultEmergenceSubOneFactory(private val app: Application)  : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdultEmergenceSubOneViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(app).turtleDatabaseDao

            return AdultEmergenceSubOneViewModel(app, AdultEmergenceSubOneRepo(dataSource)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}