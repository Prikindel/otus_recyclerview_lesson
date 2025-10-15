package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity(), Listener {

    private val personItems = mutableListOf<PersonItem>()
    private val chatAdapter by lazy { ChatAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        // Генерируем тестовые данные
        generateTestData()

        val rv = findViewById<RecyclerView>(R.id.listView)
        rv.adapter = chatAdapter

        chatAdapter.setList(personItems)
    }

    private fun generateTestData() {
        personItems.clear()
        for (i in 1..1000) {
            personItems.add(
                PersonItem(
                    id = i,
                    name = "Пользователь $i",
                    date = "${i % 24}:${String.format("%02d", i % 60)}",
                    message = "Это сообщение номер $i для демонстрации работы ListView",
                    backgroundColor = if (i % 2 == 0) R.color.green else R.color.blue
                )
            )
        }
    }

    override fun onItemClick(id: Int) {
        Toast.makeText(this, "Clicked on item with id $id", Toast.LENGTH_SHORT).show()
        val index = personItems.indexOfFirst { it.id == id }
        chatAdapter.replace(index, index + 5)
//        val newId = id + 1000
//        val person = PersonItem(
//            id = newId,
//            name = "Пользователь $newId",
//            date = "${newId % 24}:${String.format("%02d", newId % 60)}",
//            message = "Сообщение номер $newId для демонстрации работы ListView",
//            backgroundColor = if (newId % 2 == 0) R.color.green else R.color.blue
//        )
//        val index = personItems.indexOfFirst { it.id == id }
//        personItems.add(index + 1, person)
//        chatAdapter.addItem(person, index + 1)
    }

    override fun onItemPupkinDelete(id: Int) {
        val index = personItems.indexOfFirst { it.id == id }
        personItems.removeIf { it.id == id }
//        chatAdapter.setList(personItems)
        chatAdapter.removeItem(index)
    }
}