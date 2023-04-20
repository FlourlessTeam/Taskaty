package com.example.taskaty.app.ui.fragments.home

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taskaty.R
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.details.personal.TaskDetailsFragment
import com.example.taskaty.app.ui.fragments.home.adapters.SearchAdapter
import com.example.taskaty.app.ui.fragments.home.adapters.SearchListener
import com.example.taskaty.data.repositories.AllTasksRepositoryImpl
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentSearchBinding
import com.example.taskaty.domain.entities.PersonalTask
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.SearchInteractor

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),SearchListener {
    private val adapter = SearchAdapter(this)
     private val searchData = SearchInteractor(AllTasksRepositoryImpl.getInstance())
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewSearch.adapter = adapter
        setup()
    }

    private fun setup() {
        with(binding) {
        binding.backButton.setOnClickListener{
            replaceFragment(HomeFragment())
        }
            searchViewResult.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    searchData.searchTasks(newText ?: "", object : RepoCallback<List<PersonalTask>> {
                        override fun onSuccess(response: RepoResponse.Success<List<PersonalTask>>) {
                            requireActivity().runOnUiThread {
                                adapter.submitList(response.data)
                            }
                        }
                        override fun onError(response: RepoResponse.Error<List<PersonalTask>>) {
                            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    })
                    return true
                }
            })
        }
    }

   override fun onClick(task: Task) {
        replaceFragment(TaskDetailsFragment.getInstance(task.id))
          }
    fun replaceFragment(fragment: Fragment) {
        val fragmentManager = requireActivity().supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}