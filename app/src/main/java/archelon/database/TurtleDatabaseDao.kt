package archelon.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import archelon.dataModels.Room.MorningSurveyDB
import archelon.dataModels.Room.ObserversMSPartialDB
import archelon.dataModels.Room.adult.AdultEmergenceMain
import archelon.dataModels.Room.adult.Nest.AdultNestScreenOneDB
import archelon.dataModels.Room.adult.Nest.AdultNestScreenTwoDB
import archelon.dataModels.Room.adult.Normal.EmergenceNormalDB
import archelon.dataModels.Room.hatching.HatchingOneDB
import archelon.dataModels.Room.hatching.HatchingTwoDB
import archelon.dataModels.Room.hatching.PairsHatch

@Dao
interface TurtleDatabaseDao {

    @Insert
    suspend fun insertMS(ms:MorningSurveyDB)

    /**
     *Get the most recent morning survey to be added (used to get the ms you are on)
     */
    @Query("SELECT surveyID FROM morningsurveypartial  ORDER BY surveyID DESC LIMIT 1")
    suspend fun getCurrentMS():Int?

    /**
     * Deletes the table's only its contents, used when uploading as no longer needed
     */
    @Query("DELETE FROM morningsurveypartial")
    suspend fun clearMS()

    @Insert
    suspend fun insertObservers(ob:ObserversMSPartialDB)

    @Query("SELECT observerID FROM ObserversMSPartial  ORDER BY observerID DESC LIMIT 1")
    suspend fun getLastOB():Int?

    @Insert
    suspend fun insertAdultEmergence(ae: AdultEmergenceMain)

    @Insert
    suspend fun insertHatchOne(hatchOne:HatchingOneDB)

    @Insert
    suspend fun insertNestOne(AdultNestOne:AdultNestScreenOneDB)

    @Insert
    suspend fun insertNestTwo(dm: AdultNestScreenTwoDB)

    @Insert
    suspend fun insertHatchTwo(hatchTwo:HatchingTwoDB)

    @Insert
    suspend fun insertHatchPair(hPair: PairsHatch)

    @Insert
    suspend fun insertEmergenceNormal(emNormalDB: EmergenceNormalDB)

    @Query("SELECT hatchOneID FROM hatchingone  ORDER BY hatchOneID DESC LIMIT 1")
    suspend fun getLastHatchOne():Long?

    @Query("SELECT hatchTwoID FROM HatchingTwo  ORDER BY hatchTwoID DESC LIMIT 1")
    suspend fun getLastHatchTwoID():Long?

    @Query("SELECT pairHatchID FROM PairsHatch  ORDER BY pairHatchID DESC LIMIT 1")
    suspend fun getLastPairsHatchID():Long?

    @Query("SELECT adultID FROM adultEmergence  ORDER BY adultID DESC LIMIT 1")
    suspend fun getCurrentEmergence():Long?

    @Query("SELECT nestID FROM AdultNestScreenDB  ORDER BY nestID DESC LIMIT 1")
    suspend fun getNestOneID():Long?

    //Get the number of entries of adultEmergence
    @Query("SELECT COUNT(*) FROM adultEmergence WHERE MS_ID = :msID ")
    suspend fun getNumberOfEntriesOfAdult(msID:Long):Int

    //Remove the last Adult main record
    @Query("DELETE FROM adultEmergence WHERE adultID IN (SELECT adultID FROM adultEmergence ORDER BY adultID DESC LIMIT 1)")
    suspend fun removeLastAdultMain()

    //Remove the last Morning Survery record
    @Query("DELETE FROM MorningSurveyPartial WHERE surveyID IN (SELECT surveyID FROM MorningSurveyPartial ORDER BY surveyID DESC LIMIT 1)")
    suspend fun removeCurrentMorningSurvey()

    //Remove the last Hatch One record
    @Query("DELETE FROM HatchingOne WHERE hatchOneID IN (SELECT hatchOneID FROM HatchingOne ORDER BY hatchOneID DESC LIMIT 1)")
    suspend fun removeHatchOne()

    //Get the morning survey data
    @Query("SELECT * FROM morningsurveypartial  ORDER BY surveyID DESC LIMIT 1")
    suspend fun getCurrentMSData(): MorningSurveyDB

    //Get all adult main data where id = surveyID
    @Query("SELECT * FROM adultEmergence WHERE MS_ID = :msID ")
    suspend fun getAllAdultEmergence(msID:Long): List<AdultEmergenceMain>

    //Get nest one by using adult one id
    @Query("SELECT * FROM adultnestscreendb WHERE adult_ID = :forID LIMIT 1")
    suspend fun getNestOneByForeignID(forID:Long): AdultNestScreenOneDB

    //Get nest two by using adult two id
    @Query("SELECT * FROM adultnestscreentwodb WHERE nestOne_ID = :forID LIMIT 1")
    suspend fun getNestTwoByNestOne(forID:Long): AdultNestScreenTwoDB

    //Get nest two by using adult two id
    @Query("SELECT * FROM emergencenormal WHERE adult_ID = :forID LIMIT 1")
    suspend fun getNormalByAdultID(forID:Long): EmergenceNormalDB

    //Check if emergenceNormal
    @Query("SELECT COUNT(*) FROM emergencenormal WHERE normalID = :ID")
    suspend fun containHowManyEmergenceNormaWithAdultID(ID:Long):Long

    @Update
    suspend fun updateEmergenceNormal(dataModel:EmergenceNormalDB)

    @Query("DELETE FROM adultnestscreendb WHERE nestID = :ID")
    suspend fun removeNestOne(ID:Long)
}