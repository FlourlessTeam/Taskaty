package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.taskaty.R
import com.example.taskaty.databinding.FragmentOnboardingBinding
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment


class OnBoardingFragment :
    BaseFragment<FragmentOnboardingBinding>(FragmentOnboardingBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.primary_color)

    }


}