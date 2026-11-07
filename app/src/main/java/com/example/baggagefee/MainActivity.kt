package com.example.baggagefee

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etRate = findViewById<EditText>(R.id.etRate)
        val rbEconomy = findViewById<RadioButton>(R.id.rbEconomy)
        val rbBusiness = findViewById<RadioButton>(R.id.rbBusiness)
        val btnCalculate = findViewById<Button>(R.id.btnCalculate)
        val btnReset = findViewById<Button>(R.id.btnReset)

        btnCalculate.setOnClickListener {

            val weight = etWeight.text.toString()
                .trim().toDoubleOrNull()

            val rate = etRate.text.toString()
                .trim().toDoubleOrNull()

            // Validate weight
            if (weight == null || !weight.isFinite() || weight < 0) {
                etWeight.error = "Enter a valid, non-negative weight"
                etWeight.requestFocus()
                return@setOnClickListener
            }

            // Validate rate
            if (rate == null || !rate.isFinite() || rate < 0) {
                etRate.error = "Enter a valid, non-negative rate"
                etRate.requestFocus()
                return@setOnClickListener
            }

            val ticketClass = if (rbEconomy.isChecked) {
                "Economy"
            } else {
                "Business"
            }

            val allowance = if (rbEconomy.isChecked) 20.0 else 40.0
            val excess = maxOf(0.0, weight - allowance)

            if (!(excess * rate * 1.08).isFinite()) {
                etRate.error = "Values are too large"
                return@setOnClickListener
            }

            // Open Results page with the inputs
            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("WEIGHT", weight)
            intent.putExtra("RATE", rate)
            intent.putExtra("CLASS", ticketClass)

            startActivity(intent)
        }

        btnReset.setOnClickListener {
            etWeight.text.clear()
            etRate.text.clear()
            etWeight.error = null
            etRate.error = null
            rbEconomy.isChecked = true
            rbBusiness.isChecked = false
            etWeight.clearFocus()
            etRate.clearFocus()
        }
    }
}
