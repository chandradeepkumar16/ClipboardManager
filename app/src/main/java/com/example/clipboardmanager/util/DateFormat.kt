package com.example.clipboardmanager.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DateFormatting(){
    var formattedDateTime by remember { mutableStateOf("") }
    val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
    val currentDateTime = Date()
    formattedDateTime = dateFormat.format(currentDateTime)

    Text(
        text = "Timestamp: $formattedDateTime",
        modifier = Modifier.padding(6.dp)
    )
}