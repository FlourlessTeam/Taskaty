package com.example.taskaty.data.api

import com.example.taskaty.BuildConfig
import com.example.taskaty.domain.entities.User
import okhttp3.Callback
import okhttp3.Credentials
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class UserApiClient(private val okHttpClient: OkHttpClient) {
    private val baseURl = BuildConfig.BASE_URL

    fun login(userName: String, password: String, callback: Callback) {
        val base64Credentials = Credentials.basic(userName, password)
        val request = Request.Builder()
            .url("$baseURl/login")
            .header("auth", base64Credentials)
            .build()

       okHttpClient.newCall(request).enqueue(callback)

    }


    fun signup(user: User,callback: Callback) {
        val requestBody = user.toJson().toRequestBody("application/json".toMediaTypeOrNull())
        val request = Request.Builder()
            .url("$baseURl/signup")
            .post(requestBody)
            .build()

         okHttpClient.newCall(request).enqueue(callback)

    }
//
//    private fun handleSignupResponse(response: Response): SignupResponse {
//        return if (response.isSuccessful) {
//            val jsonResponseBody = JSONObject(response.body.toString())
//            SignupResponse(jsonResponseBody)
//
//        } else
//            SignupResponse(false, "internet connection error")
//
//
//    }
//
//    private fun handleLoginResponse(response: Response): LoginResponse {
//        return if (response.isSuccessful) {
//            val jsonResponseBody = JSONObject(response.body.toString())
//            LoginResponse(jsonResponseBody)
//
//        } else
//            LoginResponse(false, "internet connection error", null)
//
//    }
}