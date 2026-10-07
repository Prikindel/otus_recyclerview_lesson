package ru.prike.otus_recyclerview_lesson

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

private var countCreatedViewHolders = 0

class PersonViewHolder(
    view: View,
    private val listener: ChatListener
) : RecyclerView.ViewHolder(view) {

    init {
        println("PersonViewHolder created ${++countCreatedViewHolders}")
    }

    private val name: TextView by lazy { view.findViewById(R.id.name) }
    private val message: TextView by lazy { view.findViewById(R.id.message) }
    private val date: TextView by lazy { view.findViewById(R.id.date) }
    private val icon: ImageView by lazy { view.findViewById(R.id.image) }
    private val root: ViewGroup by lazy { view.findViewById(R.id.root) }
    private val deleteButton: View by lazy { view.findViewById(R.id.delete) }

    fun bind(item: PersonItem) {
        println("PersonViewHolder bind with id ${item.id}")
        name.text = item.name
        message.text = item.message
        date.text = item.date
        icon.setImageResource(R.drawable.outline_person_24)

        if (item.id % 2 == 0) root.setBackgroundResource(R.color.red)
        else root.setBackgroundResource(R.color.blue)

        root.setOnClickListener { listener.onItemClick(item.id) }
        deleteButton.setOnClickListener { listener.onItemDelete(item.id) }
    }
}