package com.example.taskaty.app.ui.fragments.home.bottomSheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskaty.databinding.FragmentPersonalBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PersonalBottomSheet() : BottomSheetDialogFragment() {

    private var binding: FragmentPersonalBottomSheetBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPersonalBottomSheetBinding.inflate(inflater,container,false)

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