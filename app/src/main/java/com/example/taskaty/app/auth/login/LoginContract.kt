package com.example.taskaty.app.auth.login

interface LoginContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showErrorMessage(message: String)
        fun successLogin()
    }
    interface Presenter {
        fun login(userName: String, password: String)
    }
}