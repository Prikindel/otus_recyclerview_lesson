package ru.prike.otus_recyclerview_lesson

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DayViewHolder(
    private val view: View
) : RecyclerView.ViewHolder(view) {

    private val dayView: TextView by lazy { view.findViewById(R.id.day) }

    fun bind(item: DayItem) {
        dayView.text = item.title
    }
}