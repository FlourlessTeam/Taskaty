package com.example.taskaty.app.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.taskaty.R
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentAddTaskBottomSheetBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.repositories.remote.TasksDataSource
import com.example.taskaty.domain.repositories.remote.TeamTasksDataSource
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.sql.Timestamp

enum class Tabs {

}

class NewTaskBottomSheetFragment(val selectedTabPosition: Int) : BottomSheetDialogFragment(),
    RepoCallback<Unit> {

    private lateinit var binding: FragmentAddTaskBottomSheetBinding
    private val token =
        "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJodHRwczovL3RoZS1jaGFuY2Uub3JnLyIsInN1YiI6IjIyMjg4N2RiLWExNDAtNDZhZi1hNTc2LWM5NDhjN2E3NjhkMyIsInRlYW1JZCI6ImMyYzAyNTA3LTk5NjgtNDg2Yi05YmYwLTRjMzg2MGZlMWYyZCIsImlzcyI6Imh0dHBzOi8vdGhlLWNoYW5jZS5vcmcvIiwiZXhwIjoxNjgxNjczMDQ0fQ.pDGoQvavAJVrorD6RiOX-09pYq2_qnxisLz30CrgY-k"
    private val personalTaskDataSource: TasksDataSource = RemoteTasksRepository.getInstance(token)
    private val teamTaskDataSource: TeamTasksDataSource = RemoteTasksRepository.getInstance(token)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupPersonalOrTeamBottomSheet()
        binding.newTaskBtn.setOnClickListener {
            onCreateBtnClicked()
        }
    }


    private fun setupPersonalOrTeamBottomSheet() {
        //selectedTabPosition when equal 0 means we in personal task fragment
        //selectedTabPosition when equal 1 means we in team task fragment

        if (selectedTabPosition == 1) {
            binding.title.setText(R.string.create_team_task)
            binding.assigneeTextInput.visibility = View.VISIBLE
        }
    }

    private fun onCreateBtnClicked() {
        //selectedTabPosition when equal 0 means we in personal task fragment
        //selectedTabPosition when equal 1 means we in team task fragment
        val task = Task(
            id = 0,
            title = binding.titleTextInput.editText?.text.toString(),
            description = binding.descriptionTextInput.editText?.text.toString(),
            status = 0,
            creationTime = Timestamp(0)
        )
        if (selectedTabPosition == 0) {
            personalTaskDataSource.createPersonalTask(task, this)
        } else {
            val teamTask = TeamTask(task, binding.assigneeTextInput.editText?.text.toString())
            teamTaskDataSource.createTeamTask(teamTask, this)
        }
    }

    override fun onSuccess(response: RepoResponse.Success<Unit>) {
        Toast.makeText(requireContext(), "Task Created Successfully", Toast.LENGTH_SHORT).show()
        dismiss()
    }

    override fun onError(response: RepoResponse.Error<Unit>) {
        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
    }
}