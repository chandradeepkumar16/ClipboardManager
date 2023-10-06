package com.example.clipboardmanager.userInterface.editScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun EditScreen(navController: NavController){
    
    Column(modifier = Modifier.fillMaxWidth() ) {
        Text(text = "hhehehhe")
        Text(text = "this is the second screen")
    }

}