package ru.prike.otus_recyclerview_lesson

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ChatDiffAdapter(
    private val listener: ChatListener
) : ListAdapter<Item, RecyclerView.ViewHolder>(DiffUtilsItem()) {

    fun exchange(startPosition: Int, endPosition: Int) {
        notifyItemMoved(startPosition, endPosition)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.PERSON.id -> PersonViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_item, parent, false), listener
            )
            ViewType.DAY.id -> DayViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item, parent, false),
                listener
            )
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewType = getItemViewType(position)
        val item = getItem(position)
        when (viewType) {
            ViewType.PERSON.id -> (viewHolder as PersonViewHolder).bind(item as PersonItem)
            ViewType.DAY.id -> (viewHolder as DayViewHolder).bind(item as DayItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is PersonItem -> ViewType.PERSON.id
            is DayItem -> ViewType.DAY.id
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    enum class ViewType(val id: Int) {
        DAY(R.layout.day_item),
        PERSON(R.layout.person_item)
    }

    private class DiffUtilsItem : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean = oldItem.id == newItem.id

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Item,
            newItem: Item
        ): Boolean = oldItem == newItem
    }
}