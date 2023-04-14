package com.example.taskaty.data.repositories.local

import android.app.Application
import android.content.Context
import com.example.taskaty.domain.repositories.local.LocalAuthDataSource

class LocalAuthRepository(private val application: Application) : LocalAuthDataSource {
    private var token: String? = null
    override fun getToken(): String {
        if (token == null) {
            val sharedPreferences = application.getSharedPreferences("auth", Context.MODE_PRIVATE)
            token = sharedPreferences.getString("token", "")
        }
        return token!!
    }

    override fun updateToken(token: String) {

        val sharedPreferences = application.getSharedPreferences("auth", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("token", token).apply()
    }

    companion object {
        private var instance: LocalAuthRepository? = null

        fun getInstance(application: Application): LocalAuthRepository {
            if (instance == null) {
                instance = LocalAuthRepository(application)
            }
            return instance!!
        }
    }
}