package archelon.dataModels.Room.adult.Nest

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import archelon.dataModels.Room.MorningSurveyDB
import archelon.dataModels.Room.adult.AdultEmergenceMain

@Entity(
    tableName = "AdultNestScreenDB",
    foreignKeys = [ForeignKey(
        entity = AdultEmergenceMain::class,
        parentColumns = arrayOf("adultID"),
        childColumns = arrayOf("adult_ID"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class AdultNestScreenOneDB(
    @PrimaryKey(autoGenerate = true)
    var nestID: Long = 0L,

    @ColumnInfo(name = "adult_ID", index = true)
    var adult_ID: Long,

    @ColumnInfo(name = "distance_to_top_egg")
    var distanceToTopEgg: Double,

    @ColumnInfo(name = "is_nest_protected")
    var isNestProtected:Boolean,

    @ColumnInfo(name = "is_cage_wooden")
    var isCageWooden: Boolean,

    @ColumnInfo(name = "is_cage_iron")
    var isCageIron: Boolean,

    @ColumnInfo(name = "has_bamboo_tripod")
    var hasBambooTripod: Boolean,

    @ColumnInfo(name = "has_screen")
    var hasScreen: Boolean,

    @ColumnInfo(name = "has_been_relocated")
    var hasBeenRelocated: Boolean,

    @ColumnInfo(name = "has_alleyway")
    var hasAlleyway: Boolean,

    @ColumnInfo(name = "other_protection")
    var otherProtection: String,

    @ColumnInfo(name = "is_nest_tagged")
    var isNestTagged: Boolean,

    @ColumnInfo(name = "track_type")
    var trackType: Char,

    @ColumnInfo(name = "nest_code")
    var nestCode:String
)