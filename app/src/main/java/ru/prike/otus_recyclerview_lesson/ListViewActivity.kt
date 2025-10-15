package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListViewActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var adapter: PersonAdapter
    private val personItems = mutableListOf<PersonItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        listView = findViewById(R.id.listView)
        
        // Генерируем тестовые данные
        generateTestData()
        
        // Создаем и устанавливаем адаптер
        adapter = PersonAdapter(this, personItems)
        listView.adapter = adapter
    }

    private fun generateTestData() {
        personItems.clear()
        for (i in 1..10000) {
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
