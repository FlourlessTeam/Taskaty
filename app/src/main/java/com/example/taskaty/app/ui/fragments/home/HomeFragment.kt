package com.example.taskaty.app.ui.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taskaty.R
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.home.adapters.HomePagerAdapter
import com.example.taskaty.app.ui.fragments.home.bottomSheet.NewTaskBottomSheetFragment
import com.example.taskaty.data.repositories.AllTasksRepositoryImpl
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentHomeBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.SizeInteractor
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val taskSize = SizeInteractor(AllTasksRepositoryImpl.getInstance())
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        taskSize.sizeTasks(object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                requireActivity().runOnUiThread {
                    binding.ActiveTasksCount.text = response.data.size.toString().plus(" Active Tasks")
                }
            }
            override fun onError(response: RepoResponse.Error<List<Task>>) {
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
            }
        })
        binding.searchViewHome.setOnClickListener {
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
        val bottomSheet =
            NewTaskBottomSheetFragment.newInstance(binding.tabLayout.selectedTabPosition)
        bottomSheet.show(childFragmentManager, "tag")
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}