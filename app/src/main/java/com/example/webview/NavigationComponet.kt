package com.example.webview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NavBar(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // WebView Here
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Button(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth()
    ){
        Button(onClick = { /*TODO*/ }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "")
        }
    }
}