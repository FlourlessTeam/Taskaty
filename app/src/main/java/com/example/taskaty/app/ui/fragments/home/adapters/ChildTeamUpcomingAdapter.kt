package com.example.taskaty.app.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ItemInprogressPersonalCardBinding
import com.example.taskaty.databinding.ItemUpcomingPersonalCardBinding
import com.example.taskaty.databinding.ItemUpcomingTeamCardBinding
import com.example.taskaty.domain.entities.Task

class ChildTeamUpcomingAdapter(val items: List<Task>) :
    Adapter<ChildTeamUpcomingAdapter.UpcomingViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val binding = ItemUpcomingTeamCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UpcomingViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {

    }

    class UpcomingViewHolder(val binding: ItemUpcomingTeamCardBinding) :
        ViewHolder(binding.root) {
    }
}