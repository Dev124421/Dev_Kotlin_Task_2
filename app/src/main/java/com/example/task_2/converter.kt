package com.example.task_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class converter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_converter)

        val amount = findViewById<EditText>(R.id.amount)
        val btnConvert = findViewById<Button>(R.id.Convertbtn)

        btnConvert.setOnClickListener {
            val amountValue = amount.text.toString().toFloatOrNull() ?: 0f
            Toast.makeText(this, "Hello World!", Toast.LENGTH_LONG).show()
        }
    }
}
