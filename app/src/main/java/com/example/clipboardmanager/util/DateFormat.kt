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
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DateFormatting(): String{
    var formattedDateTime by remember { mutableStateOf("") }
    val dateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.getDefault())
    val currentDateTime = Date()
    formattedDateTime = dateFormat.format(currentDateTime)

    Text("Timestamp: $formattedDateTime", fontSize = 10.sp)
    return formattedDateTime
}