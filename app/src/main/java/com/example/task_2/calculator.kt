package com.example.task_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class calculator : AppCompatActivity() {

    private val historyList = ArrayList<String>()

    private lateinit var heightInput: EditText
    private lateinit var weightInput: EditText
    private lateinit var heightUnitSpinner: Spinner
    private lateinit var weightUnitSpinner: Spinner
    private lateinit var bmiValue: TextView
    private lateinit var categoryType: TextView
    private lateinit var description: TextView

    companion object {
        private const val KEY_HISTORY = "key_history"
        private const val KEY_BMI = "key_bmi"
        private const val KEY_CAT = "key_cat"
        private const val KEY_DESC = "key_desc"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_bmi_calculator)

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

        heightInput = findViewById(R.id.height)
        weightInput = findViewById(R.id.weight)
        heightUnitSpinner = findViewById(R.id.heightUnitSpinner)
        weightUnitSpinner = findViewById(R.id.weightUnitSpinner)
        val calculateBtn = findViewById<Button>(R.id.btnCalculate)
        val historyButton = findViewById<Button>(R.id.Historybtn)
        bmiValue = findViewById(R.id.BMIvalue)
        categoryType = findViewById(R.id.Categorytype)
        description = findViewById(R.id.Description)
        val backButton = findViewById<ImageButton>(R.id.backButton)

        backButton.setOnClickListener {
            finish()
        }

        val heightUnits = arrayOf("cm", "m", "ft")
        val heightAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            heightUnits
        )
        heightUnitSpinner.adapter = heightAdapter

        val weightUnits = arrayOf("kg", "g")
        val weightAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            weightUnits
        )
        weightUnitSpinner.adapter = weightAdapter

        if (savedInstanceState != null) {
            savedInstanceState.getStringArrayList(KEY_HISTORY)?.let {
                historyList.addAll(it)
            }
            bmiValue.text = savedInstanceState.getString(KEY_BMI, "--")
            categoryType.text = savedInstanceState.getString(KEY_CAT, "--")
            description.text = savedInstanceState.getString(KEY_DESC, "Calculate your BMI to view health status")
        } else {
            heightUnitSpinner.setSelection(0)
            weightUnitSpinner.setSelection(0)
        }

        calculateBtn.setOnClickListener {
            val heightText = heightInput.text.toString().trim()
            val weightText = weightInput.text.toString().trim()

            if (heightText.isEmpty() || weightText.isEmpty()) {
                Toast.makeText(this, "Please enter height and weight", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val heightVal = heightText.toDoubleOrNull()
            val weightVal = weightText.toDoubleOrNull()

            if (heightVal == null || weightVal == null || heightVal <= 0 || weightVal <= 0) {
                Toast.makeText(this, "Please enter valid values greater than 0", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val heightUnit = heightUnitSpinner.selectedItem.toString()
            val heightM = when (heightUnit) {
                "cm" -> heightVal / 100.0
                "m" -> heightVal
                "ft" -> heightVal * 0.3048
                else -> heightVal / 100.0
            }

            val weightUnit = weightUnitSpinner.selectedItem.toString()
            val weightKg = when (weightUnit) {
                "kg" -> weightVal
                "g" -> weightVal / 1000.0
                else -> weightVal
            }

            val bmi = weightKg / (heightM * heightM)
            val category: String
            val desc: String

            when {
                bmi < 18.5 -> {
                    category = "Underweight"
                    desc = "Your BMI is below the normal range."
                }
                bmi < 25.0 -> {
                    category = "Healthy"
                    desc = "Normal weight. Keep it up!"
                }
                bmi < 30.0 -> {
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

            val record = "BMI: $formattedBMI ($category) • $weightText $weightUnit / $heightText $heightUnit"
            historyList.add(0, record)

            heightInput.text.clear()
            weightInput.text.clear()
        }

        historyButton.setOnClickListener {
            val intent = Intent(this, BMI_History::class.java)
            intent.putStringArrayListExtra("BMI_HISTORY", historyList)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(KEY_HISTORY, historyList)
        outState.putString(KEY_BMI, bmiValue.text.toString())
        outState.putString(KEY_CAT, categoryType.text.toString())
        outState.putString(KEY_DESC, description.text.toString())
    }
}