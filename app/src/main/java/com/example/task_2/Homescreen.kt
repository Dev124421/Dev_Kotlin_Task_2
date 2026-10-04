package com.example.task_2

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class Homescreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homescreen)

        val currencyButton = findViewById<CardView>(R.id.currencyBtn)
        currencyButton.setOnClickListener {
            val intent = Intent(this, converter::class.java)
            startActivity(intent)
        }

        val bmiButton = findViewById<CardView>(R.id.bmiBtn)
        bmiButton.setOnClickListener {
            val intent = Intent(this, calculator::class.java)
            startActivity(intent)
        }
    }
}