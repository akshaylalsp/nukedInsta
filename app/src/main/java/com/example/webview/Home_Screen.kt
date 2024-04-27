package com.example.webview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.example.webview.ui.theme.WebViewTheme

@Composable
fun HomeScreen(){
    var url by remember { mutableStateOf("https://www.instagram.com/direct/inbox") }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter){
        WebView(url)
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(Color.Transparent)
                .pointerInput(Unit){},
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(onClick = { url="https://www.instagram.com/?variant=following" }) {
                Icons.Filled.Home
            }
            Button(onClick = { url = "https://www.instagram.com/direct/inbox" }) {
                Icons.Filled.Email
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    WebViewTheme {
        HomeScreen()
    }
}