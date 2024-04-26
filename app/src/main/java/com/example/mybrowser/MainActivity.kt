package com.example.mybrowser

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.viewinterop.AndroidView
import com.example.mybrowser.ui.theme.MyBrowserTheme
import java.io.InputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBrowserTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BrowserView()
                }
            }
        }
    }
}




@SuppressLint("SetJavaScriptEnabled")
@Composable
fun BrowserView(modifier: Modifier = Modifier){
    val overlayVisible = remember { mutableStateOf(true) }
    @Composable
    fun BlockTouchWebView(modifier: Modifier = Modifier) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Your WebView with proper WebViewClient handling re-injection

            if (overlayVisible.value) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .pointerInput(Unit) {} // Block touch events
                )
            }
        }
    }

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
//                webViewClient = WebViewClient()
                webViewClient = object : WebViewClient() {

                    val jsCode = """
                                setTimeout(function() {
                                    var elements = document.querySelectorAll('._aacg,.x1n2onr6.x1f91t4q.x1mh60rb.xxfw5ft.xleuxlb.x1qughib.x1gvbg2u.x1q0g3np.x78zum5,.x1n2onr6.x5yr21d.xdt5ytf.x78zum5.xvbhtw8');
                                    elements.forEach(function(element) {
                                        element.style.display = 'none'; // Hide elements with _aacg class
                                    });
                            }, 1000); // Delay by 1 second
                        """.trimIndent()

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        overlayVisible.value = true
                        // JavaScript to hide elements by ID
                        view?.evaluateJavascript(jsCode, null)
                        overlayVisible.value = false
                    }

                    override fun onLoadResource(view: WebView?, url: String?) {
                        super.onLoadResource(view, url)
                        overlayVisible.value = true
                        // JavaScript to hide elements by ID
                        view?.evaluateJavascript(jsCode, null)
                        overlayVisible.value = false

                    }
                }
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(true)
            }
        },
        update = { webView ->
            webView.loadUrl("https://www.instagram.com/?variant=following")
        }
    )
    if (overlayVisible.value) {
        BlockTouchWebView() // Blocks touch while visible
    }
}
