package com.example.taskaty.domain.interactors

import android.app.Application
import com.example.taskaty.domain.repositories.local.LocalAuthDataSource
import com.example.taskaty.domain.repositories.remote.RemoteAuthDataSource

class AuthInteractor(
    private val localAuthDataSource: LocalAuthDataSource,
    private val remoteAuthDataSource: RemoteAuthDataSource
) {

}