package com.example.taskaty.domain.interactors


import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.LoginResponse
import com.example.taskaty.domain.entities.SignupResponse
import com.example.taskaty.domain.entities.User
import com.example.taskaty.domain.repositories.local.LocalAuthDataSource
import com.example.taskaty.domain.repositories.remote.RemoteAuthDataSource


class AuthInteractor(
    private val localAuthDataSource: LocalAuthDataSource,
    private val remoteAuthDataSource: RemoteAuthDataSource
) {

    fun login(userName: String, password: String, callback: RepoCallback<String>) {
            getTokenFromRemote(userName, password, callback)
    }

    fun signup(user: User, callback: RepoCallback<String>) {
        remoteAuthDataSource.signup(user, object : RepoCallback<SignupResponse> {
            override fun onSuccess(response: RepoResponse.Success<SignupResponse>) {
                if (response.data.isSuccess){
                    callback.onSuccess(RepoResponse.Success(response.data.value.username))
                }
                else{
                    callback.onError(RepoResponse.Error(response.data.message.toString()))
                }
            }

            override fun onError(response: RepoResponse.Error<SignupResponse>) {
                callback.onError(RepoResponse.Error(response.message))
            }


        })
    }
     fun getTokenFromLocal(): String {
        return localAuthDataSource.getToken()
    }

    private fun getTokenFromRemote(
        userName: String,
        password: String,
        callback: RepoCallback<String>,
    ) {
        remoteAuthDataSource.fetchTokenByLogin(userName, password, object : RepoCallback<LoginResponse> {

            override fun onSuccess(response: RepoResponse.Success<LoginResponse>) {
                if (response.data.isSuccess) {
                    val token = response.data.value.token
                    callback.onSuccess(RepoResponse.Success(token))
                    localAuthDataSource.updateToken(token)
                }
                else{
                    callback.onError(RepoResponse.Error(response.data.message.toString()))
                }
            }

            override fun onError(response: RepoResponse.Error<LoginResponse>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }
    fun checkValidField(userName: String, password: String,confirmPassword:String=password): Boolean {
        return userName.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
    }
    fun checkValidPassword(password: String,confirmPassword:String): Boolean {
        return password==confirmPassword
    }
}


