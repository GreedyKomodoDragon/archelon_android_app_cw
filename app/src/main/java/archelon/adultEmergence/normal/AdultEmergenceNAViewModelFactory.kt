package archelon.adultEmergence.normal

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase

class AdultEmergenceNAViewModelFactory(val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdultEmergenceNAViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(application).turtleDatabaseDao

            return AdultEmergenceNAViewModel(application, AdultEmergenceNARepo(dataSource)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}