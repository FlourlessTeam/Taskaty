package com.example.taskaty.domain.interactors


import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.User
import com.example.taskaty.domain.repositories.local.LocalAuthDataSource
import com.example.taskaty.domain.repositories.remote.RemoteAuthDataSource


class AuthInteractor(
    private val localAuthDataSource: LocalAuthDataSource,
    private val remoteAuthDataSource: RemoteAuthDataSource
) {

    fun login(userName: String, password: String, callback: RepoCallback<String>) {
        val token = getToken()
        if (token.isNotEmpty()) {
            callback.onSuccess(RepoResponse.Success(token))
        } else {
            getTokenFromRemote(userName, password, callback)
        }
    }

    fun signup(user: User, callback: RepoCallback<User>) {
        remoteAuthDataSource.signup(user, object : RepoCallback<User> {
            override fun onSuccess(response: RepoResponse.Success<User>) {
                callback.onSuccess(response)
            }

            override fun onError(response: RepoResponse.Error<User>) {
                callback.onError(response)
            }

        })
    }

    private fun getToken(): String {
        return localAuthDataSource.getToken() ?: ""
    }

    private fun getTokenFromRemote(
        userName: String,
        password: String,
        callback: RepoCallback<String>,
    ) {
        remoteAuthDataSource.fetchTokenByLogin(userName, password, object : RepoCallback<String> {
            override fun onSuccess(response: RepoResponse.Success<String>) {
                val token = response.data
                localAuthDataSource.updateToken(token)
                callback.onSuccess(RepoResponse.Success(token))
            }

            override fun onError(response: RepoResponse.Error<String>) {
                callback.onError(response)
            }
        })
    }

}


