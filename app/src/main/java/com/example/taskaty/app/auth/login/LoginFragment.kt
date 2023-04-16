package com.example.taskaty.app.auth.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.taskaty.databinding.FragmentLoginBinding
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.local.LocalAuthRepository
import com.example.taskaty.data.repositories.remote.RemoteAuthRepository
import com.example.taskaty.domain.interactors.AuthInteractor


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) , LoginContract.View{
    private lateinit var presenter: LoginContract.Presenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = LoginPresenter(AuthInteractor(LocalAuthRepository.getInstance(requireContext()),RemoteAuthRepository.getInstance()),this)
        setup()
    }

    private fun setup() {
        binding.buttonLogin.setOnClickListener {
            binding.apply {
                presenter.onLogin(editTextUsername.text.toString(),editTextPassword.text.toString())
            }
        }
    }

    override fun showLoading() {
        Log.d("TAG", "showLoading: ")
    }

    override fun hideLoading() {
       Log.d("TAG", "hideLoading: ")
    }

    override fun showErrorMessage(message: String) {
        showToast(message)
    }

    override fun navigateToHomeScreen() {
        showToast("Hi, ${binding.editTextUsername.text}")
    }

    override fun showValidationError(message: String) {
        showToast(message)
    }
    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
    }

}