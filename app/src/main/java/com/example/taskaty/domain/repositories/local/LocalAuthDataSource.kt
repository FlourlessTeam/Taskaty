package com.example.taskaty.domain.repositories.local

interface LocalAuthDataSource {

    fun getToken(): String

    fun getExpireAt(): String

    fun updateToken(token: String, expireAt: String)
}