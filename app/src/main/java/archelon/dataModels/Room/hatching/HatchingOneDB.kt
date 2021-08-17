package archelon.dataModels.Room.hatching

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import archelon.dataModels.Room.MorningSurveyDB


@Entity(
    tableName = "HatchingOne",
    foreignKeys = [ForeignKey(
        entity = MorningSurveyDB::class,
        parentColumns = arrayOf("surveyID"),
        childColumns = arrayOf("MS_ID"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class HatchingOneDB(
    @PrimaryKey(autoGenerate = true)
    var hatchOneID: Long = 0L,

    @ColumnInfo(name = "MS_ID", index = true)
    var MS_ID: Long,

    @ColumnInfo(name = "is_first_hatch")
    var isFirstHatch : Boolean,

    @ColumnInfo(name = "timestamp")
    var timestamp : Long,

    @ColumnInfo(name = "hatch_code")
    var hatchCode : String,

    @ColumnInfo(name = "nest_stones_found")
    var hasStonesNextToNest : Boolean,

    @ColumnInfo(name = "nest_code")
    var nestCode:String,

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
    var distanceToRBM: Float,

    @ColumnInfo(name = "distance_to_3BM")
    var distanceTo3BM: Float,

    @ColumnInfo(name = "photo_ID")
    var photoID: String
)