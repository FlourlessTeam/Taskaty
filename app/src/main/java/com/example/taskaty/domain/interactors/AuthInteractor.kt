package com.example.taskaty.domain.interactors

import android.app.Application
import com.example.taskaty.domain.repositories.AuthDataSource
import com.example.taskaty.domain.repositories.RemoteAuthDataSource

class AuthInteractor(
    private val application: Application,
    private val localDataSource: AuthDataSource,
    private val remoteAuthDataSource: RemoteAuthDataSource
) {

}