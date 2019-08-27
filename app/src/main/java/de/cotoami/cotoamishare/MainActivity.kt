package de.cotoami.cotoamishare

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreference = getSharedPreferences("Cotoami", Context.MODE_PRIVATE)

        val cookie = sharedPreference.getString("cookie", "")

        if (cookie == "") {
            setContentView(R.layout.activity_main)

            val inputUrl = findViewById<EditText>(R.id.editText)
            val submitButton = findViewById<Button>(R.id.button)

            submitButton.setOnClickListener {
                Intent(this, ShowActivity::class.java).also {
                    // start your next activity
                    it.putExtra("url", inputUrl.text.toString())
                    startActivity(it)
                }
            }
        } else {
            Intent(this, ShowActivity::class.java).also {
                // start your next activity)
                it.data = Uri.parse("https://cotoa.me")
                startActivity(it)
            }
        }
    }
}
