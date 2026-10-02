package com.example.task_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class calculator : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bmi_calculator)

        // Input fields
        val weightInput = findViewById<EditText>(R.id.weight)
        val heightInput = findViewById<EditText>(R.id.height)

        // Button
        val calculateBtn = findViewById<Button>(R.id.btnCalculate)

        // Output TextViews
        val bmiValue = findViewById<TextView>(R.id.BMIvalue)
        val categoryType = findViewById<TextView>(R.id.Categorytype)
        val description = findViewById<TextView>(R.id.Description)

        calculateBtn.setOnClickListener {

            // Check empty inputs
            if (weightInput.text.isEmpty() || heightInput.text.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter height and weight",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Get input values
            val weight = weightInput.text.toString().toDouble()
            val heightCm = heightInput.text.toString().toDouble()

            // Convert height from cm to meters
            val heightM = heightCm / 100

            // Calculate BMI
            val bmi = weight / (heightM * heightM)

            // Determine category and description
            val category: String
            val desc: String

            when {
                bmi < 18.5 -> {
                    category = "Underweight"
                    desc = "Your BMI is below the normal range."
                }

                bmi < 25 -> {
                    category = "Normal weight"
                    desc = "Your BMI is within the normal range."
                }

                bmi < 30 -> {
                    category = "Overweight"
                    desc = "Your BMI is above the normal range."
                }

                else -> {
                    category = "Obese"
                    desc = "Your BMI is in the obese range."
                }
            }

            // Display result
            bmiValue.text = "%.2f".format(bmi)
            categoryType.text = category
            description.text = desc

            // Toast message
            Toast.makeText(
                this,
                "BMI: %.2f\nCategory: $category".format(bmi),
                Toast.LENGTH_LONG
            ).show()
        }
        val historybtn = findViewById<Button>(R.id.BMIhistory)
        historybtn.setOnClickListener {
        val historyitent = Intent(this, BMI_History::class.java)
        startActivity(historyitent)
    }
    }
}