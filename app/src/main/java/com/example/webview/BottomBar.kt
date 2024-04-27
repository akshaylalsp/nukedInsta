package com.example.webview

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput


@Composable
fun BottomBar(url: MutableState<String>){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .pointerInput(Unit) {},
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Button(onClick = { url.value="https://www.instagram.com/?variant=following" }) {
            Icons.Filled.Home
        }
        Button(onClick = { url.value = "https://www.instagram.com/direct/inbox" }) {
            Icons.Filled.Email
        }
    }
}
