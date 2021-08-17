package archelon.hatching.hatchOne

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase
import archelon.restAPI.ArchelonApi.retrofitService

class HatchOneViewFactory (private val app: Application) : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HatchOneViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(app).turtleDatabaseDao

            return HatchOneViewModel(app, HatchOneRepo(dataSource, retrofitService, app)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}