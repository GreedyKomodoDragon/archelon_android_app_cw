package archelon.morningSurvey

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
import java.util.*

@RunWith(AndroidJUnit4::class)
class MorningViewModelTest{

    private lateinit var vm: MorningViewModel
    private lateinit var turtleDao: TurtleDatabaseDao
    private lateinit var db: TurtleDatabase

    @Before
    fun createViewModel(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext


        db = Room.inMemoryDatabaseBuilder(context, TurtleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        turtleDao = db.turtleDatabaseDao

        vm = MorningViewModel(
            ApplicationProvider.getApplicationContext(),
            MorningSurveyStartRepo(turtleDao))
    }

    @Test
    fun isValidDateTest(){

        //valid date
        assertEquals(true, vm.isValidDate("12-12-2012"))
        assertEquals(true, vm.isValidDate("01-01-0001"))


        //not valid date
        assertEquals(false, vm.isValidDate("12-12-0"))
        assertEquals(false, vm.isValidDate(""))
        assertEquals(false, vm.isValidDate("12-12"))
        assertEquals(false, vm.isValidDate("32-12-2020"))

    }

    @Test
    fun updateDateTest(){

        //current data
        val year = Calendar.getInstance()[Calendar.YEAR]
        val month = Calendar.getInstance()[Calendar.MONTH] + 1
        val date = Calendar.getInstance()[Calendar.DATE]

        assertEquals(year, vm.year.value)
        assertEquals(month, vm.month.value)
        assertEquals(date, vm.date.value)

        //update date
        vm.updateDate(2012,5,2)

        assertEquals(2012, vm.year.value)
        assertEquals(6, vm.month.value)
        assertEquals(2, vm.date.value)


        //should go back to todays date
        vm.updateDate(2012,4,32)

        assertEquals(year, vm.year.value)
        assertEquals(month, vm.month.value)
        assertEquals(date, vm.date.value)

    }

    @Test
    fun updateTime(){
        val hour = vm.hour.value
        val min = vm.min.value

        assertEquals(true, hour != null)
        assertEquals(true, min != null)


        //invalid time
        vm.updateTime(null, -1, -56)

        assertEquals(hour, vm.hour.value)
        assertEquals(min, vm.min.value)


        //valid change
        vm.updateTime(null, 12, 12)

        assertEquals(12, vm.hour.value)
        assertEquals(12, vm.min.value)

    }

}