package com.example.taskaty.app.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ItemInprogressPersonalCardBinding

class ChildPersonalInProgressAdapter(val items: List<Any>) :
    Adapter<ChildPersonalInProgressAdapter.InProgressViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InProgressViewHolder {
        val binding = ItemInprogressPersonalCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InProgressViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: InProgressViewHolder, position: Int) {

    }

    class InProgressViewHolder(val binding: ItemInprogressPersonalCardBinding) :
        ViewHolder(binding.root) {
    }
}