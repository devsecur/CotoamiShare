package de.cotoami.cotoamishare

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient

class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val sharedPreference =  getSharedPreferences("Cotoami", Context.MODE_PRIVATE)

        val mywebview = findViewById<WebView>(R.id.cotonamiView).also {
            it.settings.javaScriptEnabled = true
            it.settings.javaScriptCanOpenWindowsAutomatically = true
            it.settings.domStorageEnabled = true
        }
        mywebview!!.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                val cookies = CookieManager.getInstance().getCookie(url)
                sharedPreference.edit().also {
                    it.putString("cookie",cookies)
                    it.apply()
                }
            }
        }

        val cookie = sharedPreference.getString("cookie","")
        CookieManager.getInstance().setCookie("cotoa.me", cookie)

        val data = intent?.data

        if (data != null) {
            val scheme = data.scheme
            val host = data.host
            val path = data.path
            val url = "$scheme://$host/$path"
            println(url)
            mywebview.loadUrl(url)
        }

        else {
            mywebview.loadUrl("https://cotoa.me")
        }

    }
}
