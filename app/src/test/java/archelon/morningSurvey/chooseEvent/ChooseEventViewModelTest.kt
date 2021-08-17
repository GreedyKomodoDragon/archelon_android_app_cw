package archelon.morningSurvey.chooseEvent

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import archelon.dataModels.Room.MorningSurveyDB
import archelon.database.TurtleDatabase
import archelon.database.TurtleDatabaseDao
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChooseEventViewModelTest{

    private lateinit var vm: ChooseEventViewModel
    private lateinit var turtleDao: TurtleDatabaseDao
    private lateinit var db: TurtleDatabase

    @Before
    fun createViewModel(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext


        db = Room.inMemoryDatabaseBuilder(context, TurtleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        turtleDao = db.turtleDatabaseDao

        vm = ChooseEventViewModel(
            ApplicationProvider.getApplicationContext(),
            ChooseEventRepo(turtleDao)
        )
    }


    @Test
    fun removeCurrentMS(){
        runBlocking {
            val ms = MorningSurveyDB(0,
                "beachTest",
                "east",
                13,
                9,
                1998,
                2,
                32
            )

            turtleDao.insertMS(ms)

            var lastMS = turtleDao.getCurrentMS()
            assertEquals(1, lastMS)

            vm.removeCurrentMS()

            lastMS = turtleDao.getCurrentMS()
            assertEquals(null, lastMS)

        }
    }
}