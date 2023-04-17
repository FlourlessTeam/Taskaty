package com.example.taskaty.data.repositories.local
import android.content.Context
import com.example.taskaty.domain.repositories.local.LocalAuthDataSource
import java.text.SimpleDateFormat
import java.util.Locale

class LocalAuthRepository private constructor(private val application: Context) :
    LocalAuthDataSource {
    private var token: String? = null
    private var expireAt: Long? = null
    override fun getToken(): String {
        if (token == null) {
            val sharedPreferences = application.getSharedPreferences("auth", Context.MODE_PRIVATE)
            token = sharedPreferences.getString("token", "")
        }
        return token!!
    }

    override fun getExpireAt(): Long {
        if (expireAt == null) {
            val sharedPreferences = application.getSharedPreferences("auth", Context.MODE_PRIVATE)
            expireAt = sharedPreferences.getLong("expireAt", 0)
        }
        return expireAt!!
    }


    override fun updateToken(token: String, expireAt: String) {
        val sharedPreferences = application.getSharedPreferences("auth", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("token", token).apply()
        val exp = convertExpireAt(expireAt)
        sharedPreferences.edit().putLong("expireAt", exp).apply()
        this.token = sharedPreferences.getString("token", "")!!
        this.expireAt = sharedPreferences.getLong("expireAt", 0)
    }

    private fun convertExpireAt(expireAt: String): Long {
        if(expireAt.isEmpty())
            return 0
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
        val expireAtDate = dateFormat.parse(expireAt)
        return expireAtDate.time
    }

    companion object {
        private var instance: LocalAuthRepository? = null

        fun getInstance(application: Context): LocalAuthRepository {
            if (instance == null) {
                instance = LocalAuthRepository(application)
            }
            return instance!!
        }
    }
}