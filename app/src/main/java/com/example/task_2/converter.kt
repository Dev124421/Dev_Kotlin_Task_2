package com.example.task_2

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class converter : AppCompatActivity() {

    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var amount: EditText
    private lateinit var resultamount: EditText
    private lateinit var convertBtn: Button
    private lateinit var historyText: TextView

    // List to store conversion history
    private val historyList = ArrayList<String>()

    // Currency names
    private val currencies = arrayOf(
        "USD",
        "INR",
        "EUR",
        "GBP",
        "JPY"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)

        // Connect XML elements
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        amount = findViewById(R.id.amount)
        resultamount = findViewById(R.id.resultamount)
        convertBtn = findViewById(R.id.Convertbtn)
        historyText = findViewById(R.id.History)

        // Create Spinner adapter
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            currencies
        )

        spinner1.adapter = adapter
        spinner2.adapter = adapter

        // Convert button
        convertBtn.setOnClickListener {

            val input = amount.text.toString()

            // Check if amount is empty
            if (input.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter an amount",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val value = input.toDouble()

            val fromCurrency = spinner1.selectedItem.toString()
            val toCurrency = spinner2.selectedItem.toString()

            // Convert currency
            val convertedValue = convertCurrency(
                value,
                fromCurrency,
                toCurrency
            )

            // Display result
            resultamount.setText(
                String.format("%.2f", convertedValue)
            )

            // Add conversion to history
            val history = "$value $fromCurrency → %.2f $toCurrency"
                .format(convertedValue)

            historyList.add(history)

            // Display history
            displayHistory()
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