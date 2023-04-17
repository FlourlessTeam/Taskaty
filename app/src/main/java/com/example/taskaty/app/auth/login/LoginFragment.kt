package com.example.taskaty.app.auth.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.example.taskaty.R
import com.example.taskaty.app.auth.signup.SignupFragment
import com.example.taskaty.app.ui.fragments.ViewAllFragment
import com.example.taskaty.databinding.FragmentLoginBinding
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.local.LocalAuthRepository
import com.example.taskaty.data.repositories.remote.RemoteAuthRepository
import com.example.taskaty.domain.interactors.AuthInteractor


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    LoginContract.View {
    private lateinit var presenter: LoginContract.Presenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.background_color)
        presenter = LoginPresenter(
            AuthInteractor(
                LocalAuthRepository.getInstance(requireContext().applicationContext),
                RemoteAuthRepository.getInstance()
            ), this
        )
        setup()
    }

    private fun setup() {
        presenter.onLoginWithSaveToken()
        with(binding) {
            buttonLogin.setOnClickListener {
                presenter.onLogin(
                    editTextUsername.text.toString(),
                    editTextPassword.text.toString()
                )
            }
            textGoSignup.setOnClickListener {
                navigateToSignupScreen()
            }
        }
    }

    override fun showLoading() {
        activity?.runOnUiThread {
            setVisibility(true)
            binding.buttonLogin.text = ""
        }
    }

    override fun hideLoading() {
        activity?.runOnUiThread {
            setVisibility(false)
            binding.buttonLogin.text = getString(R.string.text_button_login)
        }
    }

    override fun showErrorMessage(message: String) {
        showToast(message)
    }

    override fun navigateToHomeScreen() {
        showToast("Login Success")
    }

    override fun showValidationError(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        activity?.runOnUiThread {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToSignupScreen() {
        val signupFragment = SignupFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, signupFragment)
        transaction.commit()
    }

    private fun setVisibility(isVisible: Boolean) {
        with(binding) {
            progressBar.isVisible = isVisible
            buttonLogin.isEnabled = !isVisible
        }
    }


}