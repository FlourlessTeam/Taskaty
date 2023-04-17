package com.example.taskaty.app.ui.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentPersonalTasksBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.CardDataInteractor


class PersonalTasksFragment : BaseFragment<FragmentPersonalTasksBinding>(FragmentPersonalTasksBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val interactor = CardDataInteractor(RemoteTasksRepository.getInstance("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6ImMyMzY0MzdmLTZiMDktNGMyNC1iZDhmLTFmZmFhYmY5ZWVmZSIsInRlYW1JZCI6ImMyYzAyNTA3LTk5NjgtNDg2Yi05YmYwLTRjMzg2MGZlMWYyZCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxODUzMjcyfQ.p02yBvXNP7npFkiegLO6aJTSrXjPtk91Urfwsuza-sQ"))
        getPersonalTasksData(interactor)
    }


    private fun getPersonalTasksData(interactor: CardDataInteractor) {
        interactor.getPersonalTasksData( object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                val tasks = response.data


            }
            override fun onError(response: RepoResponse.Error<List<Task>>) {
                Log.d("tag", "getPersonalTasksData onError: ${response.message}")

            }
        })

    }

}