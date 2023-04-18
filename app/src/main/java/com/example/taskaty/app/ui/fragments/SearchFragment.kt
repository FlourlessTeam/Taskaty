package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.example.taskaty.R
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.details.personal.TaskDetailsFragment
import com.example.taskaty.app.ui.fragments.home.HomeFragment
import com.example.taskaty.app.ui.fragments.home.adapters.SearchAdapter
import com.example.taskaty.app.ui.fragments.home.adapters.SearchListener
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentSearchBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.SearchInteractor

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate),SearchListener {
    private val adapter = SearchAdapter(this)
     private val searchData = SearchInteractor(RemoteTasksRepository.getInstance())
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewSearch.adapter = adapter
        setup()
    }

    private fun setup() {
        with(binding) {
        binding.backButton.setOnClickListener{
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container_fragment, HomeFragment())
                .addToBackStack(null)
                .commit()
        }
            searchViewResult.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    searchData.searchTasks(newText ?: "", object : RepoCallback<List<Task>> {
                        override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                            requireActivity().runOnUiThread {
                                adapter.submitList(response.data)
                            }
                        }
                        override fun onError(response: RepoResponse.Error<List<Task>>) {
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
         requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container_fragment, TaskDetailsFragment.getInstance(task.id))
            .addToBackStack(null)
            .commit()
          }



}