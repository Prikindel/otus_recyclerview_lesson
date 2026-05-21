package ru.prike.otus_recyclerview_lesson

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DayViewHolder(
    view: View,
) : RecyclerView.ViewHolder(view) {

    private val title: TextView by lazy { view.findViewById(R.id.day) }

    fun bind(item: DayItem) {
        title.text = item.date
    }
}