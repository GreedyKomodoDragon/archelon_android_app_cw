package archelon.observersWeather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import archelon.dataModels.Room.MorningSurveyDB
import archelon.dataModels.Room.ObserversMSPartialDB
import archelon.database.TurtleDatabase
import archelon.database.TurtleDatabaseDao
import archelon.hatching.hatchOne.HatchOneRepo
import archelon.hatching.hatchOne.HatchOneViewModel
import archelon.hatching.hatchOne.MockAPI
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ObserverRepoTest{

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var ob: ObserverRepo
    private lateinit var turtleDao: TurtleDatabaseDao
    private lateinit var db: TurtleDatabase

    @Before
    fun createObserRepo(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext


        db = Room.inMemoryDatabaseBuilder(context, TurtleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        turtleDao = db.turtleDatabaseDao

        ob = ObserverRepo(turtleDao)
    }


    @Test
    fun getLastMSTest(){
        runBlocking {
            //before anything has been added
            val value = ob.getLastMS()
            assertEquals(null, value)

            //after adding one
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

            val value2 = ob.getLastMS()
            assertEquals(1, value2)

            //after adding two
            val ms2 = MorningSurveyDB(0,
                "beachTest",
                "east",
                13,
                9,
                1998,
                2,
                32
            )
            turtleDao.insertMS(ms2)

            val value3 = ob.getLastMS()
            assertEquals(2, value3)

        }

    }

    @Test
    fun removeCurrentMSTest(){
        runBlocking {
            //after adding one
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

            val value = ob.getLastMS()
            assertEquals(1, value)

            ob.removeCurrentMS()

            val value2 = ob.getLastMS()
            assertEquals(null, value2)
        }
    }

    @Test
    fun uploadObserTest(){
        runBlocking {
            //after adding one
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
            val value = ob.getLastMS()

            //create data model
            val obserDB = ObserversMSPartialDB(
                0,
                value!!.toLong(),
                "Jake",
                "Liam",
                "Kale",
                "Diam",
                "Clear",
                "None",
                "Small",
                "North",
                "No",
            )

            ob.uploadObser(obserDB)

            val value2 = ob.getLastOB()
            assertEquals(1, value2)

        }
    }

}