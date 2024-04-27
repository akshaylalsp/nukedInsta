package com.example.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.example.webview.ui.theme.WebViewTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WebViewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WebView()
                }
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebView() {

    // ... (rest of the code)
    val mUrl = "https://instagram.com"
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
                            }, 1000); // Delay by 1 second
                        """.trimIndent()


                @Deprecated("Deprecated in Java", ReplaceWith("false"))
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    // Handle link clicks here (optional)
                    // ...
                    view.evaluateJavascript(jsCode, null)
                    return false
                }

                override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    view.evaluateJavascript(jsCode, null)
                    // Show a loading indicator while page loads
                    // (implement your loading indicator UI here)
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    view.evaluateJavascript(jsCode, null)
                    // Hide loading indicator after page finishes loading
                }

                override fun onReceivedError(view: WebView, request: WebResourceRequest, error: WebResourceError) {
                    super.onReceivedError(view, request, error)
                    view.reload()
                    // Handle loading errors (display an error message or retry)
                    // (implement your error handling logic here)
                }
            }
        }
        webView // Return the created WebView instance
    }, update = {
        it.loadUrl(mUrl)
    })
}
