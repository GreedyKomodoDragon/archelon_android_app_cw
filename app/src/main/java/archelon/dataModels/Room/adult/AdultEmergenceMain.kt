package archelon.dataModels.Room.adult

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import archelon.dataModels.Room.MorningSurveyDB

@Entity(
    tableName = "adultEmergence",
    foreignKeys = [ForeignKey(
        entity = MorningSurveyDB::class,
        parentColumns = arrayOf("surveyID"),
        childColumns = arrayOf("MS_ID"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class AdultEmergenceMain(
    @PrimaryKey(autoGenerate = true)
    var adultID: Long = 0L,

    @ColumnInfo(name = "MS_ID", index = true)
    var MS_ID: Long,

    @ColumnInfo(name = "path")
    var path: String,

    @ColumnInfo(name = "photoID")
    var photoID: String,

    @ColumnInfo(name = "numberOfAttempts")
    var numberOfAttempts: Int
)