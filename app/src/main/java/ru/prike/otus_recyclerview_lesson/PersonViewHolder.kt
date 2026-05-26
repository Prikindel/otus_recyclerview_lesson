package ru.prike.otus_recyclerview_lesson

import android.view.View
import android.widget.TextView
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
    private val root: View by lazy { view.findViewById(R.id.root) }
    private val deleteView: View by lazy { view.findViewById(R.id.delete) }

    fun bind(item: PersonItem) {
//        if (item.isHide) {
//            root.visibility = View.GONE
//            return
//        } else {
//            root.visibility = View.VISIBLE
//        }
        println("bind PersonViewHolder with id ${item.id}")
        name.text = item.name
        message.text = item.message
        date.text = item.date

        root.setBackgroundResource(item.getColor())

        root.setOnClickListener { listener.onItemClick(item.id) }

        deleteView.setOnClickListener { listener.onItemDelete(item.id) }
    }
}