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
import com.example.taskaty.databinding.FragmentPersonalTasksBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.CardDataInteractor


class PersonalTasksFragment :
    BaseFragment<FragmentPersonalTasksBinding>(FragmentPersonalTasksBinding::inflate) {

    private val token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6ImMyMzY0MzdmLTZiMDktNGMyNC1iZDhmLTFmZmFhYmY5ZWVmZSIsInRlYW1JZCI6ImMyYzAyNTA3LTk5NjgtNDg2Yi05YmYwLTRjMzg2MGZlMWYyZCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxODUzMjcyfQ.p02yBvXNP7npFkiegLO6aJTSrXjPtk91Urfwsuza-sQ"
    private val interactor =
        CardDataInteractor(RemoteTasksRepository.getInstance())
    private var inProgressTasks = listOf<Task>()
    private var upcomingTasks = listOf<Task>()
    private var doneTasks = listOf<Task>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPersonalTasksData(interactor)
    }

    private fun getPersonalTasksData(interactor: CardDataInteractor) {
        interactor.getPersonalTasksData(object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                val tasks = response.data
                filterTasks(tasks)
            }

            override fun onError(response: RepoResponse.Error<List<Task>>) {
                Log.d("tag", "getPersonalTasks onError: ${response.message}")
                showErrorMessage(response.message)
            }
        })
    }

    private fun filterTasks(tasks: List<Task>) {
        inProgressTasks = tasks.filter { it.status == IN_PROGRESS_STATUS }
        upcomingTasks = tasks.filter { it.status == UPCOMING_STATUS }.take(LIMIT)
        doneTasks = tasks.filter { it.status == DONE_STATUS }.take(LIMIT)
        initViews()
    }

    private fun initViews() {
        val adapter = ParentPersonalAdapter(inProgressTasks, upcomingTasks, doneTasks)
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
        const val IN_PROGRESS_STATUS = 0
        const val UPCOMING_STATUS = 1
        const val DONE_STATUS = 2
        const val LIMIT = 2
    }
}