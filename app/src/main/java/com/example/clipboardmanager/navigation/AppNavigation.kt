package com.example.clipboardmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clipboardmanager.userInterface.editScreen.EditScreen
import com.example.clipboardmanager.userInterface.home.ClipboardManagerApp
import com.example.clipboardmanager.userInterface.intro.LoginScreen
import com.example.clipboardmanager.userInterface.intro.SignupScreen
import com.example.clipboardmanager.widgets.SideBarWithContent
import com.google.firebase.auth.FirebaseAuth


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser


    NavHost(navController = navController, startDestination  = if (currentUser != null) AppScreens.ClipboardManagerApp.name else AppScreens.LoginPage.name) {
        composable(AppScreens.ClipboardManagerApp.name) {
            SideBarWithContent(navController = navController) {
                ClipboardManagerApp(navController = navController)
            }
        }
        composable(AppScreens.EditScreen.name) {
            EditScreen(navController = navController)
        }
        composable(AppScreens.LoginPage.name) {
            LoginScreen(navController = navController)
        }
        composable(AppScreens.SignupPage.name) {
            SignupScreen(navController = navController)
        }
    }
}
