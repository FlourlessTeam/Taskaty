package com.example.taskaty.app.ui.fragments.home.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.taskaty.R
import com.example.taskaty.data.response.RepoCallback
import com.example.taskaty.data.response.RepoResponse
import com.example.taskaty.databinding.FragmentAddTaskBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment



class NewTaskBottomSheetFragment(private val selectedTabPosition: Int) : BottomSheetDialogFragment(),
    RepoCallback<Unit> {

    private lateinit var binding: FragmentAddTaskBottomSheetBinding
    private val presenter = NewTaskPresenters()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAddTaskBottomSheet()
    }
    private fun setupAddTaskBottomSheet() {

        binding.newTaskBtn.setOnClickListener {
            presenter.onCreateBtnClicked(
                title = binding.titleTextInput.editText?.text.toString(),
                description = binding.descriptionTextInput.editText?.text.toString(),
                assignee = binding.assigneeTextInput.editText?.text.toString(),
                selectedTabPosition,
                this
            )
        }
        if (selectedTabPosition == 1) {
            binding.title.setText(R.string.create_team_task)
            binding.assigneeTextInput.visibility = View.VISIBLE
        } else {
            binding.title.setText(R.string.create_personal_task)
            binding.assigneeTextInput.visibility = View.GONE
        }
    }
    override fun onSuccess(response: RepoResponse.Success<Unit>) {
      requireActivity().runOnUiThread {

          Toast.makeText(requireContext(), "Task Created Successfully", Toast.LENGTH_SHORT).show()
          dismiss()
      }
    }

    override fun onError(response: RepoResponse.Error<Unit>) {
        Toast.makeText(requireContext(), "Something Wrong", Toast.LENGTH_SHORT).show()
    }
}