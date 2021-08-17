package archelon.login.activity

import android.app.Application
import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.*
import archelon.restAPI.ArchelonApi
import com.decouikit.atom.R
import kotlinx.coroutines.launch

class LogViewModel(repository: LoginRepository, application: Application) : AndroidViewModel(application) {

    private val _application = application

    private var _username = MutableLiveData("")
    var username:LiveData<String> = _username

    private var _password = MutableLiveData("")
    var password : LiveData<String> = _password

    private var _response = MutableLiveData(false)
    var response:LiveData<Boolean> = _response

    private var repo = repository


    private var _isLoggingIn = MutableLiveData(false)
    var isLoggingIn : LiveData<Boolean> = _isLoggingIn

    private var _failedLogin = MutableLiveData(false)
    var failedLogin : LiveData<Boolean> = _failedLogin


    private fun isValidPassword(): Boolean{
        return _password.value!!.isNotEmpty()
    }

    private fun isValidUsername(): Boolean{
        return _username.value!!.isNotEmpty()
    }

    fun onUsernameChanged(
        s: CharSequence,
        @Suppress("UNUSED_PARAMETER")start: Int,
        @Suppress("UNUSED_PARAMETER")before: Int,
        @Suppress("UNUSED_PARAMETER")count: Int
    ) {
        viewModelScope.launch {
            if(s.toString().isNotEmpty()){
                _username.value = s.toString()
            }else{
                _username.value = ""
            }
        }
    }

    fun onPasswordChange(
        s: CharSequence,
        @Suppress("UNUSED_PARAMETER")start: Int,
        @Suppress("UNUSED_PARAMETER")before: Int,
        @Suppress("UNUSED_PARAMETER")count: Int
    ) {
        viewModelScope.launch {
            if(s.toString().isNotEmpty()){
                _password.value = s.toString()
            }else{
                _password.value = ""
            }
        }
    }

    //allows user go back using back button
    fun resetResponse(){
        viewModelScope.launch {
            _response.value = false
        }
    }

    fun login(@Suppress("UNUSED_PARAMETER")view: View) {
        viewModelScope.launch {
            _failedLogin.value = false

            if(isValidPassword() && isValidUsername()){
                _isLoggingIn.value = true
                //jchapm09
                //*RUSH=kind=late*

                val apiKey = repo.checkUserDetails(username.value!!, password.value!! )

                //check that login was valid
                if(apiKey != null){

                    //save api key
                    val sharedPref = _application.getSharedPreferences("archelon", MODE_PRIVATE);
                    val edit = sharedPref.edit()
                    edit.clear()
                    edit.putString("key", apiKey.key)
                    edit.apply()

                    //move to the next page
                    _isLoggingIn.value = false
                    _response.value = true


                }else{
                    _isLoggingIn.value = false
                    _failedLogin.value = true
                    //TODO show error message
                }

            }else{
                _failedLogin.value = true
            }
        }
    }
}