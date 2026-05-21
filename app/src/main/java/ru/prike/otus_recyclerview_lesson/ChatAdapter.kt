package ru.prike.otus_recyclerview_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val listener: ChatListener
) : RecyclerView.Adapter<PersonViewHolder>() {
    private var list = emptyList<PersonItem>()

    fun setList(list: List<PersonItem>) {
        this.list = list.toList()
        notifyDataSetChanged()
    }

    fun addItem(item: PersonItem, index: Int) {
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
    ): PersonViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_item, parent, false)

        return PersonViewHolder(view, listener)
    }

    override fun onBindViewHolder(
        viewHolder: PersonViewHolder,
        position: Int
    ) {
        viewHolder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}