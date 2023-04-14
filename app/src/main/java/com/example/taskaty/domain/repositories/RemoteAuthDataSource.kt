package com.example.taskaty.domain.repositories

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.domain.entities.User

interface RemoteAuthDataSource {
    fun fetchTokenByLogin(
        userName: String,
        password: String,
        callback: RepoCallback<String>
    )
  fun signup(user:User,callback: RepoCallback<User>)
}