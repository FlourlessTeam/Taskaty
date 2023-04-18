package com.example.taskaty.data.repositories.remote

import com.example.taskaty.data.api.UserApiClient
import com.example.taskaty.data.mappers.AuthMappers
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.LoginResponse
import com.example.taskaty.domain.entities.SignupResponse
import com.example.taskaty.domain.entities.User
import com.example.taskaty.domain.repositories.remote.RemoteAuthDataSource
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException

class RemoteAuthRepository private constructor() : RemoteAuthDataSource {


    override fun login(
        userName: String,
        password: String,
        callback: RepoCallback<LoginResponse>
    ) {
        val userClient = UserApiClient(OkHttpClient())
        val apiCallBack = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error("No internet connection"))
            }
            override fun onResponse(call: Call, response: Response) {
                val loginResponse =
                    AuthMappers.mapJsonStringToLoginResponse(response.body.string())
                callback.onSuccess(RepoResponse.Success(loginResponse))
            }
        }
        userClient.login(userName, password, apiCallBack)
    }

    override fun signup(user: User, callback: RepoCallback<SignupResponse>) {
        val userClient = UserApiClient(OkHttpClient())
        val apiCallBack = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error("No internet connection"))
            }

            override fun onResponse(call: Call, response: Response) {
                val signupResponse =
                    AuthMappers.mapJsonStringToSignupResponse(response.body.string())
                callback.onSuccess(RepoResponse.Success(signupResponse))
            }
        }
        userClient.signup(user, apiCallBack)
    }


    companion object {
        private var instance: RemoteAuthRepository? = null

        fun getInstance(): RemoteAuthRepository {
            if (instance == null) {
                instance = RemoteAuthRepository()
            }
            return instance!!
        }
    }

}