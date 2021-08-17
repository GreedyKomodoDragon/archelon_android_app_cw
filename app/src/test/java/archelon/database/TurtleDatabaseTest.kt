package archelon.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import archelon.dataModels.Room.MorningSurveyDB
import archelon.dataModels.Room.ObserversMSPartialDB
import archelon.dataModels.Room.adult.AdultEmergenceMain
import archelon.dataModels.Room.adult.Normal.EmergenceNormalDB
import archelon.dataModels.Room.hatching.HatchingOneDB
import archelon.dataModels.Room.hatching.HatchingTwoDB
import archelon.dataModels.Room.hatching.PairsHatch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class TurtleDatabaseTest{
    private lateinit var turtleDao: TurtleDatabaseDao
    private lateinit var db: TurtleDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext


        db = Room.inMemoryDatabaseBuilder(context, TurtleDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        turtleDao = db.turtleDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetMS() {
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

            val lastMS = turtleDao.getCurrentMS()
            assertEquals(1, lastMS)
        }
    }

    private suspend fun insertMSAndOB(_turtleDao: TurtleDatabaseDao): Long{
        val ms = MorningSurveyDB(0,
            "beachTest",
            "east",
            13,
            9,
            1998,
            2,
            32
        )

        _turtleDao.insertMS(ms)

        val lastMS = _turtleDao.getCurrentMS()

        //create data model
        val obserDB = ObserversMSPartialDB(
            0,
            lastMS!!.toLong(),
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

        _turtleDao.insertObservers(obserDB)

        return lastMS.toLong()
    }

    @Test
    @Throws(Exception::class)
    fun insertMSThenOB() {
        runBlocking {

            insertMSAndOB(turtleDao)

            val ob = turtleDao.getLastOB()

            assertEquals(1, ob)
        }
    }

    private suspend fun insertHatchOne(): Long{
        val msID = insertMSAndOB(turtleDao)

        val hatch1 = HatchingOneDB(
            0,
            msID,
            false,
            100,
            "MW001",
            false,
            "nestCode",
            false,
            true,
            false,
            24f,
            2f,
            5f,
            3f,
            "photoID"
        )

        turtleDao.insertHatchOne(hatch1)

        return turtleDao.getLastHatchOne()!!
    }

    @Test
    @Throws(Exception::class)
    fun insertHatchOneTest(){
        runBlocking {

            val ob = insertHatchOne()

            assertEquals(1, ob)
        }
    }

    private suspend fun insertHatchTwo():Long{
        val hatchOneID= insertHatchOne()

        val hatch2 = HatchingTwoDB(
            0,
            hatchOneID,
            12.0,
            0.0,
            "klhsdl",
            "sdjkflj",
            "siahd"
        )

        turtleDao.insertHatchTwo(hatch2)

        return turtleDao.getLastHatchTwoID()!!
    }

    @Test
    @Throws(Exception::class)
    fun insertHatchTwoTest(){
        runBlocking {

            val ob = insertHatchTwo()

            assertEquals(1, ob)
        }
    }


    @Test
    @Throws(Exception::class)
    fun insertHatchTwoPairs(){
        runBlocking {

            val hatchTwoID = insertHatchTwo()

            for(i in 0..7){
                val data = PairsHatch(
                    0,
                    hatchTwoID,
                    "first$i",
                    "second$i"
                )

                turtleDao.insertHatchPair(data)
            }

            val ob = turtleDao.getLastPairsHatchID()

            assertEquals(8L, ob)


        }
    }

    @Test
    @Throws(Exception::class)
    fun removeMS(){
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

            val lastMS = turtleDao.getCurrentMS()
            assertEquals(1, lastMS)

            turtleDao.clearMS()
            val lastMS2 = turtleDao.getCurrentMS()
            assertEquals(null, lastMS2)
        }
    }

    @Test
    fun insertAdultMain(){
        runBlocking {

            insertMSAndOB(turtleDao)

            val ob = turtleDao.getCurrentMS()

            val dataModel = AdultEmergenceMain(
                0,
                ob!!.toLong(),
                "path",
                "photoID",
                1
            )

            turtleDao.insertAdultEmergence(dataModel)

            val adultID = turtleDao.getCurrentEmergence()
            assertEquals(1L, adultID)
        }
    }

    @Test
    @Throws(Exception::class)
    fun removeAdultMain(){
        runBlocking {

            insertMSAndOB(turtleDao)

            val ob = turtleDao.getCurrentMS()

            val dataModel = AdultEmergenceMain(
                0,
                ob!!.toLong(),
                "path",
                "photoID",
                1
            )

            turtleDao.insertAdultEmergence(dataModel)

            val adultID = turtleDao.getCurrentEmergence()
            assertEquals(1L, adultID)

            turtleDao.removeLastAdultMain()

            val adultID2 = turtleDao.getCurrentEmergence()
            assertEquals(null, adultID2)

        }
    }

    @Test
    @Throws(Exception::class)
    fun insertEmergenceNormal(){
        runBlocking {
            insertMSAndOB(turtleDao)

            val ob = turtleDao.getCurrentMS()

            val dataModel = AdultEmergenceMain(
                0,
                ob!!.toLong(),
                "path",
                "photoID",
                1
            )

            turtleDao.insertAdultEmergence(dataModel)

            val normalDB = EmergenceNormalDB(
                0,
                1L,
                false,
                false,
                'A',
                false,
                true,
                false,
                2f,
                5f,
                5f,
                4f,
                56.0,
                78.0,
                "kdslf",
                "jldfh",
                "dsklh"
            )

            turtleDao.insertEmergenceNormal(normalDB)

            val model = turtleDao.getNormalByAdultID(1L)

            assertEquals(1L, model.normalID)
        }
    }

    @Test
    @Throws(Exception::class)
    fun containsEmergenceNormal(){
        runBlocking {
            insertMSAndOB(turtleDao)

            val ob = turtleDao.getCurrentMS()

            val dataModel = AdultEmergenceMain(
                0,
                ob!!.toLong(),
                "path",
                "photoID",
                1
            )

            turtleDao.insertAdultEmergence(dataModel)

            val beforeValue = turtleDao.containHowManyEmergenceNormaWithAdultID(1L)

            assertEquals(0L, beforeValue)

            val normalDB = EmergenceNormalDB(
                0,
                1L,
                false,
                false,
                'A',
                false,
                true,
                false,
                2f,
                5f,
                5f,
                4f,
                56.0,
                78.0,
                "kdslf",
                "jldfh",
                "dsklh"
            )

            turtleDao.insertEmergenceNormal(normalDB)

            val afterValue = turtleDao.containHowManyEmergenceNormaWithAdultID(1L)

            assertEquals(1L, afterValue)
        }
    }

    @Test
    @Throws(Exception::class)
    fun updateEmergenceNormal(){
        runBlocking {
            insertMSAndOB(turtleDao)

            val ob = turtleDao.getCurrentMS()

            val dataModel = AdultEmergenceMain(
                0,
                ob!!.toLong(),
                "path",
                "photoID",
                1
            )

            turtleDao.insertAdultEmergence(dataModel)


            var normalDB = EmergenceNormalDB(
                0,
                1L,
                false,
                false,
                'A',
                false,
                true,
                false,
                2f,
                5f,
                5f,
                4f,
                56.0,
                78.0,
                "kdslf",
                "jldfh",
                "dsklh"
            )

            turtleDao.insertEmergenceNormal(normalDB)

            normalDB.comments = "it worked"

            val idInDB = turtleDao.getNormalByAdultID(1L)

            normalDB.normalID = idInDB.normalID

            turtleDao.updateEmergenceNormal(normalDB)

            val idInDB2 = turtleDao.getNormalByAdultID(1L)

            assertEquals(1L, idInDB2.normalID)
            assertEquals("it worked", idInDB2.comments)


        }
    }




}