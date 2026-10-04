package com.example.task_2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BMI_History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_history)
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val historyListView = findViewById<ListView>(R.id.Bmi_history)
        val historyList = intent.getStringArrayListExtra("BMI_HISTORY") ?: ArrayList()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            historyList
        )

        historyListView.adapter = adapter
        if (historyList.isEmpty()) {
            Toast.makeText(
                this,
                "No BMI history available",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}