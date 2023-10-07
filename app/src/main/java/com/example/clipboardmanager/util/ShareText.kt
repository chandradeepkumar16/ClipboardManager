package com.example.clipboardmanager.util

import android.content.Context
import android.content.Intent

// Function to share text with other apps
 fun shareText(text: String, context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    val shareIntent = Intent.createChooser(intent, null)
    context.startActivity(shareIntent)
}