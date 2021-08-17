package archelon.adultEmergence.nest.relocation.start

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase

class RelocationStartViewModelFactory(private val app: Application)  : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RelocationStartViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(app).turtleDatabaseDao

            return RelocationStartViewModel(app, RelocationStartRepo(dataSource)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}