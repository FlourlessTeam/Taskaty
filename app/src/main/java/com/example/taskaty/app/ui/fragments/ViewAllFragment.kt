package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskaty.adapters.ViewAllAdapter
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.CardItemsInViewAll
import com.example.taskaty.databinding.FragmentSignupBinding
import com.example.taskaty.databinding.FragmentTeamTasksBinding
import com.example.taskaty.databinding.FragmentViewAllBinding

class ViewAllFragment:  BaseFragment<FragmentViewAllBinding>(FragmentViewAllBinding::inflate){

      override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
          setUp()

        }

    private fun setUp(){

        //val adapter=ViewAllAdapter()
        //binding.recyclerViewInViewAll.adapter=adapter

    }

    

}