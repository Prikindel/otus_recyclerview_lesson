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
//        val item = list.getOrNull(position)
//
//        item?.let {
//            holder.bind(item)
//        }

        when (val item = list.getOrNull(position)) {
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

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is ChatItem -> ViewTypes.CHAT.id // R.layout.person_item
            is DayItem -> ViewTypes.DAY.id // R.layout.day_item
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

    fun moved() {
        notifyItemMoved(1, 5)
    }

    fun setData(newList: List<Item>, position: Int, addCount: Int) {
        list = newList
        notifyItemRangeInserted(position, addCount)
    }

    fun exchange(startPosition: Int, endPosition: Int) {
       if (startPosition < endPosition) {
           for (index in startPosition until endPosition) {
               Collections.swap(list, index, index + 1)
           }
       } else {
           for (index in endPosition until startPosition) {
               Collections.swap(list, index, index - 1)
           }
       }
        notifyItemMoved(startPosition, endPosition)
    }

    enum class ViewTypes(val id: Int) {
        DAY(R.layout.day_item),
        CHAT(R.layout.person_item)
    }
}