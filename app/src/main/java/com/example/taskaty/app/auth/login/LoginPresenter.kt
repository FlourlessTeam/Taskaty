package com.example.taskaty.app.auth.login

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.interactors.AuthInteractor

class LoginPresenter(private val authInteractor: AuthInteractor, private val view: LoginContract.View): LoginContract.Presenter {

    override fun login(userName: String, password: String) {
        view.showLoading()
        authInteractor.login(userName,password,object:RepoCallback<String>{
            override fun onSuccess(response: RepoResponse.Success<String>) {
                view.hideLoading()
                view.successLogin()
            }

            override fun onError(response: RepoResponse.Error<String>) {
                view.hideLoading()
                view.showErrorMessage(response.message)
            }

        })
    }
}
