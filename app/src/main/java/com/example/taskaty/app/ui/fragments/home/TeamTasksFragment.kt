package com.example.taskaty.app.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.app.ui.fragments.home.adapters.ParentPersonalAdapter
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentTeamTasksBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.interactors.CardDataInteractor

class TeamTasksFragment :
    BaseFragment<FragmentTeamTasksBinding>(FragmentTeamTasksBinding::inflate) {

    private val token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6ImMyMzY0MzdmLTZiMDktNGMyNC1iZDhmLTFmZmFhYmY5ZWVmZSIsInRlYW1JZCI6ImMyYzAyNTA3LTk5NjgtNDg2Yi05YmYwLTRjMzg2MGZlMWYyZCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxODUzMjcyfQ.p02yBvXNP7npFkiegLO6aJTSrXjPtk91Urfwsuza-sQ"
    private val interactor = CardDataInteractor(RemoteTasksRepository.getInstance(token))
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
                val tasks = response.data
                filterTasks(tasks)
            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                Log.d("tag", "getTeamTasksData onError: ${response.message}")

            }

        })

    }

    private fun filterTasks(tasks: List<TeamTask>) {
        inProgressTasks = tasks.filter { it.task.status == PersonalTasksFragment.IN_PROGRESS_STATUS }
        upcomingTasks = tasks.filter { it.task.status == PersonalTasksFragment.UPCOMING_STATUS }.take(
            PersonalTasksFragment.LIMIT
        )
        doneTasks = tasks.filter { it.task.status == PersonalTasksFragment.DONE_STATUS }.take(
            PersonalTasksFragment.LIMIT
        )

    }
//    private fun initViews() {
//        val adapter = ParentPersonalAdapter(inProgressTasks, upcomingTasks, doneTasks)
//        binding.PersonalTasksRecycler.adapter = adapter
//        showTasks()
//    }

    private fun showTasks() {
        binding.apply {
            PersonalTasksRecycler.isVisible = true
        }
    }

}