package ru.prike.otus_recyclerview_lesson

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ChatDiffAdapter(
    private val listener: Listener
) : ListAdapter<Item, RecyclerView.ViewHolder>(DiffUtilItem()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewTypes.CHAT.id -> {
                println("onCreateViewHolder CHAT")
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_item, parent, false)
                PersonViewHolder(view, listener)
            }
            ViewTypes.DAY.id -> {
                println("onCreateViewHolder DAY")
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item, parent, false)
                DayViewHolder(view, listener)
            }
            else -> throw IllegalArgumentException("Not found view type for chat adapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)/*currentList.getOrNull(position)*/) {
            is ChatItem -> {
                println("onBindViewHolder chat")
                (holder as PersonViewHolder).bind(item)
            }
            is DayItem -> {
                println("onBindViewHolder day")
                (holder as DayViewHolder).bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ChatItem -> ViewTypes.CHAT.id // R.layout.person_item
            is DayItem -> ViewTypes.DAY.id // R.layout.day_item
            else -> -1
        }
    }

    enum class ViewTypes(val id: Int) {
        DAY(R.layout.day_item),
        CHAT(R.layout.person_item)
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