package com.example.gph_kt_android_taks.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import com.example.gph_kt_android_taks.R
import com.example.gph_kt_android_taks.data.entities.Course
import kotlinx.android.synthetic.main.course_item.view.*

class CoursesAdapter(private val interaction: Interaction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Course>() {

        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return  oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return CoursesAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.course_item,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CoursesAdapterViewHolder -> {
                holder.bind(differ.currentList.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<Course>) {
        differ.submitList(list)
    }

    class CoursesAdapterViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Course) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            itemView.tv_course_name.text = item.courseName
            itemView.tv_city.text = item.city
            itemView.tv_place.text = item.provider
            itemView.tv_date.text = item.date
            itemView.tv_duration.text =  "${item.duration} ايام "
            itemView.tv_rate.text = item.rate

        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: Course)
    }
}
