package com.example.taskaty.domain.repositories.remote

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.domain.entities.LoginResponse
import com.example.taskaty.domain.entities.SignupResponse
import com.example.taskaty.domain.entities.User

interface RemoteAuthDataSource {
    fun login(
        userName: String,
        password: String,
        callback: RepoCallback<LoginResponse>
    )
  fun signup(user:User,callback: RepoCallback<SignupResponse>)
}