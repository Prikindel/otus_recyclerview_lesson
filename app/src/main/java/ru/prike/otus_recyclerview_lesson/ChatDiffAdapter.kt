package ru.prike.otus_recyclerview_lesson

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class ChatDiffAdapter(
    private val listener: Listener
) : ListAdapter<Item, RecyclerView.ViewHolder>(DiffUtilItem()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.PERSON.id -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_item, parent, false)

                PersonViewHolder(view, listener)
            }
            ViewType.DAY.id -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item, parent, false)

                DayViewHolder(view, listener)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is PersonItem -> ViewType.PERSON.id
            is DayItem -> ViewType.DAY.id
            else -> -1
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (getItemViewType(position)) {
            ViewType.PERSON.id -> (viewHolder as PersonViewHolder).bind(item as PersonItem)
            ViewType.DAY.id -> (viewHolder as DayViewHolder).bind(item as DayItem)
        }
    }

    fun exchange(from: Int, to: Int) {
//        if (from < to) {
//            for (index in from until to) {
//                Collections.swap(currentList, index, index + 1)
//            }
//        } else {
//            for (index in to until from) {
//                Collections.swap(currentList, index, index - 1)
//            }
//        }
        notifyItemMoved(from, to)
        listener.exchange(from, to)
    }

    enum class ViewType(val id: Int) {
        DAY(R.layout.day_item),
        PERSON(R.layout.person_item)
    }
}

private class DiffUtilItem : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem::class != newItem::class) return false

        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}