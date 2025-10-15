package ru.prike.otus_recyclerview_lesson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class LinearLayoutActivity : AppCompatActivity() {

    private lateinit var linearLayoutContainer: ViewGroup
    private val personItems = mutableListOf<PersonItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_linear_layout)

        linearLayoutContainer = findViewById(R.id.linearLayoutContainer)
        
        // Генерируем тестовые данные
        generateTestData()
        
        // Заполняем LinearLayout элементами
        populateLinearLayout()
    }

    private fun generateTestData() {
        personItems.clear()
        for (i in 1..1000) {
            personItems.add(
                PersonItem(
                    id = i,
                    name = "Пользователь $i",
                    date = "${i % 24}:${String.format("%02d", i % 60)}",
                    message = "Это сообщение номер $i для демонстрации работы LinearLayout"
                )
            )
        }
    }

    private fun populateLinearLayout() {
        // Очищаем контейнер
        linearLayoutContainer.removeAllViews()
        
        // Создаем View для каждого элемента
        personItems.forEach { personItem ->
            val itemView = createPersonItemView(personItem)
            linearLayoutContainer.addView(itemView)
        }
    }

    private fun createPersonItemView(personItem: PersonItem): View {
        println("create person with id ${personItem.id}")
        // Инфлейтим layout для элемента списка
        val itemView = LayoutInflater.from(this)
            .inflate(R.layout.person_item, linearLayoutContainer, false)
        
        // Находим View элементы
        val nameTextView = itemView.findViewById<TextView>(R.id.name)
        val messageTextView = itemView.findViewById<TextView>(R.id.message)
        val dateTextView = itemView.findViewById<TextView>(R.id.date)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.delete)
        
        // Заполняем данными
        nameTextView.text = personItem.name
        messageTextView.text = personItem.message
        dateTextView.text = personItem.date
        
        // Устанавливаем обработчик клика на элемент
        itemView.setOnClickListener {
            Toast.makeText(this, "Клик по ${personItem.name}", Toast.LENGTH_SHORT).show()
        }
        
        // Устанавливаем обработчик клика на кнопку удаления
        deleteButton.setOnClickListener {
            removePersonItem(personItem)
        }
        
        return itemView
    }

    private fun removePersonItem(personItem: PersonItem) {
        // Удаляем из списка данных
        personItems.remove(personItem)
        
        // Пересоздаем весь список (неэффективно!)
        populateLinearLayout()
        
        Toast.makeText(this, "Удален ${personItem.name}", Toast.LENGTH_SHORT).show()
    }
}
