package com.example.clipboardmanager.navigation

enum class AppScreens {
    ClipboardManagerApp,
    EditScreen;


    companion object{
        fun fromRoute(route:String?): AppScreens
                =when (route?.substringBefore("/")){
            ClipboardManagerApp.name -> ClipboardManagerApp
            EditScreen.name -> EditScreen
            null -> ClipboardManagerApp
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }



}