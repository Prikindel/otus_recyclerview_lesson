package ru.prike.otus_recyclerview_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val listener: ChatListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var list = emptyList<Item>()

    fun setList(list: List<Item>) {
        this.list = list.toList()
        notifyDataSetChanged()
    }

    fun addItem(item: Item, index: Int) {
        list = list.toMutableList().apply { add(index, item) }.toList()
        notifyItemInserted(index)
    }

    fun removeItem(index: Int) {
        list = list.toMutableList().apply { removeAt(index) }.toList()
        notifyItemRemoved(index)
    }

    fun replace(from: Int, to: Int) {
        list = list.toMutableList().apply {
            add(to, removeAt(from))
        }
        notifyItemMoved(from, to)
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
                    .inflate(R.layout.day_item, parent, false)
            )
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(
        viewHolder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val viewType = getItemViewType(position)
        when (viewType) {
            ViewType.PERSON.id -> (viewHolder as PersonViewHolder).bind(list[position] as PersonItem)
            ViewType.DAY.id -> (viewHolder as DayViewHolder).bind(list[position] as DayItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = list[position]
        return when (item) {
            is PersonItem -> ViewType.PERSON.id
            is DayItem -> ViewType.DAY.id
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    override fun getItemCount(): Int = list.size

    private enum class ViewType(val id: Int) {
        DAY(R.layout.day_item),
        PERSON(R.layout.person_item)
    }
}