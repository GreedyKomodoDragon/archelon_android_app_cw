package archelon.hatching.hatchOne

import archelon.dataModels.REST.MorningSurveyProperty
import archelon.dataModels.REST.MorningSurveyPropertyID
import archelon.dataModels.REST.NestDataEntryProperty
import archelon.dataModels.REST.NestProperty
import archelon.restAPI.ArchelonAPIService

class MockAPI : ArchelonAPIService {
    override suspend fun getAllNests(tokenKey: String): List<NestProperty> {
        val nestMockList = ArrayList<NestProperty>()
        val firstObject = NestProperty(
            "nestcode",
            "beachTest",
            "sectorExample",
            "subSectorE",
            "pro",
            false,
            false,
            "dfsdlhf",
            "dskhfsl",
            false,
            false,
            false,
            "2016",
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            "commentOne",
            "commentTwo"
        )

        val secondObject = NestProperty(
            "nestcode",
            "beachTest",
            "sectorExample",
            "subSectorE",
            "pro",
            false,
            false,
            "dfsdlhf",
            "dskhfsl",
            false,
            false,
            false,
            "2016",
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            "commentOne",
            "commentTwo"
        )

        nestMockList.add(firstObject)
        nestMockList.add(secondObject)

        return nestMockList
    }

    override suspend fun getNestByID(id: Int, tokenKey: String): NestProperty {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNestByID(id: Int, tokenKey: String): NestProperty {
        TODO("Not yet implemented")
    }

    override suspend fun addNewNestProperty(body: NestProperty, tokenKey: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNestList(id: Int, body: NestProperty, tokenKey: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllNestEntries(tokenKey: String): List<NestDataEntryProperty> {
        TODO("Not yet implemented")
    }

    override suspend fun getNestEntryByID(id: Int, tokenKey: String): NestDataEntryProperty {
        TODO("Not yet implemented")
    }

    override suspend fun deleteNestEntryByID(id: Int, tokenKey: String): NestDataEntryProperty {
        TODO("Not yet implemented")
    }

    override suspend fun addNewNestData(body: NestDataEntryProperty, tokenKey: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateNestData(id: Int, body: NestDataEntryProperty, tokenKey: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllMorningSurveys(tokenKey: String): List<MorningSurveyPropertyID> {
        TODO("Not yet implemented")
    }

    override suspend fun getMorningSurveysByID(id: Int, tokenKey: String): MorningSurveyProperty {
        TODO("Not yet implemented")
    }

    override suspend fun addNewMorningSurvey(body: MorningSurveyProperty, tokenKey: String): MorningSurveyPropertyID {
        TODO("Not yet implemented")
    }

    override suspend fun updateMorningSurvey(
        id: Int,
        body: MorningSurveyProperty,
        tokenKey: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMorningSurvey(id: Int, tokenKey: String) {
        TODO("Not yet implemented")
    }
}