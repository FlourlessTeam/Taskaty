package com.example.taskaty.app.auth.signup

interface SignupContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showErrorMessage(message: String)
        fun navigateToLoginScreen(user: String)
        fun showValidationError(message: String)
    }
    interface Presenter {
        fun onSignup(userName: String, password: String, confirmPassword:String)
    }
}