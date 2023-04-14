package com.example.taskaty.domain.repositories

interface AuthDataSource {
    fun getToken(): String?
    fun updateToken(token: String)
}