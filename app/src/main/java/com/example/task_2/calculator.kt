package com.example.task_2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class calculator : AppCompatActivity() {

    private val historyList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi_calculator)
        val heightInput = findViewById<EditText>(R.id.height)
        val weightInput = findViewById<EditText>(R.id.weight)
        val heightUnitSpinner = findViewById<Spinner>(R.id.heightUnitSpinner)
        val weightUnitSpinner = findViewById<Spinner>(R.id.weightUnitSpinner)
        val calculateBtn = findViewById<Button>(R.id.btnCalculate)
        val historyButton = findViewById<Button>(R.id.Historybtn)
        val bmiValue = findViewById<TextView>(R.id.BMIvalue)
        val categoryType =findViewById<TextView>(R.id.Categorytype)
        val description =findViewById<TextView>(R.id.Description)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        val heightUnits = arrayOf(
            "cm",
            "m",
            "ft"
        )

        val heightAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            heightUnits
        )

        heightUnitSpinner.adapter = heightAdapter
        val weightUnits = arrayOf(
            "kg",
            "g"
        )

        val weightAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            weightUnits
        )

        weightUnitSpinner.adapter = weightAdapter

        heightUnitSpinner.setSelection(0)
        weightUnitSpinner.setSelection(0)

        calculateBtn.setOnClickListener {
            val heightText = heightInput.text.toString().trim()
            val weightText = weightInput.text.toString().trim()

            if (heightText.isEmpty() || weightText.isEmpty()) {
                Toast.makeText(
                    this,
                    "Please enter height and weight",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val heightValue = heightText.toDoubleOrNull()
            val weightValue = weightText.toDoubleOrNull()

            if (heightValue == null || weightValue == null) {
                Toast.makeText(
                    this,
                    "Please enter valid values",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (heightValue <= 0 || weightValue <= 0) {
                Toast.makeText(
                    this,
                    "Values must be greater than zero",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val heightUnit = heightUnitSpinner.selectedItem.toString()

            val heightM = when (heightUnit) {
                "cm" -> heightValue / 100
                "m" -> heightValue
                "ft" -> heightValue * 0.3048
                else -> heightValue / 100
            }

            val weightUnit = weightUnitSpinner.selectedItem.toString()

            val weightKg = when (weightUnit) {
                "kg" -> weightValue
                "g" -> weightValue / 1000
                else -> weightValue
            }

            val bmi = weightKg / (heightM * heightM)
            val category: String
            val desc: String

            when {
                bmi < 18.5 -> {
                    category = "Underweight"
                    desc = "Your BMI is below the normal range."
                }
                bmi < 25 -> {
                    category = "Healthy"
                    desc = "Normal weight"
                }
                bmi < 30 -> {
                    category = "Overweight"
                    desc = "You are slightly overweight."
                }
                else -> {
                    category = "Obese"
                    desc = "Your BMI is in the obese range."
                }
            }
            val formattedBMI = String.format("%.1f", bmi)

            bmiValue.text = formattedBMI
            categoryType.text = category
            description.text = desc

            val history = "BMI: $formattedBMI | $category"
            historyList.add(history)

            heightInput.text.clear()
            weightInput.text.clear()
            heightUnitSpinner.setSelection(0)
            weightUnitSpinner.setSelection(0)
        }
        historyButton.setOnClickListener {
            val intent = Intent(this, BMI_History::class.java)
            intent.putStringArrayListExtra(
                "BMI_HISTORY",
                historyList
            )
            startActivity(intent)
        }
    }
}