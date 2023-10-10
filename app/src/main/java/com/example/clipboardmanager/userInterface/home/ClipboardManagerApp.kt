package com.example.clipboardmanager.userInterface.home

import android.content.ClipboardManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.navigation.NavController
import com.example.clipboardmanager.data.ClipboardItem
import com.example.clipboardmanager.navigation.AppScreens
import com.example.clipboardmanager.widgets.ClipboardItemCard
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable

fun ClipboardManagerApp(navController: NavController) {


    val context = LocalContext.current
    val clipboardManager = remember { context.getSystemService<ClipboardManager>() }

    val clipboardTexts = remember { mutableStateListOf<ClipboardItem>() }
    val coroutineScope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    val filteredClipboardTexts = remember { mutableStateListOf<ClipboardItem>() }

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    if (backDispatcher != null) {
        com.example.clipboardmanager.util.BackHandler(backDispatcher = backDispatcher) {
            navController.navigate(route = AppScreens.ClipboardManagerApp.name)
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

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Copied Texts:",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(start = 4.dp)
                )

                Divider()

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
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
        }


        DisposableEffect(Unit) {
            val job = coroutineScope.launch {
                while (true) {
                    val clipData = clipboardManager?.primaryClip
                    val text = clipData?.getItemAt(0)?.text.toString()

                    if (text.isNotEmpty() && !clipboardTexts.any { it.text == text }) {
                        val clipboardItem = ClipboardItem(text, System.currentTimeMillis())
                        clipboardTexts.add(0, clipboardItem)

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
                    delay(1000) // Check the clipboard every 1 second
                }
            }
            onDispose {
                job.cancel()
            }
        }
//        Toast.makeText(context, "Yes user", Toast.LENGTH_SHORT).show()





}







