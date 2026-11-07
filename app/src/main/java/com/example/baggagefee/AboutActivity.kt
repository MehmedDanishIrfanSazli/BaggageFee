package com.example.baggagefee

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button

class AboutActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val btnGithub = findViewById<Button>(R.id.btnGithub)

        btnGithub.setOnClickListener {
            val githubUrl =
                "https://github.com/YOUR_USERNAME/BaggageFee"

            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(githubUrl)
            )

            startActivity(intent)
        }
    }
}
