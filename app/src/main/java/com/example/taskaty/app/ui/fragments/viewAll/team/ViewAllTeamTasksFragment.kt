package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.taskaty.app.adapters.ViewAllTeamTasksAdapter
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.viewAll.team.ViewAllTeamTasksContract
import com.example.taskaty.app.ui.fragments.viewAll.team.ViewAllTeamTasksPresenter
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse

import com.example.taskaty.databinding.FragmentViewAllTeamTasksBinding
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.interactors.TeamTaskInteractor
import com.example.taskaty.domain.repositories.remote.TeamTasksDataSource

class ViewAllTeamTasksFragment : BaseFragment<FragmentViewAllTeamTasksBinding>
    (FragmentViewAllTeamTasksBinding::inflate), ViewAllTeamTasksContract.View {
    private lateinit var presenter: ViewAllTeamTasksContract.Presenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ViewAllTeamTasksPresenter(
            TeamTaskInteractor(RemoteTasksRepository.getInstance()), this
        )

        setup()

    }

    private fun setup() {
        presenter.getTeamTaskData()
    }

    private fun getStatusNames(status: Int?): String {
        return when (status) {
            0 -> "ToDo"
            1 -> "In Progress"
            else -> "Done"
        }
    }

    override fun viewAllTeamTasksStatus(teamTasks: List<TeamTask>) {
        val status = arguments?.getInt("key")
        requireActivity().runOnUiThread {
            val adapter = ViewAllTeamTasksAdapter()
            adapter.submitList(teamTasks.filter { it.status == status })
            binding.toolbar.title = getStatusNames(status)
            binding.recyclerViewInViewAll.adapter = adapter
        }
    }


}



