package ru.prike.otus_recyclerview_lesson

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonViewHolder(
    private val view: View,
    private val listener: Listener,
) : RecyclerView.ViewHolder(view) {

    init {
        println(this)
    }

    private val name: TextView by lazy { view.findViewById(R.id.name) }
    private val image: ImageView by lazy { view.findViewById(R.id.image) }
    private val message: TextView by lazy { view.findViewById(R.id.message) }
    private val date: TextView by lazy { view.findViewById(R.id.date) }
    private val root: ViewGroup by lazy { view.findViewById(R.id.root) }
    private val delete: View by lazy { view.findViewById(R.id.delete) }

    fun bind(item: ChatItem) {
        println("bind item ${item.id}")
        name.text = item.name
        message.text = item.message
        date.text = item.date
        image.setImageResource(R.drawable.icon_phone_android_24)

        if (item.background != null) root.setBackgroundResource(item.background)
        else root.background = null

        root.setOnClickListener { listener.onItemClicked(item.id) }
        delete.setOnClickListener { listener.onItemActionClicked(item.id) }
    }
}