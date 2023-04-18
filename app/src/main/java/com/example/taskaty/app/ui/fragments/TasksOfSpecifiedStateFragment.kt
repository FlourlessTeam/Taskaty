package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskaty.databinding.FragmentTasksOfSpecifiedStateBinding
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment


class TasksOfSpecifiedStateFragment :
    BaseFragment<FragmentTasksOfSpecifiedStateBinding>(FragmentTasksOfSpecifiedStateBinding::inflate) {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}