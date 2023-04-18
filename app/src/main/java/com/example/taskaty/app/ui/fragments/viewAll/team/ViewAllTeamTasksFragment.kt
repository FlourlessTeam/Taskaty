package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.taskaty.app.adapters.ViewAllTeamTasksAdapter
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse

import com.example.taskaty.databinding.FragmentViewAllTeamTasksBinding
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.interactors.TeamTaskInteractor
import com.example.taskaty.domain.repositories.remote.TeamTasksDataSource

class ViewAllTeamTasksFragment: BaseFragment<FragmentViewAllTeamTasksBinding>
    (FragmentViewAllTeamTasksBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()

    }

    private fun setup() {
        //get the status number from the home screen to display the required list
        val status = arguments?.getInt("key")
        TeamTaskInteractor(RemoteTasksRepository.getInstance())
            .getTeamTaskData(object : RepoCallback<List<TeamTask>> {
                override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {

                    requireActivity().runOnUiThread {
                        val adapter = ViewAllTeamTasksAdapter()
                        adapter.submitList(response.data.filter { it.task.status == status })
                        binding.toolbar.title = getStatusNames(status)
                        binding.recyclerViewInViewAll.adapter = adapter
                    }
                }

                override fun onError(response: RepoResponse.Error<List<TeamTask>>) {

                }

            })
    }

    private fun getStatusNames(status: Int?): String {
        return when (status) {
            0 -> "ToDo"
            1 -> "In Progress"
            else -> "Done"
        }
    }
}