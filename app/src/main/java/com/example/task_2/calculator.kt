package com.example.task_2

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

        val weightInput = findViewById<EditText>(R.id.weight)
        val heightInput = findViewById<EditText>(R.id.height)
        val calculateBtn = findViewById<Button>(R.id.btnCalculate)
        val result = findViewById<TextView>(R.id.result)

        calculateBtn.setOnClickListener {

            // Check if fields are empty
            if (weightInput.text.isEmpty() || heightInput.text.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please enter height and weight",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Get values
            val weight = weightInput.text.toString().toDouble()
            val heightCm = heightInput.text.toString().toDouble()

            // Convert cm to meters
            val heightM = heightCm / 100

            // Calculate BMI
            val bmi = weight / (heightM * heightM)

            // Determine category
            val category = when {
                bmi < 18.5 -> "Underweight"
                bmi < 25 -> "Normal weight"
                else -> "Overweight"
            }

            // Display result in TextView
            result.text = "BMI: %.2f\n$category".format(bmi)

            // Display result using Toast
            Toast.makeText(
                this,
                "BMI: %.2f\n$category".format(bmi),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}