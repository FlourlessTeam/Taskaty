package com.example.taskaty.data.repositories.local

import android.content.Context
import com.example.taskaty.domain.repositories.local.LocalAuthDataSource
import com.example.taskaty.global.GlobalState

class LocalAuthRepository private constructor(private val application: Context) :
    LocalAuthDataSource {
    private var token: String? = null
    private var expireAt: String? = null
    override fun getToken(): String {
        if (token == null) {
            val sharedPreferences = application.getSharedPreferences("auth", Context.MODE_PRIVATE)
            token = sharedPreferences.getString("token", "")
        }
        return token!!
    }
    override fun getExpireAt(): String {
        if (expireAt == null) {
            val sharedPreferences = application.getSharedPreferences("auth", Context.MODE_PRIVATE)
            expireAt = sharedPreferences.getString("expireAt", "")
        }
        return expireAt!!
    }
    override fun updateToken(token: String, expireAt: String) {
        val sharedPreferences = application.getSharedPreferences("auth", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("token", token).apply()
        sharedPreferences.edit().putString("expireAt", expireAt).apply()
        this.token = sharedPreferences.getString("token", "")
        this.expireAt = sharedPreferences.getString("expireAt", "")
    }
    companion object {
        private var instance: LocalAuthRepository? = null

        fun getInstance(application: Context = GlobalState.appContext): LocalAuthRepository {
            if (instance == null) {
                instance = LocalAuthRepository(application)
            }
            return instance!!
        }
    }
}