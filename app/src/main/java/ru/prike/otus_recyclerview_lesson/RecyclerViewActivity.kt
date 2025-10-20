package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class RecyclerViewActivity : AppCompatActivity(), Listener {

    private val personItems = mutableListOf<Item>()
    private val chatAdapter by lazy { ChatAdapter(this) }
    private val chatDiffAdapter by lazy { ChatDiffAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        // Генерируем тестовые данные
        generateTestData()

        val rv = findViewById<RecyclerView>(R.id.listView)
        rv.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL).apply {
                ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
                    ?.let { setDrawable(it) }
            }
        )
//        rv.addItemDecoration(CustomDecorator())

        ItemTouchHelper(ItemTouchHelperCallback()).attachToRecyclerView(rv)

        rv.adapter = chatAdapter
//        rv.adapter = chatDiffAdapter

        chatAdapter.setList(personItems)
//        chatDiffAdapter.submitList(personItems.toList())
    }

    private fun generateTestData() {
        personItems.clear()
        for (i in 0..1000) {
            if (i % 4 == 0) {
                val day = i / 4
                personItems.add(
                    DayItem(
                        id = day * 1000 + 1,
                        title = "Day $day"
                    )
                )
            } else {
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
    }

    override fun onItemClick(id: Int) {
        Toast.makeText(this, "Clicked on item with id $id", Toast.LENGTH_SHORT).show()

        val index = personItems.indexOfFirst { it.id == id }
        val item = personItems[index]
        when (item) {
            is PersonItem -> {
                personItems.add(index + 5, item)
                personItems.removeAt(index)
            }
            is DayItem -> {
                for (i in index + 1 until personItems.size) {
                    if (personItems[i] is DayItem) break
                    else {
                        val person = personItems[i] as PersonItem
                        personItems.removeAt(i)
                        personItems.add(i, person.copy(isHide = !person.isHide))
                    }
                }
            }
        }

        chatDiffAdapter.submitList(personItems.toList().filter { (it as? PersonItem)?.isHide != true })

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
//        val index = personItems.indexOfFirst { it.id == id }
        personItems.removeIf { it.id == id }
//        chatAdapter.setList(personItems)
//        chatAdapter.removeItem(index)
        chatDiffAdapter.submitList(personItems.toList())
    }

    override fun exchange(from: Int, to: Int) {
        if (from < to) {
            for (index in from until to) {
                Collections.swap(personItems, index, index + 1)
            }
        } else {
            for (index in to until from) {
                Collections.swap(personItems, index, index - 1)
            }
        }
//        chatDiffAdapter.submitList(personItems.toList())
    }
}