package com.example.taskaty.app.auth.signup

import com.example.taskaty.domain.entities.User

interface SignupContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showErrorMessage(message: String)
        fun successSignup(user: User)
    }
    interface Presenter {
        fun signup(uesr: User)
    }
}