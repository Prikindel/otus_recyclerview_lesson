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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.Day.id -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item, parent, false)
                DayViewHolder(view, listener)
            }

            ViewType.Person.id -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.person_item, parent, false)
                PersonViewHolder(view, listener)
            }

            else -> throw IllegalArgumentException("Not find viewType")
        }
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewType = getItemViewType(position)
        val item = getItem(position)
        when (viewType) {
            ViewType.Day.id -> (viewHolder as DayViewHolder).bind(item as DayItem)
            ViewType.Person.id -> (viewHolder as PersonViewHolder).bind(item as PersonItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is PersonItem -> ViewType.Person.id
            is DayItem -> ViewType.Day.id
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    sealed class ViewType(val id: Int) {
        data object Day : ViewType(R.layout.day_item)
        data object Person : ViewType(R.layout.person_item)
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