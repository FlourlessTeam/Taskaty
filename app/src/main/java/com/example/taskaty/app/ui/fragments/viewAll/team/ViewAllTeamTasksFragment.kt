package com.example.taskaty.app.ui.fragments.viewAll.team

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository

import com.example.taskaty.databinding.FragmentViewAllTeamTasksBinding
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.interactors.TeamTaskInteractor

class ViewAllTeamTasksFragment : BaseFragment<FragmentViewAllTeamTasksBinding>
    (FragmentViewAllTeamTasksBinding::inflate), ViewAllTeamTasksView {
    private lateinit var presenter: ViewAllTeamTasksPresenter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ViewAllTeamTasksPresenter(
            TeamTaskInteractor(RemoteTasksRepository.getInstance()), this
        )

        setup()

    }

    private fun setup() {
        val status = arguments?.getInt("key")
        presenter.getTeamTaskData(0)
    }

    private fun getStatusNames(status: Int?): String {
        return when (status) {
            0 -> "ToDo"
            1 -> "In Progress"
            else -> "Done"
        }
    }

    override fun showLoading() {
        requireActivity().runOnUiThread {
            binding.shimmerFrameLayout.startShimmer()
            binding.shimmerFrameLayout.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        requireActivity().runOnUiThread {
            binding.shimmerFrameLayout.stopShimmer()
            binding.shimmerFrameLayout.visibility = View.GONE
        }
    }

    override fun showErrorMessage(message: String) {
        Log.d("TAG", "showErrorMessage: $message")
    }

    override fun viewAllTeamTasksStatus(state:Int,teamTasks: List<TeamTask>) {
        requireActivity().runOnUiThread {
            val adapter = ViewAllTeamTasksAdapter()
            adapter.submitList(teamTasks)
            binding.toolbar.title = getStatusNames(state)
            binding.recyclerViewInViewAll.adapter = adapter
        }
    }


}



