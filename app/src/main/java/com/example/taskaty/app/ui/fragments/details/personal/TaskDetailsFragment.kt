package com.example.taskaty.app.ui.fragments.details.personal

import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.ListPopupWindow
import androidx.core.view.isVisible
import com.example.taskaty.R
import com.example.taskaty.app.ui.fragments.abstractFragments.BaseFragment
import com.example.taskaty.data.repositories.local.LocalAuthRepository
import com.example.taskaty.data.repositories.remote.RemoteTasksRepository
import com.example.taskaty.databinding.FragmentTaskDetailsBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.interactors.PersonalTaskInteractor
import java.text.SimpleDateFormat
import java.util.*


class TaskDetailsFragment : BaseFragment<FragmentTaskDetailsBinding>(
	FragmentTaskDetailsBinding::inflate
), IContract.IView {

	private lateinit var presenter: IContract.IPresenter
	private lateinit var listPopupWindow: ListPopupWindow
	private lateinit var taskId: String

	private val statusMap = mapOf(
		UPCOMING_STATE to "Up Coming",
		IN_PROGRESS_STATE to "In Progress",
		DONE_STATE to "Done"
	)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		taskId = arguments?.getString(ARGUMENT_KEY, "")!!

		val token = LocalAuthRepository.getInstance(requireActivity().application).getToken()

		val remoteTasksRepository = RemoteTasksRepository.getInstance(token)
		presenter = TaskDetailsPresenter(
			this,
			PersonalTaskInteractor(remoteTasksRepository)
		)

		setup()
	}

	private fun setup() {
		presenter.getTask(taskId)
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
			presenter.updateTaskStatus(taskId, taskStatus)
		}

		binding.taskStatusButton.setOnClickListener {
			listPopupWindow.show()
		}
	}

	override fun updateUiData(task: Task) {
		requireActivity().runOnUiThread {
			with(binding) {
				taskTitle.text = task.title
				taskStatusButton.text = statusMap[task.status]
				taskDate.text = task.creationTime.toDateFormat()
				taskTime.text = task.creationTime.toTimeFormat()
				taskDescriptionContent.text = task.description
			}
		}
	}

	override fun showGetTaskLoading() {
		showLoading(progressBarVisible = true, taskDataVisible = false)
	}

	override fun hideGetTaskLoading() {
		showLoading(progressBarVisible = false, taskDataVisible = true)
	}

	override fun showUpdateTaskLoading() {
		showLoading(progressBarVisible = true, taskDataVisible = true)
	}

	override fun hideUpdateTaskLoading() {
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
		private const val ARGUMENT_KEY = "personal_task_id"
		private const val UPCOMING_STATE = 0
		private const val IN_PROGRESS_STATE = 1
		private const val DONE_STATE = 2

		fun getInstance(taskId: String) = TaskDetailsFragment().apply {
			arguments = Bundle().apply {
				putString(ARGUMENT_KEY, taskId)
			}
		}
	}


}
