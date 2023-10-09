package com.example.clipboardmanager.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.clipboardmanager.userInterface.editScreen.EditScreen
import com.example.clipboardmanager.userInterface.home.ClipboardManagerApp
import com.example.clipboardmanager.userInterface.intro.LoginScreen


@Composable
fun AppNavigation(){

    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = AppScreens.ClipboardManagerApp.name){

        composable(AppScreens.ClipboardManagerApp.name){
            ClipboardManagerApp(navController = navController)
        }

        composable(AppScreens.EditScreen.name){
            EditScreen(navController = navController)
        }
        
        composable(AppScreens.LoginPage.name){
            LoginScreen(navController = navController)
        }

    }


}