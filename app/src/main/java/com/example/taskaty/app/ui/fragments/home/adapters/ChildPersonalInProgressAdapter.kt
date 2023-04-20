package com.example.taskaty.app.ui.fragments.home.adapters

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ItemInprogressPersonalCardBinding
import com.example.taskaty.domain.entities.PersonalTask
import java.util.*

class ChildPersonalInProgressAdapter(
    private val items: List<PersonalTask>,
    private val onTaskClickListener: OnTaskClickListener
) :
    Adapter<ChildPersonalInProgressAdapter.InProgressViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InProgressViewHolder {
        val binding = ItemInprogressPersonalCardBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return InProgressViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: InProgressViewHolder, position: Int) {
        val item = items[position]
        val inputDatePattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
        val outputDatePattern = "yyyy-MM-dd"
        val outputTimePattern = "HH:mm"
        val inputDateFormat = SimpleDateFormat(inputDatePattern, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(outputDatePattern, Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat(outputTimePattern, Locale.getDefault())

        holder.binding.apply {
            root.setOnClickListener { onTaskClickListener.onTaskClick(item) }
            taskCardHeaderInProgress.text = item.title
            taskCardDescriptionInProgress.text = item.description
            taskCardDateInProgress.text =
                outputDateFormat.format(inputDateFormat.parse(item.creationTime))
            timeTextInProgress.text =
                outputTimeFormat.format(inputDateFormat.parse(item.creationTime))
        }
    }

    class InProgressViewHolder(val binding: ItemInprogressPersonalCardBinding) :
        ViewHolder(binding.root) {
    }
}