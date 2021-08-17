package archelon.login.activity

import android.util.Log
import archelon.dataModels.REST.LoginApiToken
import archelon.dataModels.REST.UserProperty
import archelon.restAPI.ArchelonLoginService


class LoginRepository(private val restAPIService: ArchelonLoginService) {

    suspend fun checkUserDetails(username:String, password:String): LoginApiToken? {
        val loginData = UserProperty(username, password)

        return try {
            restAPIService.checkLoginDetails(loginData)
        } catch (e: Exception) {
            Log.i("test", e.toString())
            null
        }


    }

}
