package com.example.mathongo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import com.example.mathongo.R

class WebQuestionActivity : AppCompatActivity() {

    private lateinit var webView:WebView
    private lateinit var networkErrorIllu:ImageView
    private lateinit var progress:ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_question)
        initialiseViews()
        intent.getStringExtra(URL_TAG)?.let { url ->
            val webClient=object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    hideProgress()
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    networkErrorIllu.isVisible=true
                }
            }
            webView.settings.javaScriptEnabled=true
            webView.webViewClient=webClient
            webView.loadUrl(url)
            showProgress()
        }
    }

    private fun hideProgress() {
        progress.isVisible=false
    }

    private fun showProgress() {
        progress.isVisible=true
    }

    private fun initialiseViews() {
        webView=findViewById(R.id.webview)
        networkErrorIllu=findViewById(R.id.newtwork_illu)
        progress=findViewById(R.id.progress)
    }


    companion object {
        val URL_TAG: String?="url"
    }
}