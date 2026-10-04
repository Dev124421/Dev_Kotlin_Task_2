package com.example.task_2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class BMI_History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_history)

        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            Intent(this , calculator::class.java)
        }
    }
}