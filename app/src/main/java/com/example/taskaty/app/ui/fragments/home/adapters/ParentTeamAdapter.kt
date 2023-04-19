package com.example.taskaty.app.ui.fragments.home.adapters

import android.graphics.Color
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
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.util.Locale

class ParentTeamAdapter(
    private val InProgress: List<TeamTask>,
    private val Upcoming: List<TeamTask>,
    private val Done: List<TeamTask>,
    private val onViewAllClickListener: OnViewAllClickListener,
    private val onTeamTaskClickListener: OnTeamTaskClickListener
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
                return DoneViewHolder(view.root)
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
            is ChartViewHolder -> bindChart(holder)
        }
    }

    private fun bindChart(holder: ChartViewHolder) {
        val totalTasks = Done.size + InProgress.size + Upcoming.size
        var upComingStatesValue = 0
        var doneStatesValue = 0
        var inProgressStatesValue = 0
        if(totalTasks != 0) {
            upComingStatesValue = (Upcoming.size * 100) / totalTasks
            doneStatesValue = (Done.size * 100) / totalTasks
            inProgressStatesValue = (InProgress.size * 100) / totalTasks
        }
        holder.binding.apply {
            todoStates.text = "$upComingStatesValue %"
            doneStates.text = "$doneStatesValue %"
            inProgressStates.text = "$inProgressStatesValue %"
            chart.isDrawHoleEnabled = true
            chart.setUsePercentValues(false)
            chart.setDrawEntryLabels(false)
            chart.holeRadius = 70f
            chart.centerText = "Total \n$totalTasks"
            chart.setCenterTextSize(11F)
            chart.description.isEnabled = false
            chart.legend.isEnabled = false
            val entries = ArrayList<PieEntry>()
            entries.add(PieEntry(Upcoming.size * 1f, "Todo"))
            entries.add(PieEntry(Done.size * 1f, "Done"))
            entries.add(PieEntry(InProgress.size * 1f, "In Progress"))
            val colors = ArrayList<Int>()
            colors.add(Color.parseColor("#7FBAA9"))
            colors.add(Color.parseColor("#93CB80"))
            colors.add(Color.parseColor("#418E77"))
            val dataSet = PieDataSet(entries, "")
            dataSet.colors = colors
            val data = PieData(dataSet)
            data.setDrawValues(false)
            data.setValueFormatter(PercentFormatter(chart))
            data.setValueTextSize(12f)
            data.setValueTextColor(Color.BLACK)
            chart.data = data
            chart.invalidate()
        }
    }



    private fun bindInProgress(holder: InProgressViewHolder) {
        holder.binding.apply {
            if (InProgress.isEmpty()) {
                root.visibility = View.GONE
            } else {
                val adapter = ChildTeamInProgressAdapter(InProgress, onTeamTaskClickListener)
                inProgressViewAll.setOnClickListener { onViewAllClickListener.onViewAllClick(1) }
                childRecycler.adapter = adapter
                tasksNumber.text = InProgress.size.toString()
            }
        }
    }

    private fun bindUpcoming(holder: UpcomingViewHolder) {
        val inputDateFormat = SimpleDateFormat(INPUT_DATE_PATTERN, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(OUTPUT_DATE_PATTERN, Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat(OUTPUT_TIME_PATTERN, Locale.getDefault())

        holder.binding.apply {
            linearLayout.setOnClickListener { onViewAllClickListener.onViewAllClick(0) }
            tasksNumber.text = Upcoming.size.toString()
            if (Upcoming.isEmpty()) {
                root.visibility = View.GONE
            } else if (Upcoming.size == 1) {
                val firstItem = Upcoming[FIRST_ITEM]
                taskHeaderFirst.text = firstItem.title
                dateTextFirst.text =
                    outputDateFormat.format(inputDateFormat.parse(firstItem.creationTime))
                timeTextFirst.text =
                    outputTimeFormat.format(inputDateFormat.parse(firstItem.creationTime))
                upcomingFirstCard.setOnClickListener { onTeamTaskClickListener.onTaskClick(firstItem) }
                upcomingSecondCard.visibility = View.GONE
            } else {
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
                upcomingFirstCard.setOnClickListener { onTeamTaskClickListener.onTaskClick(firstItem) }
                upcomingSecondCard.setOnClickListener { onTeamTaskClickListener.onTaskClick(secondItem) }

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
            if (Done.isEmpty()) {
                root.visibility = View.GONE
            } else if (Done.size == 1) {
                val firstItem = Done[FIRST_ITEM]
                taskHeaderFirst.text = firstItem.title
                dateTextFirst.text =
                    outputDateFormat.format(inputDateFormat.parse(firstItem.creationTime))
                timeTextFirst.text =
                    outputTimeFormat.format(inputDateFormat.parse(firstItem.creationTime))
                firstCard.setOnClickListener { onTeamTaskClickListener.onTaskClick(firstItem) }
                secondCard.visibility = View.GONE
            } else {
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
                firstCard.setOnClickListener { onTeamTaskClickListener.onTaskClick(firstItem) }
                secondCard.setOnClickListener { onTeamTaskClickListener.onTaskClick(secondItem) }
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

    class ChartViewHolder(view: View) : BaseViewHolder(view){
        val binding = ChildRecyclerHomeChartBinding.bind(view)
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

    interface OnViewAllClickListener {
        fun onViewAllClick(taskType: Int)
    }

    interface OnTeamTaskClickListener {
        fun onTaskClick(task: TeamTask)
    }
}