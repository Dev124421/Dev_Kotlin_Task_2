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

    private fun convertCurrency(
        amount: Double,
        from: String,
        to: String
    ): Double {

        // Exchange rates relative to USD
        val rates = mapOf(
            "USD" to 1.0,
            "INR" to 88.0,
            "EUR" to 0.85,
            "GBP" to 0.74,
            "JPY" to 147.0
        )

        // Convert source currency → USD
        val amountInUSD = amount / rates[from]!!

        // Convert USD → target currency
        return amountInUSD * rates[to]!!
    }

    private fun displayHistory() {

        // Clear the TextView first
        historyText.text = ""

        // Display latest history
        for (item in historyList.reversed()) {
            historyText.append("$item\n")
        }
    }
}