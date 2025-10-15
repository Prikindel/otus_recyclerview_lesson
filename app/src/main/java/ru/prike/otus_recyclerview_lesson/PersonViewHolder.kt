package ru.prike.otus_recyclerview_lesson

import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonViewHolder(
    view: View,
    private val listener: Listener
) : RecyclerView.ViewHolder(view) {

    init {
        println("init PersonViewHolder")
    }

    private val name: TextView by lazy { view.findViewById(R.id.name) }
    private val image: ImageView by lazy { view.findViewById(R.id.image) }
    private val message: TextView by lazy { view.findViewById(R.id.message) }
    private val date: TextView by lazy { view.findViewById(R.id.date) }
    private val deleteView: ImageButton by lazy { view.findViewById(R.id.delete) }
    private val root: ViewGroup by lazy { view.findViewById(R.id.root) }

    fun bind(personItem: PersonItem) {
        println("bind PersonViewHolder with id ${personItem.id}")
        name.text = personItem.name
        message.text = personItem.message
        date.text = personItem.date

        root.setBackgroundResource(personItem.backgroundColor)

        root.setOnClickListener {
            listener.onItemClick(personItem.id)
        }

        deleteView.setOnClickListener {
            listener.onItemPupkinDelete(personItem.id)
        }
    }
}