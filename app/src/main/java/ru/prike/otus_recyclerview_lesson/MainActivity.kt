package ru.prike.otus_recyclerview_lesson

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupButtons()
    }

    private fun setupButtons() {
        findViewById<Button>(R.id.btnLinearLayout).setOnClickListener {
            startActivity(Intent(this, LinearLayoutActivity::class.java))
        }

        findViewById<Button>(R.id.btnListView).setOnClickListener {
            startActivity(Intent(this, ListViewActivity::class.java))
        }

        findViewById<Button>(R.id.btnRecyclerView).setOnClickListener {
             startActivity(Intent(this, RecyclerViewActivity::class.java))
        }
    }
}