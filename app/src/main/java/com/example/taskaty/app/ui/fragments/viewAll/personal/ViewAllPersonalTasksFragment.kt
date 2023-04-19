package com.example.taskaty.app.ui.fragments.viewAll.personal

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.databinding.FragmentViewAllPersonalTasksBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.PersonalTaskInteractor


class ViewAllPersonalTasksFragment private constructor() :
    BaseFragment<FragmentViewAllPersonalTasksBinding>(FragmentViewAllPersonalTasksBinding::inflate),
    ViewAllPersonalTasksContract.View {
    private lateinit var presenter: ViewAllPersonalTasksContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ViewAllPersonalTasksPresenter(
            PersonalTaskInteractor(RemoteTasksRepository.getInstance()), this
        )
        setup(requireArguments().getInt(TASK_TYPE_ARG))
    }

    private fun setup(status: Int) {
        presenter.getPersonalTasks(status)
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

    override fun viewAllPersonalTasksStatus(tasks: List<Task>) {
        val status = arguments?.getInt(TASK_TYPE_ARG)
        requireActivity().runOnUiThread {
            val adapter = ViewAllPersonalTasksAdapter()
            adapter.submitList(tasks)
            binding.toolbar.title = getStatusNames(status)
            binding.recyclerViewInViewAll.adapter = adapter
        }
    }

    companion object {
        private const val TASK_TYPE_ARG = "task_type"
        fun newInstance(taskType: Int) =
            ViewAllPersonalTasksFragment().apply {
                arguments = Bundle().apply {
                    putInt(TASK_TYPE_ARG, taskType)
                }
            }
    }
}


