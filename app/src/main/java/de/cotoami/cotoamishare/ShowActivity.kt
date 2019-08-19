package de.cotoami.cotoamishare

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.CookieManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient

class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)
        val sharedPreference =  getSharedPreferences("Cotoami", Context.MODE_PRIVATE)

        var mywebview = findViewById<WebView>(R.id.cotonamiView).also {
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
                var editor = sharedPreference.edit().also {
                    it.putString("cookie",cookies)
                    it.apply()
                }
            }
        }

        val url = sharedPreference.getString("url", "https://cotoa.me")
        mywebview.loadUrl(url)
    }
}
