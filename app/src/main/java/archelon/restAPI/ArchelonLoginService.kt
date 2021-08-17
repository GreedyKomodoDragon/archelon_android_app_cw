package archelon.restAPI

import archelon.dataModels.REST.LoginApiToken
import archelon.dataModels.REST.UserProperty
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

private const val BASE_URL = "https://archaelon.roussos.mobi:443/"

interface ArchelonLoginService {

    //User Related
    @POST("login/")
    suspend fun checkLoginDetails(@Body body: UserProperty) : LoginApiToken


}



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()


object ArchelonApiLogin {
    val retrofitServiceLogin : ArchelonLoginService by lazy {
        retrofit.create(ArchelonLoginService::class.java) }
}
