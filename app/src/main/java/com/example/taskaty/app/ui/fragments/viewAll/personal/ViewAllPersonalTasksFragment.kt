package com.example.taskaty.app.ui.fragments

import android.os.Bundle
import android.view.View
import com.example.taskaty.app.adapters.ViewAllPersonalTasksAdapter
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentViewAllPersonalTasksBinding
import com.example.taskaty.domain.entities.Task


class ViewAllPersonalTasksFragment : BaseFragment<FragmentViewAllPersonalTasksBinding>
    (FragmentViewAllPersonalTasksBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()

    }

    private fun setup() {
        //get the status number from the home screen to display the required list
        val status = arguments?.getInt("key")

        CardDataInteractor(RemoteTasksRepository.getInstance())
            .getPersonalTasksData(object : RepoCallback<List<Task>> {
                override fun onSuccess(response: RepoResponse.Success<List<Task>>) {

                    requireActivity().runOnUiThread {
                        val adapter = ViewAllPersonalTasksAdapter()
                        adapter.submitList(response.data.filter { it.status == status })
                        binding.toolbar.title = getStatusNames(status)
                        binding.recyclerViewInViewAll.adapter = adapter
                    }

                }

                override fun onError(response: RepoResponse.Error<List<Task>>) {
                }

            })

    }

    private fun getStatusNames(status: Int?): String {
        return when (status) {
            0 -> "ToDo"
            1 -> "In Progress"
            else -> "Done"
        }
    }

    //token just for testing
    companion object {

        private const val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
                ".eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6" +
                "ImIwNjI3ZDVhLTZlYjUtNDhlNC1hNTA4LTY1ZTY3MzIzMWE3ZCIsIn" +
                "RlYW1JZCI6ImMyYzAyNTA3LTk5NjgtNDg2Yi05YmYwLTRjMzg2MGZlMW" +
                "YyZCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjox" +
                "NjgxOTE2ODY4fQ.GolW2N3a5ubGpK7BPPC9rscD8qW7Be_AqJqCcALipto"
    }

}


//Temporary Interactor should move in another file
class CardDataInteractor(private val repo: RemoteTasksRepository) {

    fun getPersonalTasksData(callback: RepoCallback<List<Task>>) {
        repo.getAllPersonalTasks(object : RepoCallback<List<Task>> {
            override fun onSuccess(response: RepoResponse.Success<List<Task>>) {
                val task = response.data
                callback.onSuccess(RepoResponse.Success(task))
            }

            override fun onError(response: RepoResponse.Error<List<Task>>) {
                callback.onError(RepoResponse.Error(response.message))
            }
        })
    }


}
