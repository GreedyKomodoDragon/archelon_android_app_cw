package archelon.dataModels.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey


//TODO: add cascade on delete
@Entity(
    tableName = "ObserversMSPartial",
    foreignKeys = arrayOf(
    ForeignKey(
        entity = MorningSurveyDB::class,
        parentColumns = arrayOf("surveyID"),
        childColumns = arrayOf("MS_ID"),
        onDelete = CASCADE
    )))
data class ObserversMSPartialDB(
    @PrimaryKey(autoGenerate = true)
    var observerID: Long = 0L,

    @ColumnInfo(name = "MS_ID", index = true)
    var MS_ID: Long,

    @ColumnInfo(name = "obser_one")
    val obserOne: String,

    @ColumnInfo(name = "obser_two")
    val obserTwo: String,

    @ColumnInfo(name = "obser_three")
    val obserThree: String,

    @ColumnInfo(name = "obser_four")
    val obserFour: String,

    @ColumnInfo(name = "sky")
    val sky: String,

    @ColumnInfo(name = "precipitation")
    val precipitation: String,

    @ColumnInfo(name = "w_intensity")
    val wIntensity: String,


    @ColumnInfo(name = "w_direction")
    val wDirection: String,

    @ColumnInfo(name = "surf")
    val surf: String
)