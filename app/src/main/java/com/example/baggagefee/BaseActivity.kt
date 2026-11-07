
package com.example.baggagefee

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

open class BaseActivity : AppCompatActivity() {

    private val githubUrl =
        "https://github.com/MehmedDanishIrfanSazli/BaggageFee"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.decorView.setBackgroundColor(
            ContextCompat.getColor(this, R.color.navy)
        )

        val content = findViewById<View>(android.R.id.content)

        ViewCompat.setOnApplyWindowInsetsListener(content) { view, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                bars.left,
                bars.top,
                bars.right,
                bars.bottom
            )

            insets
        }

        ViewCompat.requestApplyInsets(content)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.appToolbar)

        toolbar.title = "BaggageFee"
        toolbar.setNavigationIcon(R.drawable.ic_menu)

        // Add the Share menu directly to the custom toolbar.
        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.toolbar_menu)

        toolbar.setNavigationOnClickListener { anchor ->
            val popup = PopupMenu(this, anchor)
            popup.menuInflater.inflate(
                R.menu.navigation_menu,
                popup.menu
            )

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> {
                        if (this !is MainActivity) {
                            val intent = Intent(
                                this,
                                MainActivity::class.java
                            )
                            intent.addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                        Intent.FLAG_ACTIVITY_SINGLE_TOP
                            )
                            startActivity(intent)
                            finish()
                        }
                        true
                    }

                    R.id.nav_about -> {
                        if (this !is AboutActivity) {
                            startActivity(
                                Intent(this, AboutActivity::class.java)
                            )
                        }
                        true
                    }

                    else -> false
                }
            }

            popup.show()
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_share -> {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(
                            Intent.EXTRA_SUBJECT,
                            "BaggageFee App"
                        )
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Check out BaggageFee!\n$githubUrl"
                        )
                    }

                    startActivity(
                        Intent.createChooser(
                            shareIntent,
                            "Share BaggageFee"
                        )
                    )
                    true
                }

                else -> false
            }
        }
    }
}
