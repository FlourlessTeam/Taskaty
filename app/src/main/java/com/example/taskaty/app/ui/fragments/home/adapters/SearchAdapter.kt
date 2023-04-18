package com.example.taskaty.app.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.taskaty.R
import com.example.taskaty.databinding.ItemInprogressPersonalCardBinding
import com.example.taskaty.domain.entities.PersonalTask
import java.text.SimpleDateFormat
import java.util.*

class SearchAdapter(private val searchListener: SearchListener) :
    ListAdapter<PersonalTask, SearchAdapter.SearchViewHolder>(TaskDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding = ItemInprogressPersonalCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(binding)
    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, searchListener)

    }

    class SearchViewHolder(private val binding: ItemInprogressPersonalCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PersonalTask, searchListener: SearchListener) {
            binding.apply {
                binding.root.setOnClickListener {
                    searchListener.onClick(item)
                }

                taskCardHeaderInProgress.text = item.title
                taskCardDescriptionInProgress.text = item.description
                timeTextInProgress.text = item.creationTime.toTimeFormat()
                taskCardDateInProgress.text = item.creationTime.toDateFormat()
                inProgressText.text = when (item.status) {
                    0 -> "Upcoming"
                    1 -> "In Progress"
                    else -> "Done"
                }
                inProgressText.backgroundTintList = ContextCompat.getColorStateList(
                    binding.root.context,
                    getStatusColors(item.status)
                )
            }
        }
    }

}

private fun String.toDateFormat(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    val date = inputFormat.parse(this)
    val outputDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return outputDateFormat.format(date!!)
}

private fun String.toTimeFormat(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    val time = inputFormat.parse(this)
    val outputTimeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    return outputTimeFormat.format(time!!)
}

private fun getStatusColors(status: Int?): Int {
    return when (status) {
        0 -> R.color.todo_color
        1 -> R.color.in_progress_color
        else -> R.color.done_color
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<PersonalTask>() {
    override fun areItemsTheSame(oldItem: PersonalTask, newItem: PersonalTask): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PersonalTask, newItem: PersonalTask): Boolean {
        return oldItem == newItem
    }

}



