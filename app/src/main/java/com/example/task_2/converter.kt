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

    private val historyList = ArrayList<String>()

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

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            currencies
        )

        spinner1.adapter = adapter
        spinner2.adapter = adapter

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

            val convertedValue = convertCurrency(
                value,
                fromCurrency,
                toCurrency
            )

            resultamount.setText(
                String.format("%.2f", convertedValue)
            )

            val history = "$value $fromCurrency → %.2f $toCurrency"
                .format(convertedValue)

            historyList.add(history)

            displayHistory()
        }
    }

    private fun convertCurrency(
        amount: Double,
        from: String,
        to: String
    ): Double {

        val rates = mapOf(
            "USD" to 1.0,
            "INR" to 88.0,
            "EUR" to 0.85,
            "GBP" to 0.74,
            "JPY" to 147.0
        )

        val amountInUSD = amount / rates[from]!!
        return amountInUSD * rates[to]!!
    }

    private fun displayHistory() {

        historyText.text = ""

        for (item in historyList.reversed()) {
            historyText.append("$item\n")
        }
    }
}