package archelon.restAPI

import archelon.dataModels.REST.MorningSurveyProperty
import archelon.dataModels.REST.MorningSurveyPropertyID
import archelon.dataModels.REST.NestDataEntryProperty
import archelon.dataModels.REST.NestProperty
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://archaelon.roussos.mobi:443/api/archelon/"


//Create REST API Structure
interface ArchelonAPIService {

    //Nest Related REST
    @GET("nest_list")
    suspend fun getAllNests(@Header("Authorization") tokenKey: String): List<NestProperty>

    @GET("nest_list/{id}")
    suspend fun getNestByID(@Path("id") id: Int, @Header("Authorization") tokenKey: String): NestProperty

    @DELETE("nest_list/{id}")
    suspend fun deleteNestByID(@Path("id") id: Int, @Header("Authorization") tokenKey: String): NestProperty

    @POST("nest_list")
    suspend fun addNewNestProperty(
        @Body body: NestProperty,
        @Header("Authorization") tokenKey: String
    ) //TODO find out what is returned

    @PUT("nest_list/{id}")
    suspend fun updateNestList(
        @Path("id") id: Int,
        @Body body: NestProperty,
        @Header("Authorization") tokenKey: String
    )


    //Nest-data Related REST
    @GET("nest_data")
    suspend fun getAllNestEntries(@Header("Authorization") tokenKey: String): List<NestDataEntryProperty>

    @GET("nest_data/{id}")
    suspend fun getNestEntryByID(@Path("id") id: Int, @Header("Authorization") tokenKey: String): NestDataEntryProperty

    @DELETE("nest_data/{id}")
    suspend fun deleteNestEntryByID(@Path("id") id: Int, @Header("Authorization") tokenKey: String): NestDataEntryProperty

    @POST("nest_data")
    suspend fun addNewNestData(
        @Body body: NestDataEntryProperty,
        @Header("Authorization") tokenKey: String
    )

    @PUT("nest_data/{id}")
    suspend fun updateNestData(
        @Path("id") id: Int,
        @Body body: NestDataEntryProperty,
        @Header("Authorization") tokenKey: String
    )


    //Morning Survey Related REST
    @GET("morning_survey")
    suspend fun getAllMorningSurveys(@Header("Authorization") tokenKey: String): List<MorningSurveyPropertyID>

    @GET("morning_survey/{id}")
    suspend fun getMorningSurveysByID(
        @Path("id") id: Int,
        @Header("Authorization") tokenKey: String
    ): MorningSurveyProperty

    @POST("morning_survey")
    suspend fun addNewMorningSurvey(
        @Body body: MorningSurveyProperty,
        @Header("Authorization") tokenKey: String
    ) : MorningSurveyPropertyID

    @PUT("morning_survey/{id}")
    suspend fun updateMorningSurvey(
        @Path("id") id: Int,
        @Body body: MorningSurveyProperty,
        @Header("Authorization") tokenKey: String
    )

    @DELETE("morning_survey/{id}")
    suspend fun deleteMorningSurvey(@Path("id") id: Int, @Header("Authorization") tokenKey: String)

}

private var certificatePinnerMobi = CertificatePinner.Builder()
    .add("archaelon.roussos.mobi",
        "sha256/jY3QNNI3HnQJP0oGelQOms6TDbJgJvP58pYYtEmqeRo=",
        "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0-")
    .build()

val interceptor = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply { httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY }
}
val client = OkHttpClient.Builder()
    .certificatePinner(certificatePinnerMobi)
    .addInterceptor(interceptor).build()


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()


object ArchelonApi {
    val retrofitService : ArchelonAPIService by lazy {
        retrofit.create(ArchelonAPIService::class.java) }
}
