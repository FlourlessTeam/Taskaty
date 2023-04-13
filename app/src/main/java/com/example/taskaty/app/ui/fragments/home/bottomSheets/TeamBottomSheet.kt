package com.example.taskaty.app.ui.fragments.home.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskaty.databinding.FragmentAddTaskBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TeamBottomSheet() : BottomSheetDialogFragment() {

    private var binding: FragmentAddTaskBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBottomSheetBinding.inflate(inflater, container, false)

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}