package archelon.adultEmergence.nest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import archelon.adultEmergence.nest.one.AdultEmergenceSubOneRepo
import archelon.adultEmergence.nest.one.AdultEmergenceSubOneViewModel
import archelon.database.TurtleDatabase
import archelon.database.TurtleDatabaseDao
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AdultEmergenceSubOneViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    private var instantExecutorRule = InstantTaskExecutorRule()

    // Subject under test
    private lateinit var vm: AdultEmergenceSubOneViewModel
    private lateinit var turtleDao: TurtleDatabaseDao
    private lateinit var db: TurtleDatabase

    @Before
    fun setupViewModel() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext


        db = Room.inMemoryDatabaseBuilder(context, TurtleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        turtleDao = db.turtleDatabaseDao

        vm = AdultEmergenceSubOneViewModel(ApplicationProvider.getApplicationContext(), AdultEmergenceSubOneRepo(turtleDao))
    }


    @Test
    fun updateDistanceEggValidValue() {
        vm.updateDistanceEgg("8",0 ,0 ,0)

        val value = vm.distanceToTopEggCMDouble.getOrAwaitValue()

        assertEquals(8.0, value)

    }


    @Test
    fun updateDistanceEggValidUnexpected() {
        vm.updateDistanceEgg("-1",0 ,0 ,0)

        val value = vm.distanceToTopEggCMDouble.getOrAwaitValue()

        vm.updateDistanceEgg("a",0 ,0 ,0)

        val valueA = vm.distanceToTopEggCMDouble.getOrAwaitValue()

        assertEquals(null, value)
        assertEquals(null, valueA)

    }

    @Test
    fun updateDistanceEggValidBoundaryTest() {
        //Before valid
        vm.updateDistanceEgg("-1",0 ,0 ,0)
        val value = vm.distanceToTopEggCMDouble.getOrAwaitValue()
        assertEquals(null, value)

        //On first valid
        vm.updateDistanceEgg("0",0 ,0 ,0)
        val valueZero = vm.distanceToTopEggCMDouble.getOrAwaitValue()
        assertEquals(0.0, valueZero)

    }

    @Test
    fun updateIsNestProtected(){
        //on start
        assertEquals(false, vm.isNestProtected.value)

        //changing to is protected
        vm.updateProtectedNest(true)
        val value = vm.isNestProtected.getOrAwaitValue()

        assertEquals(true, value)

        //changing to is not protected
        vm.updateProtectedNest(false)
        val value2 = vm.isNestProtected.getOrAwaitValue()

        assertEquals(false, value2)

    }

    @Test
    fun updateCheckBoxes(){
        //on start all checkboxes should be false
        assertEquals(false, vm.isCageWooden.value)
        assertEquals(false, vm.isCageIron.value)
        assertEquals(false, vm.hasBambooTripod.value)
        assertEquals(false, vm.hasScreen.value)
        assertEquals(false, vm.hasBeenRelocated.value)
        assertEquals(false, vm.hasAlleywayChecked.value)

        //Perform update
        vm.updateCageIron()
        vm.updateCageWooden()
        vm.updateHasBamboo()
        vm.updateHasScreen()
        vm.updateRelocated()
        vm.updateAlleywayChecked()

        //Should all been turned on
        assertEquals(true, vm.isCageWooden.value)
        assertEquals(true, vm.isCageIron.value)
        assertEquals(true, vm.hasBambooTripod.value)
        assertEquals(true, vm.hasScreen.value)
        assertEquals(true, vm.hasBeenRelocated.value)
        assertEquals(true, vm.hasAlleywayChecked.value)

        //Perform update
        vm.updateCageIron()
        vm.updateCageWooden()
        vm.updateHasBamboo()
        vm.updateHasScreen()
        vm.updateRelocated()
        vm.updateAlleywayChecked()

        //should all been turned off again
        assertEquals(false, vm.isCageWooden.value)
        assertEquals(false, vm.isCageIron.value)
        assertEquals(false, vm.hasBambooTripod.value)
        assertEquals(false, vm.hasScreen.value)
        assertEquals(false, vm.hasBeenRelocated.value)
        assertEquals(false, vm.hasAlleywayChecked.value)

    }

    @Test
    fun allRequiredInformationFilledInTest(){
        assertEquals(false, vm.allRequiredInformationFilledIn())
        vm.updateDistanceEgg("4", 0,0,0)
        assertEquals(true, vm.allRequiredInformationFilledIn())
    }
}