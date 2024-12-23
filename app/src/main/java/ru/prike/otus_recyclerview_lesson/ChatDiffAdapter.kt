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
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_item, parent, false)
                PersonViewHolder(view, listener)
            }
            ViewTypes.DAY.id -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item, parent, false)
                DayViewHolder(view)
            }
            else -> throw IllegalArgumentException("Not found view type for chat adapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = currentList.getOrNull(position)) {
            is ChatItem -> (holder as PersonViewHolder).bind(item)
            is DayItem -> (holder as DayViewHolder).bind(item)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is ChatItem -> ViewTypes.CHAT.id
            is DayItem -> ViewTypes.DAY.id
            else -> -1
        }
    }

    enum class ViewTypes(val id: Int) {
        DAY(0),
        CHAT(1)
    }
}

class DiffUtilItem : DiffUtil.ItemCallback<Item>() {

    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        if (oldItem::class != newItem::class) return false

        when {
            oldItem is ChatItem && newItem is ChatItem -> return oldItem.id == newItem.id
            oldItem is DayItem && newItem is DayItem -> return oldItem.id == newItem.id
            else -> return false
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }

}