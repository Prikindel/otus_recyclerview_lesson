package ru.prike.otus_recyclerview_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val listener: ChatListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = emptyList<Item>()

    fun setList(list: List<Item>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addItem(list: List<Item>, index: Int) {
        this.list = list
        notifyItemInserted(index)
    }

    fun removeItem(list: List<Item>, index: Int) {
        this.list = list
        notifyItemRemoved(index)
    }

    fun replace(list: List<Item>, from: Int, to: Int) {
        this.list = list
        notifyItemMoved(from, to)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.Day.id -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.day_item, parent, false)
                DayViewHolder(view)
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
        when (viewType) {
            ViewType.Day.id -> (viewHolder as DayViewHolder).bind(list[position] as DayItem)
            ViewType.Person.id -> (viewHolder as PersonViewHolder).bind(list[position] as PersonItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return when (item) {
            is PersonItem -> ViewType.Person.id
            is DayItem -> ViewType.Day.id
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    override fun getItemCount(): Int = list.size

    private sealed class ViewType(val id: Int) {
        data object Day : ViewType(R.layout.day_item)
        data object Person : ViewType(R.layout.person_item)
    }
}