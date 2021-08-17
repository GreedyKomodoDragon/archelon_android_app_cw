package archelon.hatching.hatchTwo

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import archelon.database.TurtleDatabase

class HatchingTwoViewFactory (private val app: Application) : ViewModelProvider.Factory  {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HatchingTwoViewModel::class.java)) {
            //Get database
            val dataSource = TurtleDatabase.getInstance(app).turtleDatabaseDao

            return HatchingTwoViewModel(app, HatchingTwoRepo(dataSource)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}