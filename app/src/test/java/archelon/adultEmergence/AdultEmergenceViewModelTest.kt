package archelon.adultEmergence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import archelon.database.TurtleDatabase
import archelon.database.TurtleDatabaseDao
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AdultEmergenceViewModelTest{

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var vm: AdultEmergenceViewModel
    private lateinit var turtleDao: TurtleDatabaseDao
    private lateinit var db: TurtleDatabase

    @Before
    fun setupViewModel() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext


        db = Room.inMemoryDatabaseBuilder(context, TurtleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        turtleDao = db.turtleDatabaseDao

        vm = AdultEmergenceViewModel(ApplicationProvider.getApplicationContext(), AdultEmergenceRepo(turtleDao))
    }

    @Test
    fun normalPathWayTest(){
        //path way one
        val path = ArrayList<String>()
        path.add("NA")

        vm.Operations.value = path

        assertEquals(true, vm.isNormalPathWay())

        //path way two
        val pathTwo = ArrayList<String>()
        pathTwo.add("BP")
        pathTwo.add("AEC")
        pathTwo.add("SWIM->BP")

        vm.Operations.value = pathTwo

        assertEquals(true, vm.isNormalPathWay())

        //path way two
        val pathThree = ArrayList<String>()
        pathThree.add("BP")
        pathThree.add("AEC")

        vm.Operations.value = pathThree

        assertEquals(false, vm.isNormalPathWay())

    }

    @Test
    fun nestPathWayTest(){
        //path way one
        val path = ArrayList<String>()
        path.add("BP")
        path.add("NEST")

        vm.Operations.value = path

        assertEquals(true, vm.isNestPathWay())

        //path way two
        val pathTwo = ArrayList<String>()
        pathTwo.add("BP")
        pathTwo.add("SWIM->NEST")

        vm.Operations.value = pathTwo

        assertEquals(true, vm.isNestPathWay())

        //path way two
        val pathThree = ArrayList<String>()
        pathThree.add("BP")
        pathThree.add("AEC")

        vm.Operations.value = pathThree

        assertEquals(false, vm.isNestPathWay())
    }

    @Test
    fun hasAllInformationTest(){
        //on start should be false
        assertEquals(false, vm.hasFilledInAllInformation())

        //has NA in list only, no photoID
        val path = ArrayList<String>()
        path.add("NA")

        vm.Operations.value = path
        vm.textShown.value = path.joinToString(separator = "-")

        assertEquals(false, vm.hasFilledInAllInformation())

        //added imageID and attempts
        vm.addImage("photoID")
        vm.onNumberOfAttemptsChanged("1",0,0,0)

        assertEquals(true, vm.hasFilledInAllInformation())

    }

    @Test
    fun removeOperationTest(){
        //check size is 0
        assertEquals(0, vm.Operations.value!!.size)

        //has NA in list only
        val path = ArrayList<String>()
        path.add("NA")

        vm.Operations.value = path
        vm.textShown.value = path.joinToString(separator = "-")

        //check size is 1 now operation is added
        assertEquals(1, vm.Operations.value!!.size)

        vm.onClickClearButton()


        //check size is 0 again after removal
        assertEquals(0, vm.Operations.value!!.size)
    }
}