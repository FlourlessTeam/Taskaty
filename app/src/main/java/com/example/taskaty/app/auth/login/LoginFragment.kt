package com.example.taskaty.app.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.taskaty.databinding.FragmentLoginBinding
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.local.LocalAuthRepository
import com.example.taskaty.data.repositories.remote.RemoteAuthRepository
import com.example.taskaty.domain.interactors.AuthInteractor


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) , LoginContract.View{
    private lateinit var presenter: LoginContract.Presenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        presenter = LoginPresenter(AuthInteractor(LocalAuthRepository.getInstance(requireContext()),RemoteAuthRepository.getInstance()),this)
        setup()
        return binding.root
    }

    private fun setup() {
        binding.buttonLogin.setOnClickListener {
            binding.apply {
                presenter.login(editTextUsername.text.toString(),editTextPassword.text.toString())
            }
        }
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showErrorMessage(message: String) {
        Toast.makeText(requireContext(),message, Toast.LENGTH_LONG).show()
    }

    override fun successLogin() {
        TODO("Not yet implemented")
    }

}