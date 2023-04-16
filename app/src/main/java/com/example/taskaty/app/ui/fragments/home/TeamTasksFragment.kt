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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val interactor = CardDataInteractor(RemoteTasksRepository.getInstance("token"))
        getTeamTasksData(interactor, 0)
        getTeamTasksData(interactor, 1)
        getTeamTasksData(interactor, 2)
    }


    private fun getTeamTasksData(interactor: CardDataInteractor, statusType: Int) {
        interactor.getTeamTasksData(statusType, object : RepoCallback<List<TeamTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
                val tasks = response.data.filter { it.task.status == statusType }
                //...

            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                Log.d("tag", "getTeamTasksData onError: ${response.message}")

            }

        })

    }
}