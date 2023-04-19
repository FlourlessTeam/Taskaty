package com.example.taskaty.app.ui.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.taskaty.R
import com.example.taskaty.app.ui.fragments.auth.login.LoginFragment
import com.example.taskaty.app.ui.fragments.auth.signup.SignupFragment
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.home.adapters.HomePagerAdapter
import com.example.taskaty.app.ui.fragments.home.bottomSheet.NewTaskBottomSheetFragment
import com.example.taskaty.app.ui.fragments.home.personal.PersonalTasksFragment
import com.example.taskaty.app.ui.fragments.home.team.TeamTasksFragment
import com.example.taskaty.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val fragmentList by lazy {
        listOf(
            PersonalTasksFragment(), TeamTasksFragment()
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        binding.searchViewHome.setOnQueryTextFocusChangeListener { _, _ ->
            replaceFragment(SearchFragment())

        }
        binding.viewPager.adapter = HomePagerAdapter(this)
        binding.viewPager.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.personal)
                else -> getString(R.string.team)
            }
        }.attach()

        binding.newTaskFAB.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = NewTaskBottomSheetFragment(binding.tabLayout.selectedTabPosition)
        bottomSheet.show(childFragmentManager, "tag")
    }
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}