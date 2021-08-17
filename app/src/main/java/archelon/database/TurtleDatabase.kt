package archelon.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import archelon.dataModels.Room.*
import archelon.dataModels.Room.adult.Nest.AdultNestScreenTwoDB
import archelon.dataModels.Room.adult.Nest.AdultNestScreenOneDB
import archelon.dataModels.Room.adult.AdultEmergenceMain
import archelon.dataModels.Room.adult.Normal.EmergenceNormalDB
import archelon.dataModels.Room.hatching.HatchingOneDB
import archelon.dataModels.Room.hatching.HatchingTwoDB
import archelon.dataModels.Room.hatching.PairsHatch

@Database(
    entities = [MorningSurveyDB::class, ObserversMSPartialDB::class, AdultNestScreenOneDB::class,
        AdultNestScreenTwoDB::class, AdultEmergenceMain::class, HatchingOneDB::class,
        HatchingTwoDB::class, PairsHatch::class, EmergenceNormalDB::class],
    version = 8,
    exportSchema = false
)
abstract class TurtleDatabase : RoomDatabase() {

    abstract val turtleDatabaseDao: TurtleDatabaseDao


    companion object {

        @Volatile
        private var INSTANCE: TurtleDatabase? = null

        fun getInstance(context: Context): TurtleDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TurtleDatabase::class.java,
                        "turtle_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }

                return instance
            }
        }
    }
}