package ru.prike.otus_recyclerview_lesson

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DayViewHolder(
    private val view: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(view) {
    private val title: TextView by lazy { view.findViewById(R.id.day) }
    private val root: View by lazy { view.findViewById(R.id.root) }

    fun bind(dayItem: DayItem) {
        title.text = dayItem.title

        root.setOnClickListener {
            listener.onItemClick(dayItem.id)
        }
    }
}