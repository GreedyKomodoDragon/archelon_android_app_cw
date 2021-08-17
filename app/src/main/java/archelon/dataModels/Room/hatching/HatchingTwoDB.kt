package archelon.dataModels.Room.hatching

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "HatchingTwo",
    foreignKeys = [ForeignKey(
        entity = HatchingOneDB::class,
        parentColumns = arrayOf("hatchOneID"),
        childColumns = arrayOf("hatch_one_ID"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class HatchingTwoDB(
    @PrimaryKey(autoGenerate = true)
    var hatchTwoID: Long = 0L,

    @ColumnInfo(name = "hatch_one_ID", index = true)
    var hatch_one_ID: Long,

    @ColumnInfo(name = "gps_lat")
    var gpsLat: Double,

    @ColumnInfo(name = "gps_long")
    var gpsLong: Double,

    @ColumnInfo(name = "tag")
    var tag:String,

    @ColumnInfo(name = "comment")
    var comment:String,

    @ColumnInfo(name = "photo_ID")
    var photoID:String
)