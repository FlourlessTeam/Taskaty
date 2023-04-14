package com.example.taskaty.data.repositories

import com.example.taskaty.data.api.UserApiClient
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.User
import com.example.taskaty.domain.repositories.remote.RemoteAuthDataSource
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.IOException

class RemoteAuthRepository private constructor() : RemoteAuthDataSource {


    override fun fetchTokenByLogin(
        userName: String,
        password: String,
        callback: RepoCallback<String>
    ) {
        val userClient = UserApiClient(OkHttpClient())
        val apiCallBack = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(RepoResponse.Success(extractTokenFromResponse(response.body.toString())))
            }
        }
        userClient.login(userName, password, apiCallBack)
    }

    override fun signup(user: User, callback: RepoCallback<User>) {
        val userClient = UserApiClient(OkHttpClient())
        val apiCallBack = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(RepoResponse.Error(e.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                callback.onSuccess(RepoResponse.Success(user))
            }
        }
        userClient.signup(user, apiCallBack)
    }

    private fun extractTokenFromResponse(jsonString: String): String {
        val gson = Gson()
        val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
        return jsonObject.getAsJsonObject("value").get("token").asString
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