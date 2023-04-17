package com.example.taskaty.data.repositories.remote

import com.example.taskaty.data.api.UserApiClient
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.LoginResponse
import com.example.taskaty.domain.entities.SignupResponse
import com.example.taskaty.domain.entities.User
import com.example.taskaty.domain.repositories.remote.RemoteAuthDataSource
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException

class RemoteAuthRepository private constructor() : RemoteAuthDataSource {


    override fun fetchTokenByLogin(
        userName: String,
        password: String,
        callback: RepoCallback<LoginResponse>
    ) {
        val userClient = UserApiClient(OkHttpClient())
        val apiCallBack = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }
            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(RepoResponse.Success(extractTokenFromResponse(response.body.string())))
            }
        }
        userClient.login(userName, password, apiCallBack)
    }

    override fun signup(user: User, callback: RepoCallback<SignupResponse>) {
        val userClient = UserApiClient(OkHttpClient())
        val apiCallBack = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(RepoResponse.Success(jsonSignupParser(response.body.string())))
            }
        }
        userClient.signup(user, apiCallBack)
    }

    private fun extractTokenFromResponse(jsonString: String): LoginResponse {
        val gson = Gson()
        val jsonObject = gson.fromJson(jsonString, LoginResponse::class.java)
        return jsonObject
    }
    private fun jsonSignupParser(jsonString: String): SignupResponse {
        val gson = Gson()
        val jsonObject = gson.fromJson(jsonString, SignupResponse::class.java)
        return jsonObject
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