package com.example.taskaty.app.ui.fragments.home.adapters

import android.icu.text.SimpleDateFormat
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
import java.util.*

class ParentTeamAdapter(
    val InProgress: List<TeamTask>,
    val Upcoming: List<TeamTask>,
    val Done: List<TeamTask>,
    private val onViewAllClickListener: OnViewAllClickListener
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
            inProgressViewAll.setOnClickListener { onViewAllClickListener.onViewAllClick(1) }
            childRecycler.adapter = adapter
            tasksNumber.text = InProgress.size.toString()
        }
    }

    private fun bindUpcoming(holder: UpcomingViewHolder) {
        val inputDateFormat = SimpleDateFormat(INPUT_DATE_PATTERN, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(OUTPUT_DATE_PATTERN, Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat(OUTPUT_TIME_PATTERN, Locale.getDefault())

        holder.binding.apply {
            linearLayout.setOnClickListener { onViewAllClickListener.onViewAllClick(0) }
            tasksNumber.text = Upcoming.size.toString()
            if (Upcoming.size == 1) {
                val firstItem = Upcoming[FIRST_ITEM]
                taskHeaderFirst.text = firstItem.title
                dateTextFirst.text =
                    outputDateFormat.format(inputDateFormat.parse(firstItem.creationTime))
                timeTextFirst.text =
                    outputTimeFormat.format(inputDateFormat.parse(firstItem.creationTime))
            } else if (Upcoming.size >= 2) {
                val firstItem = Upcoming[FIRST_ITEM]
                val secondItem = Upcoming[SECOND_ITEM]
                taskHeaderFirst.text = firstItem.title
                dateTextFirst.text =
                    outputDateFormat.format(inputDateFormat.parse(firstItem.creationTime))
                timeTextFirst.text =
                    outputTimeFormat.format(inputDateFormat.parse(firstItem.creationTime))
                taskHeaderSecond.text = secondItem.title
                dateTextSecond.text =
                    outputDateFormat.format(inputDateFormat.parse(secondItem.creationTime))
                timeTextSecond.text =
                    outputTimeFormat.format(inputDateFormat.parse(secondItem.creationTime))
            }
        }
    }

    private fun bindDone(holder: DoneViewHolder) {
        val inputDateFormat = SimpleDateFormat(INPUT_DATE_PATTERN, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(OUTPUT_DATE_PATTERN, Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat(OUTPUT_TIME_PATTERN, Locale.getDefault())

        holder.binding.apply {
            doneViewAll.setOnClickListener { onViewAllClickListener.onViewAllClick(2) }
            tasksNumber.text = Done.size.toString()
            if (Upcoming.size == 1) {
                val firstItem = Done[FIRST_ITEM]
                taskHeaderFirst.text = firstItem.title
                dateTextFirst.text =
                    outputDateFormat.format(inputDateFormat.parse(firstItem.creationTime))
                timeTextFirst.text =
                    outputTimeFormat.format(inputDateFormat.parse(firstItem.creationTime))
            } else if (Upcoming.size >= 2) {
                val firstItem = Done[FIRST_ITEM]
                val secondItem = Done[SECOND_ITEM]
                taskHeaderFirst.text = firstItem.title
                dateTextFirst.text =
                    outputDateFormat.format(inputDateFormat.parse(firstItem.creationTime))
                timeTextFirst.text =
                    outputTimeFormat.format(inputDateFormat.parse(firstItem.creationTime))
                taskHeaderSecond.text = secondItem.title
                dateTextSecond.text =
                    outputDateFormat.format(inputDateFormat.parse(secondItem.creationTime))
                timeTextSecond.text =
                    outputTimeFormat.format(inputDateFormat.parse(secondItem.creationTime))
            }
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
        const val INPUT_DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
        const val OUTPUT_DATE_PATTERN = "yyyy-MM-dd"
        const val OUTPUT_TIME_PATTERN = "HH:mm"
    }
    class OnViewAllClickListener(private val onClick: (Int) -> Unit) {
        fun onViewAllClick(taskType: Int) = onClick(taskType)
    }
}