package com.example.taskaty.app.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ItemDonePersonalCardBinding
import com.example.taskaty.domain.entities.Task

class ChildPersonalDoneAdapter(private val items: List<Task>) :
    Adapter<ChildPersonalDoneAdapter.DoneViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoneViewHolder {
        val binding = ItemDonePersonalCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DoneViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: DoneViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            taskHeader.text = item.title
            timeText.text = item.creationTime
        }
    }

    class DoneViewHolder(val binding: ItemDonePersonalCardBinding) :
        ViewHolder(binding.root) {
    }
}