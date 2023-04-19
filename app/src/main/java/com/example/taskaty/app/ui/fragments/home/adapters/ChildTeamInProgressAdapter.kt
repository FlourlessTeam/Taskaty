package com.example.taskaty.app.ui.fragments.home.adapters

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ItemInprogressTeamCardBinding
import com.example.taskaty.domain.entities.TeamTask
import java.util.*

class ChildTeamInProgressAdapter(private val items: List<TeamTask>,private val onTeamTaskClickListener: ParentTeamAdapter.OnTeamTaskClickListener) :
    Adapter<ChildTeamInProgressAdapter.InProgressViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InProgressViewHolder {
        val binding = ItemInprogressTeamCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
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
            root.setOnClickListener { onTeamTaskClickListener.onTaskClick(item) }
            timeTextTeam.text = item.creationTime
            taskCardHeaderInProgressTeam.text = item.title
            dateTextTeam.text = outputDateFormat.format(inputDateFormat.parse(item.creationTime))
            timeTextTeam.text = outputTimeFormat.format(inputDateFormat.parse(item.creationTime))
        }
    }

    class InProgressViewHolder(val binding: ItemInprogressTeamCardBinding) :
        ViewHolder(binding.root)
}