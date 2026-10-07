package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity(), ChatListener {

    private val personItems = mutableListOf<Item>()
    private val chatAdapter by lazy { ChatAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        generateTestData()

        val rv = findViewById<RecyclerView>(R.id.listView)
        rv.adapter = chatAdapter

        chatAdapter.setList(personItems.toList())
    }

    private fun generateTestData() {
        personItems.clear()
        for (i in 0..100) {
            if (i % 4 == 0) {
                val dayNumber = i / 4
                val day = DayItem(
                    id = i,
                    date = "Day $dayNumber"
                )
                personItems.add(day)
            } else {
                personItems.add(
                    PersonItem(
                        id = i,
                        name = "Пользователь $i",
                        date = "${i % 24}:${String.format("%02d", i % 60)}",
                        message = "Это сообщение номер $i для демонстрации работы ListView"
                    )
                )
            }
        }
    }

    override fun onItemClick(id: Int) {
        val personItem = personItems.find { it.id == id } ?: return
//        Toast.makeText(this, "Клик по ${personItem.name}", Toast.LENGTH_SHORT).show()

        val index = personItems.indexOfFirst { it.id == id }
//        personItems.add(index + 1, personItem.copy(id = personItems.maxOf { it.id } + 1))
//        chatAdapter.addItem(personItems.toList(), index + 1)

        personItems.removeAt(index)
        personItems.add(index + 6, personItem)
//        chatAdapter.replace(personItems, index, index + 6)
    }

    override fun onItemDelete(id: Int) {
        val index = personItems.indexOfFirst { it.id == id }
        personItems.removeAt(index)
//        chatAdapter.removeItem(personItems.toList(), index)
    }
}