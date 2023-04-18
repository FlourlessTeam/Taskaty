package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.taskaty.app.ui.fragments.details.personal.ViewAllPersonalTasksAdapter
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.viewAll.personal.ViewAllPersonalTasksContract
import com.example.taskaty.app.ui.fragments.viewAll.personal.ViewAllPersonalTasksPresenter
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.databinding.FragmentViewAllPersonalTasksBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.CardDataInteractor


class ViewAllPersonalTasksFragment :
    BaseFragment<FragmentViewAllPersonalTasksBinding>(FragmentViewAllPersonalTasksBinding::inflate),
    ViewAllPersonalTasksContract.View {
    private lateinit var presenter: ViewAllPersonalTasksContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ViewAllPersonalTasksPresenter(
            CardDataInteractor(RemoteTasksRepository.getInstance()), this
        )
        setup()
    }

    private fun setup() {
        presenter.getPersonalTasks()
    }

    private fun getStatusNames(status: Int?): String {
        return when (status) {
            0 -> "ToDo"
            1 -> "In Progress"
            else -> "Done"
        }
    }

    override fun viewAllPersonalTasksStatus(tasks: List<Task>) {
        val status = arguments?.getInt("key")
        requireActivity().runOnUiThread {
            val adapter = ViewAllPersonalTasksAdapter()
            adapter.submitList(tasks.filter { it.status == status })
            binding.toolbar.title = getStatusNames(status)
            binding.recyclerViewInViewAll.adapter = adapter
        }
    }

}
