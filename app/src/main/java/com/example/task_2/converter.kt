package com.example.task_2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class converter : AppCompatActivity() {

    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var amount: EditText
    private lateinit var resultamount: TextView
    private lateinit var resultDetails: TextView
    private lateinit var fromCurrencyList: ListView
    private lateinit var toCurrencyList: ListView
    private lateinit var convertBtn: Button
    private lateinit var backButton: ImageButton
    private val fromList = ArrayList<String>()
    private val toList = ArrayList<String>()
    private val currencies = arrayOf(
        "USD - US Dollar",
        "INR - Indian Rupee",
        "EUR - Euro",
        "GBP - British Pound",
        "JPY - Japanese Yen"
    )
    private lateinit var fromAdapter: ArrayAdapter<String>
    private lateinit var toAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)
        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        amount = findViewById(R.id.amount)
        resultamount = findViewById(R.id.resultamount)
        resultDetails = findViewById(R.id.resultDetails)
        fromCurrencyList = findViewById(R.id.FromCurrency)
        toCurrencyList = findViewById(R.id.ToCurrency)
        convertBtn = findViewById(R.id.Convertbtn)
        backButton = findViewById(R.id.backButton)


        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            currencies
        )

        spinner1.adapter = spinnerAdapter
        spinner2.adapter = spinnerAdapter

        spinner1.setSelection(0)
        spinner2.setSelection(2)

        fromAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            fromList
        )

        toAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            toList
        )

        fromCurrencyList.adapter = fromAdapter
        toCurrencyList.adapter = toAdapter

        backButton.setOnClickListener {
            finish()
        }
        convertBtn.setOnClickListener {

            val input = amount.text.toString().trim()
            if (input.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter an amount",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val value = input.toDoubleOrNull()
            if (value == null) {
                Toast.makeText(
                    this,
                    "Please enter a valid amount",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val fromCurrency = spinner1.selectedItem.toString().substring(0, 3)
            val toCurrency = spinner2.selectedItem.toString().substring(0, 3)

            val convertedValue = convertCurrency(
                value,
                fromCurrency,
                toCurrency
            )
            val formattedValue = String.format("%.2f", convertedValue)

            resultamount.text = "$toCurrency $formattedValue"
            resultDetails.text = "($value $fromCurrency = $formattedValue $toCurrency)"
            fromList.add("$value $fromCurrency")
            toList.add("$formattedValue $toCurrency")
            fromAdapter.notifyDataSetChanged()
            toAdapter.notifyDataSetChanged()
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
}