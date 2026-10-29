package com.example.task_2

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class converter : AppCompatActivity() {

    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var amount: EditText
    private lateinit var resultamount: TextView
    private lateinit var resultDetails: TextView
    private lateinit var historyList: ListView
    private lateinit var convertBtn: Button
    private lateinit var backButton: ImageButton

    // Single unified list for history items
    private var historyData = ArrayList<String>()
    private lateinit var historyAdapter: ArrayAdapter<String>

    private val currencies = arrayOf(
        "USD - US Dollar",
        "INR - Indian Rupee",
        "EUR - Euro",
        "GBP - British Pound",
        "JPY - Japanese Yen"
    )

    companion object {
        private const val KEY_HISTORY_LIST = "key_history_list"
        private const val KEY_RESULT_AMOUNT = "key_result_amount"
        private const val KEY_RESULT_DETAILS = "key_result_details"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_converter)

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

        spinner1 = findViewById(R.id.spinner1)
        spinner2 = findViewById(R.id.spinner2)
        amount = findViewById(R.id.amount)
        resultamount = findViewById(R.id.resultamount)
        resultDetails = findViewById(R.id.resultDetails)
        historyList = findViewById(R.id.historyList)
        convertBtn = findViewById(R.id.Convertbtn)
        backButton = findViewById(R.id.backButton)

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            currencies
        )
        spinner1.adapter = spinnerAdapter
        spinner2.adapter = spinnerAdapter

        if (savedInstanceState != null) {
            savedInstanceState.getStringArrayList(KEY_HISTORY_LIST)?.let {
                historyData.addAll(it)
            }
            resultamount.text = savedInstanceState.getString(KEY_RESULT_AMOUNT, "€ 0.00")
            resultDetails.text = savedInstanceState.getString(KEY_RESULT_DETAILS, "Enter an amount to convert")
        } else {
            spinner1.setSelection(0)
            spinner2.setSelection(2)
        }

        historyAdapter = ArrayAdapter(
            this,
            R.layout.item_history,
            R.id.historyText,
            historyData
        )
        historyList.adapter = historyAdapter

        backButton.setOnClickListener {
            finish()
        }

        convertBtn.setOnClickListener {
            val input = amount.text.toString().trim()
            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val value = input.toDoubleOrNull()
            if (value == null) {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val fromCurrency = spinner1.selectedItem.toString().substring(0, 3)
            val toCurrency = spinner2.selectedItem.toString().substring(0, 3)
            val convertedValue = convertCurrency(value, fromCurrency, toCurrency)
            val formattedValue = String.format("%.2f", convertedValue)

            resultamount.text = "$toCurrency $formattedValue"
            resultDetails.text = "($value $fromCurrency = $formattedValue $toCurrency)"

            historyData.add(0, "$value $fromCurrency  ➔  $formattedValue $toCurrency")
            historyAdapter.notifyDataSetChanged()

            amount.text.clear()
            amount.clearFocus()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(KEY_HISTORY_LIST, historyData)
        outState.putString(KEY_RESULT_AMOUNT, resultamount.text.toString())
        outState.putString(KEY_RESULT_DETAILS, resultDetails.text.toString())
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

        val amountInUSD = amount / (rates[from] ?: 1.0)
        return amountInUSD * (rates[to] ?: 1.0)
    }
}