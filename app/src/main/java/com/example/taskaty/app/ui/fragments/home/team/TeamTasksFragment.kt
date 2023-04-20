package com.example.taskaty.app.ui.fragments.home.team

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.taskaty.R
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.details.team.TeamTaskDetailsFragment
import com.example.taskaty.app.ui.fragments.home.adapters.OnTaskClickListener
import com.example.taskaty.app.ui.fragments.home.adapters.OnViewAllClickListener
import com.example.taskaty.app.ui.fragments.home.adapters.ParentTeamAdapter
import com.example.taskaty.app.ui.fragments.viewAll.team.ViewAllTeamTasksFragment
import com.example.taskaty.data.repositories.AllTasksRepositoryImpl
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentTeamTasksBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.interactors.CardDataInteractor
import com.example.taskaty.domain.interactors.PersonalTaskInteractor
import com.example.taskaty.domain.interactors.TeamTaskInteractor

class TeamTasksFragment :
    BaseFragment<FragmentTeamTasksBinding>(FragmentTeamTasksBinding::inflate), TeamTasksView {

    lateinit var presenter: TeamTasksPresenter
    private val interactor = TeamTaskInteractor(AllTasksRepositoryImpl.getInstance())
    private var inProgressTasks = listOf<TeamTask>()
    private var upcomingTasks = listOf<TeamTask>()
    private var doneTasks = listOf<TeamTask>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TeamTasksPresenter(interactor, this)
        presenter.getTeamTasksData()
    }

    override fun filterTasks(tasks: List<TeamTask>) {
        requireActivity().runOnUiThread {
            inProgressTasks = tasks.filter { it.status == IN_PROGRESS_STATUS }
            upcomingTasks = tasks.filter { it.status == UPCOMING_STATUS }
            doneTasks = tasks.filter { it.status == DONE_STATUS }
            initViews()
        }
    }

    override fun showErrorMessage(message: String) {
        requireActivity().runOnUiThread {
            binding.apply {
                progressBar.isVisible = false
                errorText.isVisible = true
                errorText.text = message
            }
        }
    }

    private fun initViews() {
        val adapter = ParentTeamAdapter(
            inProgressTasks,
            upcomingTasks,
            doneTasks,
            object : OnViewAllClickListener {
                override fun onViewAllClick(taskType: Int) {
                    val frag = ViewAllTeamTasksFragment.newInstance(taskType)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.container_fragment, frag).addToBackStack(null).commit()
                }
            },
            object : OnTaskClickListener {
                override fun onTaskClick(task: Task) {
                    val frag = TeamTaskDetailsFragment.getInstance(task.id)
                    requireActivity().supportFragmentManager.beginTransaction()
                        .add(R.id.container_fragment, frag).addToBackStack(null).commit()
                }

            })
        requireActivity().runOnUiThread {
            binding.PersonalTasksRecycler.adapter = adapter
        }
        showTasks()
    }

    private fun showTasks() {
        binding.apply {
            PersonalTasksRecycler.isVisible = true
            progressBar.isVisible = false
        }
    }


    companion object {
        const val UPCOMING_STATUS = 0
        const val IN_PROGRESS_STATUS = 1
        const val DONE_STATUS = 2
    }

}