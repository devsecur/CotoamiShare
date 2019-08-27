package de.cotoami.cotoamishare

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import okhttp3.RequestBody



class ShareActivity : AppCompatActivity() {
    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)

        when {
            intent?.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    handleSendText(intent) // Handle text being sent
                }
            }
        }

        val submitButton = findViewById<Button>(R.id.shareButton)

        submitButton.setOnClickListener {
            val share = JSONObject()
            val content = JSONObject()
            val shareText = findViewById<EditText>(R.id.shareText)

            share.put("coto", content)
            content.put("content", shareText.text.toString())
            content.put("summary", "")
            content.put("cotonoma_id", "")

            createCoto(this, share)
        }
    }

    private fun handleSendText(intent: Intent) {
        val shareText = findViewById<EditText>(R.id.shareText)
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            shareText.setText(it, TextView.BufferType.EDITABLE)
        }
    }

    private fun createCoto(activity: Activity, share: JSONObject) {
        val url = "https://cotoa.me/api/cotos"

        val sharedPreference =  getSharedPreferences("Cotoami", Context.MODE_PRIVATE)
        val cookie = sharedPreference.getString("cookie","")

        val JSON = MediaType.parse("application/json; charset=utf-8")
        val body = RequestBody.create(JSON, share.toString())

        val request = Request.Builder()
            .url(url)
            .post(body)
            .addHeader("X-Requested-With", "XMLHttpRequest")
            .addHeader("X-Cotoami-Client-Id", "dummy")
            .addHeader("Cookie", cookie)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Intent(activity, ShowActivity::class.java).also {
                    // start your next activity)
                    it.data = Uri.parse("https://cotoa.me")
                    startActivity(it)
                }
            }
            override fun onResponse(call: Call, response: Response) {
                Intent(activity, ShowActivity::class.java).also {
                    // start your next activity)
                    it.data = Uri.parse("https://cotoa.me")
                    startActivity(it)
                }
            }
        })
    }
}
