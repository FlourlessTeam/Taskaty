package com.example.taskaty.app.auth.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.taskaty.databinding.FragmentSignupBinding
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.local.LocalAuthRepository
import com.example.taskaty.data.repositories.remote.RemoteAuthRepository
import com.example.taskaty.domain.entities.User
import com.example.taskaty.domain.interactors.AuthInteractor


class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate),
    SignupContract.View {
    private lateinit var presenter: SignupContract.Presenter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        presenter = SignupPresenter(
            AuthInteractor(
                LocalAuthRepository.getInstance(requireContext()),
                RemoteAuthRepository.getInstance()
            ), this
        )
        setup()
        return binding.root
    }

    private fun setup() {
        binding.buttonSignup.setOnClickListener {
            binding.apply {
                if (editTextPassword.text.toString() != editTextConfirmPassword.text.toString()) {
                    showErrorMessage("Password and Confirm Password must be the same")
                } else {
                    presenter.signup(
                        User(
                            editTextUsername.text.toString(),
                            editTextPassword.text.toString()
                        )
                    )
                }
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
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun successSignup(user: User) {
        Toast.makeText(requireContext(), "Hi, ${user.name}!", Toast.LENGTH_LONG).show()
    }

}