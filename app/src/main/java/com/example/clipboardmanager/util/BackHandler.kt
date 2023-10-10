package com.example.clipboardmanager.util

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalView

@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher,
    onBack: () -> Unit
) {
    val view = LocalView.current

    DisposableEffect(view) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onBack()
            }
        }
        backDispatcher.addCallback(callback)
        onDispose {
            callback.remove()
        }
    }
}

