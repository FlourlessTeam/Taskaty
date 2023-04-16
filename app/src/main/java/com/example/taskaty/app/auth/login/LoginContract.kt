package com.example.taskaty.app.auth.login

interface LoginContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showErrorMessage(message: String)
        fun navigateToHomeScreen()
        fun showValidationError(message: String)
    }
    interface Presenter {
        fun onLogin(userName: String, password: String)
        fun onLoginWithSaveToken()
    }
}