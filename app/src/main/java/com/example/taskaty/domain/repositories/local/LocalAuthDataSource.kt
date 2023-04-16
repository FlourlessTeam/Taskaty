package com.example.taskaty.domain.repositories.local

interface LocalAuthDataSource {
    fun getToken(): String
    fun updateToken(token: String)
}