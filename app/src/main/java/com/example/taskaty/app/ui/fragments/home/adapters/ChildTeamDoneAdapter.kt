package com.example.taskaty.app.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ItemDoneTeamCardBinding
import com.example.taskaty.domain.entities.Task

class ChildTeamDoneAdapter(val items: List<Task>) :
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

    }

    class DoneViewHolder(val binding: ItemDoneTeamCardBinding) :
        ViewHolder(binding.root) {
    }
}