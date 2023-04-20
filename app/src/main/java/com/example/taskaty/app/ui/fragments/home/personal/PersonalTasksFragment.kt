package com.example.taskaty.app.ui.fragments.home.personal

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.taskaty.R
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.details.personal.TaskDetailsFragment
import com.example.taskaty.app.ui.fragments.home.adapters.ParentPersonalAdapter
import com.example.taskaty.app.ui.fragments.viewAll.personal.ViewAllPersonalTasksFragment
import com.example.taskaty.data.repositories.AllTasksRepositoryImpl
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentPersonalTasksBinding
import com.example.taskaty.domain.entities.PersonalTask
import com.example.taskaty.domain.interactors.CardDataInteractor


class PersonalTasksFragment :
    BaseFragment<FragmentPersonalTasksBinding>(FragmentPersonalTasksBinding::inflate) {

    private val interactor =
        CardDataInteractor(AllTasksRepositoryImpl.getInstance())
    private var inProgressTasks = listOf<PersonalTask>()
    private var upcomingTasks = listOf<PersonalTask>()
    private var doneTasks = listOf<PersonalTask>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPersonalTasksData(interactor)
    }

    private fun getPersonalTasksData(interactor: CardDataInteractor) {
        interactor.getPersonalTasksData(object : RepoCallback<List<PersonalTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<PersonalTask>>) {
                requireActivity().runOnUiThread {
                    val tasks = response.data
                    filterTasks(tasks)

                }

            }

            override fun onError(response: RepoResponse.Error<List<PersonalTask>>) {
                Log.d("tag", "getPersonalTasksData onError: ${response.message}")
                requireActivity().runOnUiThread {
                    showErrorMessage(response.message)

                }

            }
        })
    }

    private fun filterTasks(tasks: List<PersonalTask>) {
        inProgressTasks = tasks.filter { it.status == IN_PROGRESS_STATUS }
        upcomingTasks = tasks.filter { it.status == UPCOMING_STATUS }
        doneTasks = tasks.filter { it.status == DONE_STATUS }
        initViews()
    }

    private fun initViews() {
        val adapter = ParentPersonalAdapter(inProgressTasks, upcomingTasks, doneTasks, object : ParentPersonalAdapter.OnViewAllClickListener {
            override fun onViewAllClick(taskType: Int) {
                val frag = ViewAllPersonalTasksFragment.newInstance(taskType)
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container_fragment, frag).addToBackStack(null).commit()
            }
        }, object : ParentPersonalAdapter.OnPersonalTaskClickListener {
            override fun onTaskClick(task: PersonalTask) {
                val frag = TaskDetailsFragment.newInstance(task.id)
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container_fragment, frag).addToBackStack(null).commit()
            }

        })
        binding.PersonalTasksRecycler.adapter = adapter
        showTasks()
    }

    private fun showTasks() {
        binding.apply {
            progressBar.isVisible = false
            PersonalTasksRecycler.isVisible = true
        }
    }

    private fun showErrorMessage(message: String) {
        binding.apply {
            progressBar.isVisible = false
            errorText.isVisible = true
            errorText.text = message
        }
    }

    companion object {
        const val UPCOMING_STATUS = 0
        const val IN_PROGRESS_STATUS = 1
        const val DONE_STATUS = 2
    }
}