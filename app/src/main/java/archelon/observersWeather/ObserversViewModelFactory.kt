package archelon.observersWeather

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase

class ObserversViewModelFactory(val application: Application): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ObserverViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(application).turtleDatabaseDao

            return ObserverViewModel(application, ObserverRepo(dataSource) ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}