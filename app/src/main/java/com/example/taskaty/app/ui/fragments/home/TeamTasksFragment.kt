package com.example.taskaty.app.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentTeamTasksBinding
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.interactors.CardDataInteractor

class TeamTasksFragment :
    BaseFragment<FragmentTeamTasksBinding>(FragmentTeamTasksBinding::inflate) {

    private val token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6ImMyMzY0MzdmLTZiMDktNGMyNC1iZDhmLTFmZmFhYmY5ZWVmZSIsInRlYW1JZCI6ImMyYzAyNTA3LTk5NjgtNDg2Yi05YmYwLTRjMzg2MGZlMWYyZCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxODUzMjcyfQ.p02yBvXNP7npFkiegLO6aJTSrXjPtk91Urfwsuza-sQ"
    private val interactor = CardDataInteractor(RemoteTasksRepository.getInstance(token))


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getTeamTasksData(interactor)
    }

    private fun getTeamTasksData(interactor: CardDataInteractor) {
        interactor.getTeamTasksData(object : RepoCallback<List<TeamTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
                val tasks = response.data

            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                Log.d("tag", "getTeamTasksData onError: ${response.message}")

            }

        })

    }
}