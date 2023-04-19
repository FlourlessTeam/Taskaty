package com.example.taskaty.app.ui.fragments.home.adapters

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskaty.R
import com.example.taskaty.databinding.ChildRecyclerHomeChartBinding
import com.example.taskaty.databinding.ChildRecyclerHomePersonalDoneBinding
import com.example.taskaty.databinding.ChildRecyclerHomePersonalInprogressBinding
import com.example.taskaty.databinding.ChildRecyclerHomePersonalUpcomingBinding
import com.example.taskaty.domain.entities.PersonalTask
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.util.*

class ParentPersonalAdapter(
    val InProgress: List<PersonalTask>,
    val Upcoming: List<PersonalTask>,
    val Done: List<PersonalTask>
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
            is ChartViewHolder ->bindChart(holder)
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
        val inputDateFormat = SimpleDateFormat(INPUT_DATE_PATTERN, Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(OUTPUT_DATE_PATTERN, Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat(OUTPUT_TIME_PATTERN, Locale.getDefault())

        holder.binding.apply {
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
            tasksNumber.text = Done.size.toString()
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
    private fun bindChart(holder: ChartViewHolder) {
        val totalTasks = Done.size + InProgress.size + Upcoming.size
        val upComingStatesValue =  (Upcoming.size*100 ) /totalTasks
        val doneStatesValue =  (Done.size*100 ) /totalTasks
        val inProgressStatesValue =  (InProgress.size*100 ) /totalTasks
        holder.binding.apply {
            todoStates.text = "$upComingStatesValue %"
            doneStates.text = "$doneStatesValue %"
            inProgressStates.text = "$inProgressStatesValue %"
            chart.setDrawHoleEnabled(true)
            chart.setUsePercentValues(false)
            chart.setDrawEntryLabels(false)
            chart.holeRadius = 70f
            chart.setCenterText("Total \n$totalTasks")
            chart.setCenterTextSize(11F)
            chart.getDescription().setEnabled(false)
            chart.legend.isEnabled = false
            val entries = ArrayList<PieEntry>();
            entries.add(PieEntry(Upcoming.size*1f, "Todo"))
            entries.add(PieEntry(Done.size*1f, "Done"))
            entries.add(PieEntry(InProgress.size*1f, "In Progress"))
            val colors = ArrayList<Int>();
            colors.add(Color.parseColor("#7FBAA9"))
            colors.add(Color.parseColor("#93CB80"))
            colors.add(Color.parseColor("#418E77"))
            val dataSet = PieDataSet(entries, "");
            dataSet.setColors(colors);
            val data = PieData(dataSet);
            data.setDrawValues(false);
            data.setValueFormatter(PercentFormatter(chart));
            data.setValueTextSize(12f);
            data.setValueTextColor(Color.BLACK);
            chart.setData(data);
            chart.invalidate();
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
        val binding = ChildRecyclerHomeChartBinding.bind(view)
    }

    class DoneViewHolder(view: View) : BaseViewHolder(view) {
        val binding = ChildRecyclerHomePersonalDoneBinding.bind(view)
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
}