package com.example.taskaty.app.ui.fragments.home.team

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.home.adapters.ParentTeamAdapter
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentTeamTasksBinding
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.interactors.CardDataInteractor

class TeamTasksFragment :
    BaseFragment<FragmentTeamTasksBinding>(FragmentTeamTasksBinding::inflate) {

    private val interactor = CardDataInteractor(RemoteTasksRepository.getInstance())
    private var inProgressTasks = listOf<TeamTask>()
    private var upcomingTasks = listOf<TeamTask>()
    private var doneTasks = listOf<TeamTask>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTeamTasksData(interactor)
    }

    private fun getTeamTasksData(interactor: CardDataInteractor) {
        interactor.getTeamTasksData(object : RepoCallback<List<TeamTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
                requireActivity().runOnUiThread {
                    val tasks = response.data
                    filterTasks(tasks)
                }

            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                Log.d("tag", "getTeamTasksData onError: ${response.message}")
                requireActivity().runOnUiThread { showErrorMessage(response.message) }

            }

        })

    }

    private fun filterTasks(tasks: List<TeamTask>) {
        inProgressTasks = tasks.filter { it.status == IN_PROGRESS_STATUS }
        upcomingTasks = tasks.filter { it.status == UPCOMING_STATUS }
        doneTasks = tasks.filter { it.status == DONE_STATUS }
        initViews()
    }

    private fun initViews() {
        val adapter = ParentTeamAdapter(inProgressTasks, upcomingTasks, doneTasks)
        binding.PersonalTasksRecycler.adapter = adapter
        showTasks()
    }

    private fun showTasks() {
        binding.apply {
            PersonalTasksRecycler.isVisible = true
            progressBar.isVisible = false
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
        const val IN_PROGRESS_STATUS = 0
        const val UPCOMING_STATUS = 1
        const val DONE_STATUS = 2
    }

}