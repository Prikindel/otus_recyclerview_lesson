package ru.prike.otus_recyclerview_lesson

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DayViewHolder(
    private val view: View,
    private val listener: Listener,
) : RecyclerView.ViewHolder(view) {

    private val dayView: TextView by lazy { view.findViewById(R.id.day) }
    private val root: ViewGroup by lazy { view.findViewById(R.id.root) }

    fun bind(item: DayItem) {
        dayView.text = item.title
        root.setOnClickListener { listener.onItemClicked(item.id) }
    }
}