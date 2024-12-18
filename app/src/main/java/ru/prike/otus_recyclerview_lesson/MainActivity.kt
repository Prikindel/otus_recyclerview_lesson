package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Listener {

    private val adapter: ChatAdapter by lazy { ChatAdapter(this) }

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

        recyclerView.adapter = adapter
        list = generateList()
        adapter.setData(list)
    }

    override fun onResume() {
        super.onResume()

//        lifecycleScope.launch {
//            delay(1000)
//            adapter.moved()
//            delay(1000)
//            adapter.moved()
//            delay(1000)
//            adapter.moved()
//            delay(1000)
//            adapter.moved()
//        }
    }

    override fun onItemClicked(id: Int) {
        Toast.makeText(this, "Clicked $id item", Toast.LENGTH_SHORT).show()
    }

    override fun onItemActionClicked(id: Int) {
//        list = list.filter { it.id != id }
        adapter.removeItem(id)
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
                    background = if (it % 2 == 0) R.color.blue else R.color.green
                )
                list.add(personItem)
            }
        }

        list.toList()
    }
}