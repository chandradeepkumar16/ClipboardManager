package com.example.clipboardmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.clipboardmanager.navigation.AppNavigation
import com.example.clipboardmanager.userInterface.home.ClipboardManagerApp
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
//            ClipboardManagerApp(navController = )
            AppNavigation()
        }


    }
}




