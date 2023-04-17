package com.example.taskaty.app.auth.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.taskaty.R
import com.example.taskaty.app.auth.login.LoginFragment
import com.example.taskaty.databinding.FragmentSignupBinding
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.local.LocalAuthRepository
import com.example.taskaty.data.repositories.remote.RemoteAuthRepository
import com.example.taskaty.domain.interactors.AuthInteractor


class SignupFragment : BaseFragment<FragmentSignupBinding>(FragmentSignupBinding::inflate),
    SignupContract.View {
    private lateinit var presenter: SignupContract.Presenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = SignupPresenter(
            AuthInteractor(
                LocalAuthRepository.getInstance(requireContext().applicationContext),
                RemoteAuthRepository.getInstance()
            ), this
        )
        setup()
    }

    private fun setup() {
        with(binding) {
            buttonSignup.setOnClickListener {
                presenter.onSignup(
                    editTextUsername.text.toString(),
                    editTextPassword.text.toString(),
                    editTextConfirmPassword.text.toString()
                )
            }
            textGoLogin.setOnClickListener {
                navigateToLoginScreen()
            }
        }
    }

    override fun showLoading() {
        activity?.runOnUiThread {
            setVisibility(true)
            binding.buttonSignup.text = ""
        }
    }

    override fun hideLoading() {
        activity?.runOnUiThread {
            setVisibility(false)
            binding.buttonSignup.text = getString(R.string.text_signup)
        }
    }

    override fun showErrorMessage(message: String) {
        showToast(message)
    }

    override fun navigateToLoginScreen(user: String) {
        showToast("Hi, ${user}!")
        navigateToLoginScreen()
    }

    override fun showValidationError(message: String) {
        showToast(message)
    }

    private fun navigateToLoginScreen() {
        val loginFragment = LoginFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, loginFragment)
        transaction.commit()
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setVisibility(isVisible: Boolean) {
        with(binding) {
            progressBar.isVisible = isVisible
            buttonSignup.isEnabled = !isVisible
        }
    }

}