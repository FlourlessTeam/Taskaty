package com.example.taskaty.domain.repositories.local

interface LocalAuthDataSource {
    fun getToken(): String

    fun getExpireAt(): Long
    fun updateToken(token: String, expireAt:String)
}