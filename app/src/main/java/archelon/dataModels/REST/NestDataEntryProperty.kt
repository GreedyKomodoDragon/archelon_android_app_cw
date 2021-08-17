package archelon.dataModels.REST

import com.squareup.moshi.Json
import java.util.*

data class NestDataEntryProperty (
    @Json(name = "nest") val nest:Int,
    @Json(name = "date")  val date: String,
    @Json(name = "start_time") val startTime:String, //TODO: "2020-11-27T12:49:44.164000Z", this format
    @Json(name = "end_time") val endTime:String,
    @Json(name = "relocated_to") val relocatedTo:String,
    @Json(name = "reason_for_relocation") val reasonForRelocation:String,
    @Json(name = "top_egg_depth") val topEggDepth:Double,
    @Json(name = "bottom_of_nest_depth") val bottomOfEggDepth:Int,
    @Json(name = "distance_to_sea") val distanceToSea:Double,
    @Json(name = "number_of_eggs") val numberOfEggs:Int,
    @Json(name = "gps_latitude") val gpsLatitude:Double,
    @Json(name = "gps_longitude") val gpsLongitude:Double
)
