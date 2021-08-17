package archelon.dataModels.Room.hatching

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "PairsHatch",
    foreignKeys = [ForeignKey(
        entity = HatchingTwoDB::class,
        parentColumns = arrayOf("hatchTwoID"),
        childColumns = arrayOf("hatch_Two"),
        onDelete = CASCADE
    )]
)
data class PairsHatch(
    @PrimaryKey(autoGenerate = true)
    var pairHatchID: Long = 0L,

    @ColumnInfo(name = "hatch_Two", index = true)
    var hatchTwo: Long,

    @ColumnInfo(name = "left")
    var left: String,

    @ColumnInfo(name = "right")
    var right: String
)