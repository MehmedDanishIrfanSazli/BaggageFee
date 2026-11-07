package com.example.baggagefee

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale


class ResultActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)


        val tvExcessWeight =
            findViewById<TextView>(R.id.tvExcessWeight)

        val tvExcessCharge =
            findViewById<TextView>(R.id.tvExcessCharge)

        val tvTotalPayable =
            findViewById<TextView>(R.id.tvTotalPayable)

        val tvChargeBreakdown =
            findViewById<TextView>(R.id.tvChargeBreakdown)

        val tvTaxBreakdown =
            findViewById<TextView>(R.id.tvTaxBreakdown)

        val btnCalculateAgain =
            findViewById<Button>(R.id.btnCalculateAgain)

        // Receive inputs from the Home page
        val weight = intent.getDoubleExtra("WEIGHT", -1.0)
        val rate = intent.getDoubleExtra("RATE", -1.0)
        val ticketClass = intent.getStringExtra("CLASS")

        // Prevent invalid or missing input data
        if (!weight.isFinite() || !rate.isFinite() ||
            weight < 0 || rate < 0 ||
            (ticketClass != "Economy" &&
                    ticketClass != "Business")) {
            finish()
            return
        }

        // Ticket class allowance
        val allowance = if (ticketClass == "Economy") {
            20.0
        } else {
            40.0
        }

        // Calculate the three required outputs
        val excessWeight = maxOf(0.0, weight - allowance)
        val excessCharge = excessWeight * rate
        val serviceTax = excessCharge * 0.08
        val totalPayable = excessCharge + serviceTax

        if (!excessCharge.isFinite() ||
            !totalPayable.isFinite()) {
            finish()
            return
        }

        // Format results
        fun money(value: Double): String {
            return String.format(Locale.US, "RM%.2f", value)
        }

        tvExcessWeight.text =
            String.format(Locale.US, "%.2f kg", excessWeight)

        tvExcessCharge.text = money(excessCharge)
        tvTotalPayable.text = money(totalPayable)

        tvChargeBreakdown.text = String.format(
            Locale.US,
            "%.2f kg x %s/kg",
            excessWeight,
            money(rate)
        )

        tvTaxBreakdown.text =
            "Service tax (8%): ${money(serviceTax)}"

        // Return to calculator with existing inputs
        btnCalculateAgain.setOnClickListener {
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
