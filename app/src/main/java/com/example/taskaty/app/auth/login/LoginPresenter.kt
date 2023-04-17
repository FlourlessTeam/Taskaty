package com.example.taskaty.app.auth.login

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.interactors.AuthInteractor

class LoginPresenter(
    private val authInteractor: AuthInteractor,
    private val view: LoginContract.View
) : LoginContract.Presenter {

    override fun onLogin(userName: String, password: String) {
        if (!authInteractor.checkValidField(userName, password)){
            view.showValidationError("Please fill all fields")
            return
        }
        view.showLoading()
        authInteractor.login(userName, password, object : RepoCallback<String> {
            override fun onSuccess(response: RepoResponse.Success<String>) {
                view.hideLoading()
                view.navigateToHomeScreen()
            }

            override fun onError(response: RepoResponse.Error<String>) {
                view.hideLoading()
                view.showErrorMessage(response.message)
            }

        })
    }

    override fun onLoginWithSaveToken() {
        val token = authInteractor.getTokenFromLocal()
        if (token.isNotEmpty() && authInteractor.checkExpireAt()) {
            view.navigateToHomeScreen()
        } else {
            view.showValidationError("Token not found. Please login again.")
        }
    }
}
