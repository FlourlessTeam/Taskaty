package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.taskaty.R
import com.example.taskaty.app.auth.login.LoginContract
import com.example.taskaty.app.auth.login.LoginFragment
import com.example.taskaty.app.auth.signup.SignupFragment
import com.example.taskaty.databinding.FragmentOnboardingBinding
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.local.LocalAuthRepository
import com.example.taskaty.data.repositories.remote.RemoteAuthRepository
import com.example.taskaty.domain.interactors.AuthInteractor


class OnBoardingFragment :
    BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.primary_color)
        setup()
    }

    private fun setup() {
        with(binding) {
            buttonStart.setOnClickListener {
                navigateToLoginScreen()
            }
        }
    }


    private fun navigateToLoginScreen() {
        val loginFragment = LoginFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, loginFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}