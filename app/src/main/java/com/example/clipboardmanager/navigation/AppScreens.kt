package com.example.clipboardmanager.navigation

enum class AppScreens {
    ClipboardManagerApp,
    EditScreen,
    LoginPage,
    SignupPage;


    companion object{
        fun fromRoute(route:String?): AppScreens
                =when (route?.substringBefore("/")){
            ClipboardManagerApp.name -> ClipboardManagerApp
            EditScreen.name -> EditScreen
            LoginPage.name -> LoginPage
            SignupPage.name -> SignupPage
            null -> ClipboardManagerApp
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }

}