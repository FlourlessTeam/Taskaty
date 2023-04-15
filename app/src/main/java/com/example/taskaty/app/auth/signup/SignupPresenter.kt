package com.example.taskaty.app.auth.signup

import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.User
import com.example.taskaty.domain.interactors.AuthInteractor

class SignupPresenter(private val authInteractor: AuthInteractor,private val view: SignupContract.View) : SignupContract.Presenter {
    override fun signup(uesr: User) {
        view.showLoading()
        authInteractor.signup(uesr,object : com.example.taskaty.data.response.RepoCallback<User>{
            override fun onSuccess(response: RepoResponse.Success<User>) {
                view.hideLoading()
                view.successSignup(response.data)
            }

            override fun onError(response:RepoResponse.Error<User>) {
                view.hideLoading()
                view.showErrorMessage(response.message)
            }

        })
    }


}
