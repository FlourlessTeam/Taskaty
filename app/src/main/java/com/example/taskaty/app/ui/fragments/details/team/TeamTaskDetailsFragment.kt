package com.example.taskaty.app.ui.fragments.details.team

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.view.isVisible
import androidx.transition.TransitionManager
import com.example.taskaty.R
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.local.LocalAuthRepository
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.databinding.FragmentTeamTaskDetailsBinding
import com.example.taskaty.domain.entities.TeamTask
import com.example.taskaty.domain.interactors.TeamTaskInteractor
import java.text.SimpleDateFormat
import java.util.*


class TeamTaskDetailsFragment : BaseFragment<FragmentTeamTaskDetailsBinding>(
	FragmentTeamTaskDetailsBinding::inflate
), IContract.IView {

	private lateinit var presenter: IContract.IPresenter
	private lateinit var listPopupWindow: ListPopupWindow
	private lateinit var teamTaskId: String

	private val statusMap = mapOf(
		UPCOMING_STATE to "Up Coming",
		IN_PROGRESS_STATE to "In Progress",
		DONE_STATE to "Done"
	)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		teamTaskId = arguments?.getString(ARGUMENT_KEY, "")!!

		val token = LocalAuthRepository.getInstance(requireActivity().application).getToken()

		val remoteTasksRepository = RemoteTasksRepository.getInstance(token)
		presenter = TeamTaskDetailsPresenter(
			this,
			TeamTaskInteractor(remoteTasksRepository)
		)

		setup()
	}

	private fun setup() {
		presenter.getTeamTask(teamTaskId)
		initMenu()
		addCallbacks()
	}

	private fun initMenu() {
		listPopupWindow = ListPopupWindow(requireContext())
		listPopupWindow.anchorView = binding.taskStatusButton

		val items = statusMap.values.toList()
		val adapter = ArrayAdapter(requireContext(), R.layout.item_task_state, items)
		listPopupWindow.setAdapter(adapter)
	}

	private fun addCallbacks() {
		var taskStatus = UPCOMING_STATE

		listPopupWindow.setOnItemClickListener { _, _, status: Int, _ ->
			binding.taskStatusButton.text = statusMap[status]
			taskStatus = status
			listPopupWindow.dismiss()
		}

		binding.submit.setOnClickListener {
			presenter.updateTeamTaskStatus(teamTaskId, taskStatus)
		}

		binding.taskStatusButton.setOnClickListener {
			listPopupWindow.show()
		}
	}

	override fun updateUiData(task: TeamTask) {
		requireActivity().runOnUiThread {
			with(binding) {
				taskTitle.text = task.task.title
				taskStatusButton.text = statusMap[task.task.status]
				taskDate.text = task.task.creationTime.toDateFormat()
				taskTime.text = task.task.creationTime.toTimeFormat()
				taskDescriptionContent.text = task.task.description
				taskTeam.text = task.assignee
			}
		}
	}

	override fun showGetTeamTaskLoading() {
		showLoading(progressBarVisible = true, taskDataVisible = false)
	}

	override fun hideGetTeamTaskLoading() {
		showLoading(progressBarVisible = false, taskDataVisible = true)
	}

	override fun showUpdateTeamTaskLoading() {
		showLoading(progressBarVisible = true, taskDataVisible = true)
	}

	override fun hideUpdateTeamTaskLoading() {
		showLoading(progressBarVisible = false, taskDataVisible = true)
	}

	override fun showMessage(message: String) {
		showToast(message)
	}

	private fun showToast(message: String) {
		requireActivity().runOnUiThread {
			Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
		}
	}

	private fun showLoading(progressBarVisible: Boolean, taskDataVisible: Boolean) {
		requireActivity().runOnUiThread {
			TransitionManager.beginDelayedTransition(binding.root)
			binding.progressBar.isVisible = progressBarVisible
			binding.taskDataLayout.isVisible = taskDataVisible
		}
	}


	//Need to be moved to another file
	private fun String.toDateFormat(): String {
		val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
		val date = inputFormat.parse(this)
		val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
		return outputDateFormat.format(date!!)
	}

	//Need to be moved to another file
	private fun String.toTimeFormat(): String {
		val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
		val time = inputFormat.parse(this)
		val outputTimeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
		return outputTimeFormat.format(time!!)
	}

	companion object {
		private const val ARGUMENT_KEY = "team_task_id"
		private const val UPCOMING_STATE = 0
		private const val IN_PROGRESS_STATE = 1
		private const val DONE_STATE = 2

		fun getInstance(teamTaskId: String) = TeamTaskDetailsFragment().apply {
			arguments = Bundle().apply {
				putString(ARGUMENT_KEY, teamTaskId)
			}
		}
	}


}
