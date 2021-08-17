package archelon.dataModels.REST

import com.squareup.moshi.Json
import java.util.*

data class NestProperty(
    @Json(name = "nest_code") val nestCode:String,
    @Json(name = "beach")  val beach:String,
    @Json(name = "sector") val sector:String,
    @Json(name = "subsector") val subSector:String,
    @Json(name = "protection_measures") val protectionMeasures:String,
    @Json(name = "inundated_during_incubation") val inundatedDuringIncubation:Boolean,
    @Json(name = "predated_during_incubation") val predatedDuringIncubation:Boolean,
    @Json(name = "date_of_first_hatching") val dayOfFirstHatching:String, //Format: 2020-11-27"
    @Json(name = "date_of_last_hatching") val dayOfLastHatching:String, //Format: 2020-11-27"
    @Json(name = "inundated_during_hatching") val inundatedDuringHatching:Boolean,
    @Json(name = "predated_during_hatching") val predatedDuringHatching:Boolean,
    @Json(name = "affected_by_light_pollution") val affectedByLightPollution:Boolean,
    @Json(name = "excavation_date") val excavationDate:String,
    @Json(name = "excavation_bottom_of_nest_depth") val excavationBottomOfNestDepth:Int,
    @Json(name = "hatched_eggs") val numberOfHatchedEggs:Int,
    @Json(name = "pipped_dead_hatchlings") val numberOfPippedDeadHatchlings:Int,
    @Json(name = "pipped_live_hatchlings") val numberOfPippedLiveHatchlings:Int,
    @Json(name = "no_embryos_in_unhatched_eggs") val numberOfEmbryosInUnhatchedEggs:Int,
    @Json(name = "dead_embryos_in_unhatched_eggs_eye_spot") val numberOfDeadEmbryosInUnhatchedEggsEyeSpot:Int,
    @Json(name = "dead_embryos_in_unhatched_eggs_early") val numberOfDeadEmbryosInUnhatchedEggsEarly:Int,
    @Json(name = "dead_embryos_in_unhatched_eggs_middle") val numberOfDeadEmbryosInUnhatchedEggsMiddle:Int,
    @Json(name = "dead_embryos_in_unhatched_eggs_late") val numberOfDeadEmbryosInUnhatchedEggsLate:Int,
    @Json(name = "live_embryos_in_unhatched_eggs") val numberOfLiveEmbryosInUnhatchedEggs:Int,
    @Json(name = "dead_hatchlings_in_nest") val numberOfDeadHatchlingsInNest:Int,
    @Json(name = "live_hatchlings_in_nest") val numberOfLiveHatchlingsInNest:Int,
    @Json(name = "excavation_comments") val excavationComments:String,
    @Json(name = "general_comments") val generalComments:String
)
