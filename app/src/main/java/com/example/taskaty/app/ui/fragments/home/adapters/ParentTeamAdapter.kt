package com.example.taskaty.app.ui.fragments.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.databinding.ChildRecyclerHomeChartBinding
import com.example.taskaty.databinding.ChildRecyclerHomeTeamDoneBinding
import com.example.taskaty.databinding.ChildRecyclerHomeTeamInprogressBinding
import com.example.taskaty.databinding.ChildRecyclerHomeTeamUpcomingBinding
import com.example.taskaty.domain.entities.TeamTask

class ParentTeamAdapter(
    val InProgress: List<TeamTask>,
    val Upcoming: List<TeamTask>,
    val Done: List<TeamTask>
) : Adapter<ParentTeamAdapter.BaseViewHolder>() {

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
                val view = ChildRecyclerHomeTeamInprogressBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return InProgressViewHolder(view.root)
            }
            THIRD_ITEM -> {
                val view = ChildRecyclerHomeTeamUpcomingBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return UpcomingViewHolder(view.root)
            }
            else -> {
                val view = ChildRecyclerHomeTeamDoneBinding.inflate(
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
        val adapter = ChildTeamInProgressAdapter(InProgress)
        holder.binding.apply {
            childRecycler.adapter = adapter
            tasksNumber.text = InProgress.size.toString()
        }
    }

    private fun bindUpcoming(holder: UpcomingViewHolder) {
        val adapter = ChildTeamUpcomingAdapter(Upcoming)
        holder.binding.apply {
            childRecycler.adapter = adapter
            tasksNumber.text = Upcoming.size.toString()
        }
    }

    private fun bindDone(holder: DoneViewHolder) {
        val adapter = ChildTeamDoneAdapter(Done)
        holder.binding.apply {
            childRecycler.adapter = adapter
            tasksNumber.text = Done.size.toString()
        }
    }

    override fun getItemCount() = FOURTH_ITEM + 1

    abstract class BaseViewHolder(view: View) : ViewHolder(view)

    class InProgressViewHolder(view: View) : BaseViewHolder(view) {
        val binding = ChildRecyclerHomeTeamInprogressBinding.bind(view)
    }

    class UpcomingViewHolder(view: View) : BaseViewHolder(view) {
        val binding = ChildRecyclerHomeTeamUpcomingBinding.bind(view)
    }

    class ChartViewHolder(view: View) : BaseViewHolder(view) {

    }

    class DoneViewHolder(view: View) : BaseViewHolder(view) {
        val binding = ChildRecyclerHomeTeamDoneBinding.bind(view)
    }

    companion object {
        const val FIRST_ITEM = 0
        const val SECOND_ITEM = 1
        const val THIRD_ITEM = 2
        const val FOURTH_ITEM = 3
    }
}