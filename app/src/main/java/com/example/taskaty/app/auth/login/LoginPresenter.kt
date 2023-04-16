package com.example.taskaty.app.auth.login

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.interactors.AuthInteractor

class LoginPresenter(
    private val authInteractor: AuthInteractor,
    private val view: LoginContract.View
) : LoginContract.Presenter {

    override fun onLogin(userName: String, password: String) {
        if (!authInteractor.checkValidField(userName, password))
            return view.showValidationError("Please fill all fields")
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

    override fun onLoginWithSaveToken(userName: String, password: String) {
        if (!authInteractor.checkValidField(userName, password))
            return view.showValidationError("Please fill all fields")
        view.showLoading()
        if (authInteractor.getTokenFromLocal().isNotEmpty())
            return view.navigateToHomeScreen()
    }
}
