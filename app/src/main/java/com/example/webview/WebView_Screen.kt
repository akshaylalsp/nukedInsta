package com.example.webview

import android.annotation.SuppressLint
import android.health.connect.datatypes.units.Length
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView(mUrl:String,isNavVisible:MutableState<Boolean>) {

    AndroidView(factory = {
        val webView = WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            // Enable JavaScript for full rendering (if needed)
            settings.javaScriptEnabled = true

            // Set a WebViewClient to handle link clicks, loading progress, and errors
            webViewClient = object : WebViewClient() {

                val jsCode = """
                                setTimeout(function() {
                                    var elements = document.querySelectorAll('$blocklist');
                                    elements.forEach(function(element) {
                                        element.style.display = 'none'; // Hide elements with _aacg class
                                    });
                            }, 0); // Delay by 3 second
                        """.trimIndent()

                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)

                    view?.evaluateJavascript(jsCode, null)
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    view?.evaluateJavascript(jsCode, null)
//                    val currentUrl = request?.url?.toString()
//                    Toast.makeText(context,currentUrl,LENGTH_SHORT).show()
                    return super.shouldOverrideUrlLoading(view, request)  // Let WebView handle the URL change
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    view.evaluateJavascript(jsCode, null)
//                    isNavVisible.value = false
                    var result = url == "https://www.instagram.com/direct/inbox/"
                    if(url.contains("direct",ignoreCase = true)){
                        isNavVisible.value = false
                    }
                    Toast.makeText(context,result.toString(), LENGTH_SHORT).show()
                    Log.i("inbox",url+"https://www.instagram.com/direct/inbox")
                }

            }
        }
        webView // Return the created WebView instance
    }, update = {
        it.loadUrl(mUrl)
    })
}