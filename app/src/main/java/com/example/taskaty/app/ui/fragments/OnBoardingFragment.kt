package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.taskaty.R
import com.example.taskaty.app.auth.signup.SignupFragment
import com.example.taskaty.databinding.FragmentOnboardingBinding
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment


class OnBoardingFragment :
    BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.primary_color)
        setup()
    }

    private fun setup() {
        with(binding) {
            buttonStart.setOnClickListener {
                navigateToSignupScreen()
            }
        }
    }

    private fun navigateToSignupScreen() {
        val signupFragment = SignupFragment()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, signupFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}