package com.example.taskaty.app.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ItemDoneTeamCardBinding
import com.example.taskaty.domain.entities.Task
import com.example.taskaty.domain.entities.TeamTask

class ChildTeamDoneAdapter(val items: List<TeamTask>) :
    Adapter<ChildTeamDoneAdapter.DoneViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneViewHolder {
        val binding = ItemDoneTeamCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DoneViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DoneViewHolder, position: Int) {
        val item = items[position].task
        holder.binding.apply {
            timeText.text = item.creationTime
            taskHeader.text = item.title
        }
    }

    class DoneViewHolder(val binding: ItemDoneTeamCardBinding) :
        ViewHolder(binding.root) {
    }
}