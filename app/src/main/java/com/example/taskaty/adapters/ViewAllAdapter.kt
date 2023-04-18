//package com.example.taskaty.adapters
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.taskaty.R
//import com.example.taskaty.data.CardItemsInViewAll
//import com.example.taskaty.databinding.ItemInViewAllAndSearchBinding
//import com.example.taskaty.databinding.ItemInViewAllBinding
//import com.example.taskaty.domain.entities.Task
//
//class ViewAllAdapter(private val viewAllList:MutableList<Task>):
//    RecyclerView.Adapter<ViewAllAdapter.ViewAllHolder>() {
//
//
//    class ViewAllHolder(itemView:View):RecyclerView.ViewHolder(itemView){
//        val itemInViewAllBinding = ItemInViewAllAndSearchBinding.bind(itemView)
//        val binding= itemInViewAllBinding.bind(itemView)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllHolder {
//        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_in_view_all_and_search,parent,false)
//        return ViewAllHolder(view)
//    }
//
//    override fun getItemCount()=viewAllList.size
//
//    override fun onBindViewHolder(holder: ViewAllHolder, position: Int) {
//        val item=viewAllList[position]
//        holder.binding.apply{
//            textTitle.text=item.title
//             textContent.text=item.content
//            textCalender.text=item.calendarText
//            textClock.text=item.clockText
//            textState.text=item.stateText
//
//        }
//
//    }
//}