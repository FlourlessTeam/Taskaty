package com.example.taskaty.app.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ChildRecyclerHomeChartBinding
import com.example.taskaty.databinding.ChildRecyclerHomePersonalDoneBinding
import com.example.taskaty.databinding.ChildRecyclerHomePersonalInprogressBinding
import com.example.taskaty.databinding.ChildRecyclerHomePersonalUpcomingBinding
import com.example.taskaty.domain.entities.Task

class ParentPersonalAdapter(
    var InProgress: List<Task>,
    var Upcoming: List<Task>,
    var Done: List<Task>
) :
    Adapter<ParentPersonalAdapter.BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {

            FIRST_ITEM -> {
                val view = ChildRecyclerHomeChartBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return ChartViewHolder(view.root)
            }
            SECOND_ITEM -> {
                val view = ChildRecyclerHomePersonalInprogressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return InProgressViewHolder(view.root)
            }
            THIRD_ITEM -> {
                val view = ChildRecyclerHomePersonalUpcomingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return UpcomingViewHolder(view.root)
            }
            else -> {
                val view = ChildRecyclerHomePersonalDoneBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return UpcomingViewHolder(view.root)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            FIRST_ITEM -> FIRST_ITEM
            SECOND_ITEM -> SECOND_ITEM
            THIRD_ITEM -> THIRD_ITEM
            else -> FOURTH_ITEM
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is InProgressViewHolder -> bindInProgress(holder)
            is UpcomingViewHolder -> bindUpcoming(holder)
            is DoneViewHolder -> bindDone(holder)
        }
    }

    private fun bindInProgress(holder: InProgressViewHolder) {
        val adapter = ChildPersonalInProgressAdapter(InProgress)
        holder.binding.apply {
            childRecycler.adapter = adapter
            tasksNumber.text = InProgress.size.toString()
        }
    }

    private fun bindUpcoming(holder: UpcomingViewHolder) {
        val adapter = ChildPersonalUpcomingAdapter(Upcoming)
        holder.binding.apply {
            childRecycler.adapter = adapter
            tasksNumber.text = Upcoming.size.toString()
        }
    }

    private fun bindDone(holder: DoneViewHolder) {
        val adapter = ChildPersonalDoneAdapter(Done)
        holder.binding.apply {
            childRecycler.adapter = adapter
            tasksNumber.text = Done.size.toString()
        }
    }

    override fun getItemCount() = FOURTH_ITEM + 1

    abstract class BaseViewHolder(view: View) : ViewHolder(view)

    class InProgressViewHolder(view: View) : BaseViewHolder(view) {
        val binding = ChildRecyclerHomePersonalInprogressBinding.bind(view)
    }

    class UpcomingViewHolder(view: View) : BaseViewHolder(view) {
        val binding = ChildRecyclerHomePersonalUpcomingBinding.bind(view)
    }

    class ChartViewHolder(view: View) : BaseViewHolder(view) {

    }

    class DoneViewHolder(view: View) : BaseViewHolder(view) {
        val binding = ChildRecyclerHomePersonalDoneBinding.bind(view)
    }

    companion object {
        const val FIRST_ITEM = 0
        const val SECOND_ITEM = 1
        const val THIRD_ITEM = 2
        const val FOURTH_ITEM = 3
    }
}