package com.example.clipboardmanager

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.clipboardmanager.navigation.AppNavigation
import com.example.clipboardmanager.navigation.AppNavigationLogin
import com.example.clipboardmanager.navigation.AppScreens
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContent {
//            val navController = rememberNavController()
                AppNavigation()



        }
    }
}



