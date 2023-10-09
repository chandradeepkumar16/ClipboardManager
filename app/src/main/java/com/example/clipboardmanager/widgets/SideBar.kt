package com.example.clipboardmanager.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.clipboardmanager.R
import com.example.clipboardmanager.navigation.AppNavigation
import com.example.clipboardmanager.navigation.AppScreens
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SuspiciousIndentation")

fun SideBarWithContent(
    navController: NavController = rememberNavController(), // Pass the NavController as a parameter
    appNavigation: @Composable () -> Unit,
) {
    var selectedOption by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalDrawer(
        drawerState = drawerState,
        drawerElevation = 14.dp,
        drawerContent = {
            // Drawer content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp) // Adjust spacing here
            ) {
                Text(
                    text = "Menu",
                    style = MaterialTheme.typography.h5,
                    color = Color.Black
                )
                Divider()
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.body1,
                    color = if (selectedOption == "Home") Color.Blue else Color.Black,
                    modifier = Modifier.clickable {
                        selectedOption = "Home"
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )

                Text(
                    text = "Your Profile",
                    style = MaterialTheme.typography.body1,
                    color = if (selectedOption == "Your Profile") Color.Blue else Color.Black,
                    modifier = Modifier.clickable {
                        selectedOption = "Your Profile"
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
                Text(
                    text = "Logout",
                    style = MaterialTheme.typography.body1,
                    color = if (selectedOption == "Logout") Color.Blue else Color.Black,
                    modifier = Modifier.clickable {

//                        navController.navigate(AppScreens.LoginPage.name) // Use the provided NavController

                        selectedOption = "Logout"
                        scope.launch {
                            drawerState.close()
                        }
                        // Perform logout action here
                    }
                )
            }
        },
        content = {
            // App content
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "ClipBoard Manager") },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) {
                                            drawerState.open()
                                        } else {
                                            drawerState.close()
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_menu),
                                    contentDescription = "Menu"
                                )
                            }
                        }
                    )
                },
                content = {
                    appNavigation()
                }
            )
        }
    )
}

