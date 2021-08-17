package archelon.dataModels.Room.adult.Nest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import archelon.dataModels.Room.adult.Nest.AdultNestScreenOneDB

@Entity(
    tableName = "AdultNestScreenTwoDB",
    foreignKeys = [ForeignKey(
        entity = AdultNestScreenOneDB::class,
        parentColumns = arrayOf("nestID"),
        childColumns = arrayOf("nestOne_ID"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class AdultNestScreenTwoDB(
    @PrimaryKey(autoGenerate = true)
    var nest_ID_two: Long = 0L,

    @ColumnInfo(name = "nestOne_ID", index = true)
    var nestOne_ID: Long,

    @ColumnInfo(name = "has_LBM")
    var hasLBM: Boolean,

    @ColumnInfo(name = "has_RBM")
    var hasRBM: Boolean,

    @ColumnInfo(name = "has_3BM")
    var has3BM: Boolean,

    @ColumnInfo(name = "distance_to_sea")
    var distanceToSea: Float,

    @ColumnInfo(name = "distance_to_LBM")
    var distanceToLBM: Float,

    @ColumnInfo(name = "distance_to_RBM")
    var distancetoRBM: Float,

    @ColumnInfo(name = "distance_to_3BM")
    var distanceTo3BM: Float,

    @ColumnInfo(name = "gps_lat")
    var gpsLat: Double,

    @ColumnInfo(name = "gps_long")
    var gpsLong: Double,

    @ColumnInfo(name = "sector")
    var sector: String,

    @ColumnInfo(name = "tag")
    var tag: String,

    @ColumnInfo(name = "comment")
    var comment: String,
)