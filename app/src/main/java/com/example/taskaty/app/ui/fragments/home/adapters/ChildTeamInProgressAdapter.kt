package com.example.taskaty.app.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ItemInprogressTeamCardBinding
import com.example.taskaty.domain.entities.TeamTask

class ChildTeamInProgressAdapter(private val items: List<TeamTask>) :
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
        holder.binding.apply {
            timeTextTeam.text = item.creationTime
            taskCardHeaderInProgressTeam.text = item.title
            taskDetailsTeam.text = item.description
        }
    }

    class InProgressViewHolder(val binding: ItemInprogressTeamCardBinding) :
        ViewHolder(binding.root)
}