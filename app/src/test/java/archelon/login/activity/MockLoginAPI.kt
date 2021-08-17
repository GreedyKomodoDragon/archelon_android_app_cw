package archelon.login.activity

import archelon.dataModels.REST.LoginApiToken
import archelon.dataModels.REST.UserProperty
import archelon.restAPI.ArchelonLoginService

class MockLoginAPI : ArchelonLoginService {
    override suspend fun checkLoginDetails(body: UserProperty): LoginApiToken {
        return LoginApiToken("testToken")
    }
}