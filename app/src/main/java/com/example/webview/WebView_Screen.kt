package com.example.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
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
fun WebView(mUrl:String) {

    // ... (rest of the code)
//    val mUrl = "https://www.instagram.com/direct/inbox"

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


//                @Deprecated("Deprecated in Java", ReplaceWith("false"))
//                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//                    // Handle link clicks here (optional)
//                    // ...
//                    view.evaluateJavascript(jsCode, null)
//                    return false
//                }

                override fun onLoadResource(view: WebView?, url: String?) {
                    super.onLoadResource(view, url)
                    view?.evaluateJavascript(jsCode, null)
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    // Re-inject JavaScript if the URL changes
                    view?.evaluateJavascript(jsCode, null)
                    return super.shouldOverrideUrlLoading(view, request)  // Let WebView handle the URL change
                }


                //                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
//                    super.onPageStarted(view, url, favicon)
//                    view.evaluateJavascript(jsCode, null)
//                    // Show a loading indicator while page loads
//                    // (implement your loading indicator UI here)
//                }
//
                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    view.evaluateJavascript(jsCode, null)
                    // Hide loading indicator after page finishes loading
                }
//
//                override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
//                    super.onReceivedError(view, request, error)
//                    view.reload()
//                    // Handle loading errors (display an error message or retry)
//                    // (implement your error handling logic here)
//                }
            }
        }
        webView // Return the created WebView instance
    }, update = {
        it.loadUrl(mUrl)
    })
}