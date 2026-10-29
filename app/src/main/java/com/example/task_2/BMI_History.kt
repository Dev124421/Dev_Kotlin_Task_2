package com.example.task_2

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BMI_History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bmi_history)

        val headerLayout = findViewById<View>(R.id.headerLayout)
        val mainLayout = findViewById<View>(R.id.main)

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { _, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            headerLayout.setPadding(
                headerLayout.paddingLeft,
                systemBars.top + 10,
                headerLayout.paddingRight,
                10
            )
            mainLayout.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        val historyListView = findViewById<ListView>(R.id.Bmi_history)
        val emptyStateText = findViewById<TextView>(R.id.tvEmptyHistory)
        val historyList = intent.getStringArrayListExtra("BMI_HISTORY") ?: ArrayList()

        val adapter = ArrayAdapter(
            this,
            R.layout.item_history,
            R.id.historyText,
            historyList
        )
        historyListView.adapter = adapter

        if (historyList.isEmpty()) {
            emptyStateText.visibility = View.VISIBLE
            historyListView.visibility = View.GONE
        } else {
            emptyStateText.visibility = View.GONE
            historyListView.visibility = View.VISIBLE
        }
    }
}