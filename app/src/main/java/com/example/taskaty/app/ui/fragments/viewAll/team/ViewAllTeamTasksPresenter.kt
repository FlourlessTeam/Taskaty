package com.example.taskaty.app.ui.fragments.viewAll.team

import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.interactors.TeamTaskInteractor

class ViewAllTeamTasksPresenter(
    private val teamTaskInteractor: TeamTaskInteractor,
    private val view: ViewAllTeamTasksContract.View
) : ViewAllTeamTasksContract.Presenter {
    override fun getTeamTaskData() {
        teamTaskInteractor.getTeamTaskData(object : RepoCallback<List<TeamTask>> {
            override fun onSuccess(response: RepoResponse.Success<List<TeamTask>>) {
                view.viewAllTeamTasksStatus(response.data)
            }

            override fun onError(response: RepoResponse.Error<List<TeamTask>>) {
                TODO("Not yet implemented")
            }


        })

    }
}