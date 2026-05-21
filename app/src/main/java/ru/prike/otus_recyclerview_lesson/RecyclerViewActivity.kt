package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.os.PersistableBundle
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

        chatAdapter.setList(personItems)
    }

    private fun generateTestData() {
        personItems.clear()
        for (i in 0..10000) {
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
        Toast.makeText(this, "Clicked on item with id $id", Toast.LENGTH_SHORT).show()

//        val newId = id + 10000
//        val person = PersonItem(
//            id = newId,
//            name = "Пользователь $newId",
//            date = "${newId % 24}:${String.format("%02d", newId % 60)}",
//            message = "Новое сообщение номер $newId"
//        )
//        val index = personItems.indexOfFirst { it.id == id } + 1
//        personItems.add(index, person)
//        chatAdapter.addItem(person, index)

        val index = personItems.indexOfFirst { it.id == id }
        val newInex = index + 3
        personItems.add(newInex, personItems.removeAt(index))
        chatAdapter.replace(index, newInex)
    }

    override fun onItemDelete(id: Int) {
        val index = personItems.indexOfFirst { it.id == id }
        personItems.removeAt(index)
        chatAdapter.removeItem(index)
    }
}