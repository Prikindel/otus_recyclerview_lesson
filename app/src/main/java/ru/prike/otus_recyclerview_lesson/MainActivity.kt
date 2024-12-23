package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Listener {

//    private val adapter: ChatAdapter by lazy { ChatAdapter(this) }
private val adapter: ChatDiffAdapter by lazy { ChatDiffAdapter(this) }

    private var list: List<Item> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.addItemDecoration(
            CustomDecorator()
        )

        ItemTouchHelper(ItemTouchCallback()).attachToRecyclerView(recyclerView)

//        recyclerView.recycledViewPool.setMaxRecycledViews(
//            ChatAdapter.ViewTypes.CHAT.id,
//            10
//        )
//        recyclerView.setItemViewCacheSize(4)

        recyclerView.adapter = adapter
        list = generateList()
        adapter.submitList(list)
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            delay(1000)
            moved()
            delay(1000)
            moved()
            delay(1000)
            moved()
            delay(1000)
            moved()
        }
    }

    private fun moved() {
        val item = list.get(1)
        list = list.toMutableList().also {
            it.remove(item)
            it.add(5, item)
        }.toList()
        adapter.submitList(list)
    }

    override fun onItemClicked(id: Int) {
        Toast.makeText(this, "Clicked $id item", Toast.LENGTH_SHORT).show()
//        adapter.changeBackground(id, android.R.color.holo_red_light)
    }

    override fun onItemActionClicked(id: Int) {
        val chatItem = list.find { (it as? ChatItem)?.id == id }
        list = list.filter { chatItem != it }
        adapter.submitList(list)
//        adapter.removeItem(id)
    }

    fun generateList() = run {
        val list = mutableListOf<Item>()
        repeat(40) {
            if (it % 4 == 0) {
                list.add(
                    DayItem(it, "Day ${it / 4}")
                )
            } else {
                val personItem = ChatItem(
                    id = it,
                    name = "Name $it",
                    message = "This is message",
                    date = "12:05",
                    background = null
                )
                list.add(personItem)
            }
        }

        list.toList()
    }
}