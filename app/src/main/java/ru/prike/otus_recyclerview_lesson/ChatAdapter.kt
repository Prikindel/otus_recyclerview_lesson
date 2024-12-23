package ru.prike.otus_recyclerview_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class ChatAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list = listOf<Item>()

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
//        val item = list.getOrNull(position)
//
//        item?.let {
//            holder.bind(item)
//        }

        when (val item = list.getOrNull(position)) {
            is ChatItem -> (holder as PersonViewHolder).bind(item)
            is DayItem -> (holder as DayViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is ChatItem -> ViewTypes.CHAT.id
            is DayItem -> ViewTypes.DAY.id
            else -> -1
        }
    }

    fun setData(newList: List<Item>) {
        list = newList
        notifyDataSetChanged()
    }

    fun removeItem(id: Int) {
//        val position = list.indexOfFirst { it.id == id }
//        if (position < 0) return
//
//        list = list.filter { it.id != id }
//        notifyItemRemoved(position)

        val position = list.map { it as? ChatItem }.indexOfFirst { it?.id == id }
        if (position < 0) return

        val isDayEmpty = list.getOrNull(position - 1) is DayItem
                && list.getOrNull(position + 1) is DayItem

        list = list.toMutableList().also {
            it.removeAt(position)
            if (isDayEmpty) it.removeAt(position - 1)
        }.toList()

//        notifyItemRemoved(position)
        val startPosition = if (isDayEmpty) position - 1 else position
        val countRemove = if (isDayEmpty) 2 else 1
        notifyItemRangeRemoved(startPosition, countRemove)
    }

    fun changeBackground(index: Int, color: Int) {
        list = list.toMutableList().also {
            val item = (it[index] as? ChatItem)
            it[index] = item?.copy(background = if (item.background != null) null else color) ?: it[index]
//            it[index] = (it[index] as? ChatItem)?.copy(background = color) ?: it[index]
        }.toList()

        notifyItemChanged(index)
    }

    fun exchange(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (index in fromPosition until toPosition) {
                Collections.swap(list, index, index + 1)
            }
        } else {
            for (index in fromPosition downTo toPosition + 1) {
                Collections.swap(list, index, index - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    fun moved() {
        notifyItemMoved(1, 5)
    }

    enum class ViewTypes(val id: Int) {
        DAY(0),
        CHAT(1)
    }
}