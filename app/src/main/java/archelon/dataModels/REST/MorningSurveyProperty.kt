package archelon.dataModels.REST

import com.squareup.moshi.Json

//{"nest": ["This field is required."]}
//{"nest": ["Invalid pk \"29\" - object does not exist."]}

data class MorningSurveyProperty(
    @Json(name = "area") val area: String,
    @Json(name = "date") val date: String,
    @Json(name = "beach") val beach:String,
    @Json(name = "sector") val sector: String,
    @Json(name = "subsector") val subSector:String,
    @Json(name = "emergence_event") val emergenceEvent:String,
    @Json(name = "nest") val nest:Int,
    @Json(name = "distance_to_sea") val distanceToSea:Float,
    @Json(name = "track_type") val trackType:String,
    @Json(name = "non_nesting_attempts") val nonNestingAttempts:Int,
    @Json(name = "gps_latitude") val gpsLatitude:Double,
    @Json(name = "gps_longitude") val gpsLongitude:Double,
    @Json(name = "tags") val tags:String,
    @Json(name = "comments") val comments:String,
    @Json(name = "photo_id") val photoID:String
)
