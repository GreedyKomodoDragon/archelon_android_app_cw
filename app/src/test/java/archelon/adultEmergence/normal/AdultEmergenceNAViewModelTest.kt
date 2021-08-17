package archelon.adultEmergence.normal

import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import archelon.database.TurtleDatabase
import archelon.database.TurtleDatabaseDao
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AdultEmergenceNAViewModelTest{
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var vm: AdultEmergenceNAViewModel
    private lateinit var turtleDao: TurtleDatabaseDao
    private lateinit var db: TurtleDatabase

    @Before
    fun setupViewModel() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext


        db = Room.inMemoryDatabaseBuilder(context, TurtleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        turtleDao = db.turtleDatabaseDao

        vm = AdultEmergenceNAViewModel(
            ApplicationProvider.getApplicationContext(), AdultEmergenceNARepo(
                turtleDao
            )
        )
    }


    @Test
    fun hasAllInformationTest(){
        //on start should be false
        Assert.assertEquals(false, vm.hasFilledInAllLiveData())

        //add GPS
        val location = Location("")
        location.latitude = 0.0
        location.longitude = 0.0

        vm.setLocation(location)

        Assert.assertEquals(false, vm.hasFilledInAllLiveData())

        //add photo
        vm.addImage("photoID")

        Assert.assertEquals(false, vm.hasFilledInAllLiveData())

        //add sector and tag
        vm.updateSector("sector",0,0,0)
        vm.updateTag("tag",0,0,0)

    }
}