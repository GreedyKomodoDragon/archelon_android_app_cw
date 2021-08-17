package archelon

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import archelon.database.TurtleDatabase
import archelon.database.TurtleDatabaseDao
import archelon.morningSurvey.MorningSurveyStartRepo
import archelon.morningSurvey.MorningViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class MorningSurveyFragementViewModelTest {

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

        vm = MorningViewModel(ApplicationProvider.getApplicationContext(), MorningSurveyStartRepo(turtleDao))
    }

    @Test
    fun vmStartsOnToday(){
        // Get today's date
        val cal: Calendar = Calendar.getInstance()
        val year: Int = cal.get(Calendar.YEAR)
        val month: Int = cal.get(Calendar.MONTH) + 1 //starts at 0
        val day: Int = cal.get(Calendar.DAY_OF_MONTH)


        assertEquals(year, vm.year.value)
        assertEquals(month, vm.month.value)
        assertEquals(day, vm.date.value)
    }

    @Test
    fun isValidDate(){
        assertEquals(true, vm.isValidDate("${1}-${5}-${7}"))
    }

    @Test
    fun isNotValidDate(){
        assertEquals(false, vm.isValidDate("${-1}-${5}-${7}"))
    }

    @Test
    fun vmUpdateDate(){
        vm.updateDate(1, 5, 7)
        assertEquals(1, vm.year.value)
        assertEquals(6, vm.month.value)
        assertEquals(7, vm.date.value)
    }

}
