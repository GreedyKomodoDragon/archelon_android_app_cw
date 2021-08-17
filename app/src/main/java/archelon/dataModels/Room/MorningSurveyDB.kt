package archelon.dataModels.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MorningSurveyPartial")
data class MorningSurveyDB(
    @PrimaryKey(autoGenerate = true)
    var surveyID: Long = 0L,

    @ColumnInfo(name = "beach")
    val beach: String,

    @ColumnInfo(name = "sector")
    var sector:String,

    @ColumnInfo(name = "day")
    var day: Int,

    @ColumnInfo(name = "month")
    var month: Int,

    @ColumnInfo(name = "year")
    var year: Int,

    //24hr clock is used here
    @ColumnInfo(name = "hour")
    var hour: Int,

    @ColumnInfo(name = "min")
    var min: Int,

)