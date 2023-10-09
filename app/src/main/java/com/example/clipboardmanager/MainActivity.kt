package com.example.clipboardmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.clipboardmanager.navigation.AppNavigation
import com.example.clipboardmanager.widgets.SideBarWithContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController: NavController = rememberNavController()
            SideBarWithContent(navController = navController,{ AppNavigation() })        }
    }
}

//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController() // Create a NavController
//
//    NavHost(navController = navController, startDestination = AppScreens.ClipboardManagerApp.name) {
//        composable(AppScreens.ClipboardManagerApp.name) {
//            SideBarWithContent(navController = navController) {
//                ClipboardManagerApp(navController = navController)
//            }
//        }
//        // Define other destinations here
//    }
//}



