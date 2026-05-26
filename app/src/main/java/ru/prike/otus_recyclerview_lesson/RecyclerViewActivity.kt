package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity(), ChatListener {

    private val personItems = mutableListOf<Item>()
//    private val chatAdapter by lazy { ChatAdapter(this) }
    private val chatAdapter by lazy { ChatDiffAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        generateTestData()

        val rv = findViewById<RecyclerView>(R.id.listView)
//        rv.addItemDecoration(
//            DividerItemDecoration(
//                this,
//                DividerItemDecoration.VERTICAL
//            )
//        )
//        ===================================================================
//        rv.addItemDecoration(
//            DividerItemDecoration(
//                this,
//                DividerItemDecoration.VERTICAL
//            ).apply {
//                ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
//                    ?.let(::setDrawable)
//            }
//        )
//        ===================================================================
        rv.addItemDecoration(CustomDecorator())
        ItemTouchHelper(ItemTouchHelperCallback()).attachToRecyclerView(rv)


        rv.adapter = chatAdapter

        updateChat()
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


        val index = personItems.indexOfFirst { it.id == id }
        when (personItems[index]) {
            is PersonItem -> {
                val newInex = index + 3
                personItems.add(newInex, personItems.removeAt(index))

                if (personItems[newInex] !is PersonItem) return
                personItems.removeAt(newInex).let { item ->
                    val newItem = (item as PersonItem).copy(name = "Пользователь ${item.id - 1}")
                    personItems.add(newInex, newItem)
                }
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
        updateChat()
    }

    override fun onItemDelete(id: Int) {
        val index = personItems.indexOfFirst { it.id == id }
        personItems.removeAt(index)
        updateChat()
    }

    private fun updateChat() {
        chatAdapter.submitList(personItems.toList().filter { (it as? PersonItem)?.isHide != true })
//        chatAdapter.setList(personItems.toList().filter { (it as? PersonItem)?.isHide != true })
    }
}