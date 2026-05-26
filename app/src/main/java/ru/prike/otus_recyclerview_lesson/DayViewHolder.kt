package ru.prike.otus_recyclerview_lesson

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DayViewHolder(
    view: View,
    private val listener: ChatListener
) : RecyclerView.ViewHolder(view) {

    private val title: TextView by lazy { view.findViewById(R.id.day) }
    private val root: View by lazy {  view.findViewById(R.id.root) }

    fun bind(item: DayItem) {
        title.text = item.date

        root.setOnClickListener { listener.onItemClick(item.id) }
    }
}