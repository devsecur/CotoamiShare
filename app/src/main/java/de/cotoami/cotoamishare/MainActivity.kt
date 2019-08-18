package de.cotoami.cotoamishare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val submitButton = findViewById(R.id.button) as Button
        val inputUrl = findViewById(R.id.editText) as EditText

        submitButton.setOnClickListener {
            Toast.makeText(
                this@MainActivity,
                inputUrl.text.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
