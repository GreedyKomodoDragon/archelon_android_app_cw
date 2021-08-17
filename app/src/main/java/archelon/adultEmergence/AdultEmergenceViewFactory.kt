package archelon.adultEmergence

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase

class AdultEmergenceViewFactory(private val app: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdultEmergenceViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(app).turtleDatabaseDao

            return AdultEmergenceViewModel(app, AdultEmergenceRepo(dataSource)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}