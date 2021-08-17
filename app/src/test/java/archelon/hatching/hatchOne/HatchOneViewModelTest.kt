package archelon.hatching.hatchOne

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import archelon.database.TurtleDatabase
import archelon.database.TurtleDatabaseDao
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HatchOneViewModelTest{

    private lateinit var vm: HatchOneViewModel
    private lateinit var turtleDao: TurtleDatabaseDao
    private lateinit var db: TurtleDatabase

    @Before
    fun createViewModel(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext


        db = Room.inMemoryDatabaseBuilder(context, TurtleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        turtleDao = db.turtleDatabaseDao

        vm = HatchOneViewModel(ApplicationProvider.getApplicationContext(),
            HatchOneRepo(turtleDao, MockAPI(),ApplicationProvider.getApplicationContext()))
    }

    @Test
    fun updatingBMValues(){
        //starting values
        assertEquals(false, vm.has3BM.value)
        assertEquals(false, vm.hasRBM.value)
        assertEquals(true, vm.hasLBM.value)

        //make 3BM selected
        vm.updateBM(hasLBM = false, hasRBM = false)
        assertEquals(true, vm.has3BM.value)
        assertEquals(false, vm.hasRBM.value)
        assertEquals(false, vm.hasLBM.value)

        //make LBM selected
        vm.updateBM(hasLBM = true, hasRBM = false)
        assertEquals(false, vm.has3BM.value)
        assertEquals(false, vm.hasRBM.value)
        assertEquals(true, vm.hasLBM.value)

        //make RBM selected
        vm.updateBM(hasLBM = false, hasRBM = true)
        assertEquals(false, vm.has3BM.value)
        assertEquals(true, vm.hasRBM.value)
        assertEquals(false, vm.hasLBM.value)

        //if both true, should ignore second argument
        vm.updateBM(hasLBM = true, hasRBM = true)
        assertEquals(false, vm.has3BM.value)
        assertEquals(false, vm.hasRBM.value)
        assertEquals(true, vm.hasLBM.value)
    }

    @Test
    fun updatingDistances(){
        //starting values
        assertEquals(0f, vm.distanceTo3BM.value)
        assertEquals(0f, vm.distanceToSea.value)
        assertEquals(0f, vm.distanceToLBM.value)
        assertEquals(0f, vm.distanceToRBM.value)

        //on valid change
        vm.onDistanceTo3BMChange("3",0,0,0)
        vm.onDistanceToLBMChange("3",0,0,0)
        vm.onDistanceToRBMChange("3",0,0,0)
        vm.onDistanceToSeaChange("3",0,0,0)
        assertEquals(3f, vm.distanceTo3BM.value)
        assertEquals(3f, vm.distanceToLBM.value)
        assertEquals(3f, vm.distanceToRBM.value)
        assertEquals(3f, vm.distanceToSea.value)

        //on invalid change
        vm.onDistanceTo3BMChange("a",0,0,0)
        assertEquals(0f, vm.distanceTo3BM.value)

        vm.onDistanceToLBMChange("",0,0,0)
        assertEquals(0f, vm.distanceToLBM.value)

    }

    @Test
    fun allLiveDataObtainedTesting(){
        //on start should be invalid
        assertEquals(false, vm.allLiveDataObtained())

        //update only the distances
        vm.onDistanceTo3BMChange("3",0,0,0)
        vm.onDistanceToLBMChange("3",0,0,0)
        vm.onDistanceToRBMChange("3",0,0,0)
        vm.onDistanceToSeaChange("3",0,0,0)
        assertEquals(false, vm.allLiveDataObtained())

        //update photoID
        vm.addImage("testID")
        assertEquals(false, vm.allLiveDataObtained())

        //add nest code
        vm.updateNestCode(1)
        assertEquals(false, vm.allLiveDataObtained())

        //hatch code
        vm.onHatchCodeChange("name",0,0,0)
        assertEquals(true, vm.allLiveDataObtained())
    }
}