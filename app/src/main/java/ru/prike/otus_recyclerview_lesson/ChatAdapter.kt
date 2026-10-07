package ru.prike.otus_recyclerview_lesson

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val listener: ChatListener
) : RecyclerView.Adapter<PersonViewHolder>() {

    private var list = emptyList<PersonItem>()

    fun setList(list: List<PersonItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun addItem(list: List<PersonItem>, index: Int) {
        this.list = list
        notifyItemInserted(index)
    }

    fun removeItem(list: List<PersonItem>, index: Int) {
        this.list = list
        notifyItemRemoved(index)
    }

    fun replace(list: List<PersonItem>, from: Int, to: Int) {
        this.list = list
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