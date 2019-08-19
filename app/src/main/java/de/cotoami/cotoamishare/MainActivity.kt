package de.cotoami.cotoamishare

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        val submitButton = findViewById<Button>(R.id.button)
        val inputUrl = findViewById<EditText>(R.id.editText)

        submitButton.setOnClickListener {
            val sharedPreference =  getSharedPreferences("Cotoami", Context.MODE_PRIVATE)

            var editor = sharedPreference.edit().also {
                it.putString("url",inputUrl.text.toString())
                it.apply()
            }

            val intent = Intent(this, ShowActivity::class.java).also {
                // start your next activity
                startActivity(it)
            }
        }
    }
}
