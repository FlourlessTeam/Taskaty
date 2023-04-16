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


class PersonalTasksFragment :
    BaseFragment<FragmentPersonalTasksBinding>(FragmentPersonalTasksBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val interactor = CardDataInteractor(RemoteTasksRepository.getInstance("token"))
        getPersonalTasksData(interactor, 0)
        getPersonalTasksData(interactor, 1)
        getPersonalTasksData(interactor, 2)
    }


    private fun getPersonalTasksData(interactor: CardDataInteractor, statusType: Int) {
        interactor.getPersonalTasksData(statusType, object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                val tasks = response.data.filter { it.status == statusType }
                //...

            }

            override fun onError(response: RepoResponse.Error<List<Task>>) {
                Log.d("tag", "getPersonalTasksData onError: ${response.message}")

            }

        })

    }

}