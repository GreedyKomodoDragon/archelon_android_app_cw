package archelon.dataModels.Room.adult.Normal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import archelon.dataModels.Room.MorningSurveyDB
import archelon.dataModels.Room.adult.AdultEmergenceMain


@Entity(
    tableName = "EmergenceNormal",
    foreignKeys = [ForeignKey(
        entity = AdultEmergenceMain::class,
        parentColumns = arrayOf("adultID"),
        childColumns = arrayOf("adult_ID"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class EmergenceNormalDB(
    @PrimaryKey(autoGenerate = true)
    var normalID: Long = 0L,

    @ColumnInfo(name = "adult_ID", index = true)
    var adult_ID: Long,

    @ColumnInfo(name = "is_suspected_nest")
    var isSuspectedNest: Boolean,

    @ColumnInfo(name = "dug_and_no_eggs_found")
    var hasDugNoEggsFound: Boolean,

    @ColumnInfo(name = "track_type")
    var trackType: Char,

    @ColumnInfo(name = "has_LBM")
    var hasLBM:Boolean,

    @ColumnInfo(name = "has_RBM")
    var hasRBM:Boolean,

    @ColumnInfo(name = "has_3BM")
    var has3BM:Boolean,

    @ColumnInfo(name = "distance_to_sea")
    var distanceToSea: Float,

    @ColumnInfo(name = "distance_to_LBM")
    var distanceToLBM: Float,

    @ColumnInfo(name = "distance_to_RBM")
    var distanceToRBM: Float,

    @ColumnInfo(name = "distance_to_3BM")
    var distanceTo3BM: Float,

    @ColumnInfo(name = "gps_long")
    var gpsLong: Double,

    @ColumnInfo(name = "gps_lat")
    var gpsLat: Double,

    @ColumnInfo(name = "subSector")
    var sub_sector: String,

    @ColumnInfo(name = "tag")
    var tag: String,

    @ColumnInfo(name = "comments")
    var comments: String,

)