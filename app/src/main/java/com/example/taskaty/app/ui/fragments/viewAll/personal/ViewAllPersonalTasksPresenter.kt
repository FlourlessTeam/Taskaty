package com.example.taskaty.app.ui.fragments.viewAll.personal
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.CardDataInteractor


class ViewAllPersonalTasksPresenter(
    private val cardDataInteractor: CardDataInteractor,
    private val view: ViewAllPersonalTasksContract.View
) : ViewAllPersonalTasksContract.Presenter {
    override fun getPersonalTasks() {
        cardDataInteractor.getPersonalTasksData(object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                view.viewAllPersonalTasksStatus(response.data)
            }

            override fun onError(response: RepoResponse.Error<List<Task>>) {
            }

        })
    }
}