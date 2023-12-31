package com.example.clipboardmanager.userInterface.home

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.navigation.NavController
import com.example.clipboardmanager.data.ClipboardItem
import com.example.clipboardmanager.navigation.AppScreens
import com.example.clipboardmanager.util.fetchClipboardItems
import com.example.clipboardmanager.util.saveClipboardItem
import com.example.clipboardmanager.widgets.ClipboardItemCard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("SuspiciousIndentation")
@Composable
fun ClipboardManagerApp(navController: NavController) {



    val context = LocalContext.current
    val clipboardManager = remember { context.getSystemService<ClipboardManager>() }

    val clipboardTexts = remember { mutableStateListOf<ClipboardItem>() }
    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    val filteredClipboardTexts = remember { mutableStateListOf<ClipboardItem>() }

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser

    if (backDispatcher != null) {
        com.example.clipboardmanager.util.BackHandler(backDispatcher = backDispatcher) {
            navController.navigate(route = AppScreens.ClipboardManagerApp.name)
        }
    }

    DisposableEffect(Unit) {
        val job = coroutineScope.launch {
            fetchClipboardItems { items ->
                clipboardTexts.clear()
                clipboardTexts.addAll(0,items)
                clipboardTexts.reverse()

                filteredClipboardTexts.clear()
                filteredClipboardTexts.addAll(0,items)
                filteredClipboardTexts.reverse()
            }
        }
        onDispose {
            job.cancel()
        }
    }



    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column {
                SearchBar(
                    searchQuery = searchQuery,
                    onSearchQueryChange = { newQuery ->
                        searchQuery = newQuery
                        filteredClipboardTexts.clear()

                        filteredClipboardTexts.addAll(clipboardTexts.filter {
                            it.text.contains(
                                searchQuery,
                                ignoreCase = true
                            )
                        })
                    },
                    onSearch = {
                        val matchingTexts =
                            clipboardTexts.filter { it.text.contains(searchQuery, ignoreCase = true) }
                        filteredClipboardTexts.clear()
                        filteredClipboardTexts.addAll(matchingTexts)
                    }
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Copied Texts:",
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Spacer(modifier = Modifier.weight(1f)) // Add space to push the icon to the right

                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                Toast.makeText(context, "loading latest cliptexts....",Toast.LENGTH_SHORT).show()
                                // Fetch the latest clipboard texts from the database and update the lists
                                fetchClipboardItems { items ->
                                    clipboardTexts.clear()
                                    clipboardTexts.addAll(0, items)
                                    clipboardTexts.reverse()

                                    filteredClipboardTexts.clear()
                                    filteredClipboardTexts.addAll(0, items)
                                    filteredClipboardTexts.reverse()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh",
                        )
                    }
                }

                }


                Divider()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    //filteredClipboardTexts
                    items(filteredClipboardTexts) { item ->
                        ClipboardItemCard(clipboardItem = item,
                            navController,
                            onDeleteClick = { deletedItem ->
                                // Handle card deletion here
                                clipboardTexts.remove(deletedItem) // Remove the item from clipboardTexts
                                filteredClipboardTexts.remove(deletedItem) // Remove the item from filteredClipboardTexts
                            }
                        )
                    }
                }
            }



        DisposableEffect(Unit) {
            val job = coroutineScope.launch {
                while (true) {
                    val clipData = clipboardManager?.primaryClip
                    val text = clipData?.getItemAt(0)?.text.toString()

//                    if (text.isNotEmpty()) {

                        if (text.isNotEmpty() && !clipboardTexts.any { it.text == text } ) {

                        val clipboardItem = ClipboardItem(text, System.currentTimeMillis())
                        clipboardTexts.add(0, clipboardItem)

                        saveClipboardItem(clipboardItem)


                        // Update filteredClipboardTexts based on the search query
                        if (searchQuery.isBlank() || clipboardItem.text.contains(
                                searchQuery,
                                ignoreCase = true
                            )
                        ) {
                            filteredClipboardTexts.add(0, clipboardItem)
                        }
                    }

                    if (clipboardTexts.size > 100) {
                        clipboardTexts.removeLast()
                    }

                    if (filteredClipboardTexts.size > 100) {
                        filteredClipboardTexts.removeLast()
                    }
                    delay(100)
                }
            }
            onDispose {
                job.cancel()
            }
        }
}

private fun saveUserDataToDatabase(user: FirebaseUser?, str: ClipboardItem) {

    val database = FirebaseDatabase.getInstance()
    val usersRef = database.getReference("users")

    // Use the user's UID as the key in the database
    usersRef.child(user!!.uid).child(System.currentTimeMillis().toString()).setValue(str)
}







