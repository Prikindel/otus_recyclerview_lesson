package ru.prike.otus_recyclerview_lesson

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class PersonAdapter(
    private val context: Context,
    private val personItems: MutableList<PersonItem>
) : BaseAdapter() {

    override fun getCount(): Int = personItems.size

    override fun getItem(position: Int): PersonItem = personItems[position]

    override fun getItemId(position: Int): Long = personItems[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            println("create new view")
            // Создаем новый View
            view = LayoutInflater.from(context).inflate(R.layout.person_item, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            println("reuse view")
            // Переиспользуем существующий View
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val personItem = getItem(position)
        println("getView with id ${personItem.id}")
        
        // Заполняем данными
        viewHolder.nameTextView.text = personItem.name
        viewHolder.messageTextView.text = personItem.message
        viewHolder.dateTextView.text = personItem.date

        // Устанавливаем обработчики кликов
        view.setOnClickListener {
            Toast.makeText(context, "Клик по ${personItem.name}", Toast.LENGTH_SHORT).show()
        }

        viewHolder.deleteButton.setOnClickListener {
            removeItem(position)
        }

        return view
    }

    private fun removeItem(position: Int) {
        if (position >= 0 && position < personItems.size) {
            val removedItem = personItems.removeAt(position)
            notifyDataSetChanged() // Уведомляем об изменении
            Toast.makeText(context, "Удален ${removedItem.name}", Toast.LENGTH_SHORT).show()
        }
    }

    // ViewHolder для оптимизации (но все равно не так эффективно как в RecyclerView)
    private class ViewHolder(view: View) {
        val nameTextView: TextView = view.findViewById(R.id.name)
        val messageTextView: TextView = view.findViewById(R.id.message)
        val dateTextView: TextView = view.findViewById(R.id.date)
        val deleteButton: ImageButton = view.findViewById(R.id.delete)
    }
}
