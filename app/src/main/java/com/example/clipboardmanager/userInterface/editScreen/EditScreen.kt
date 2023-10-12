package com.example.clipboardmanager.userInterface.editScreen

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clipboardmanager.util.currentUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditScreen(
    navController: NavController,
    initialText: String) {

    val oldtext = initialText
    var editedText by remember { mutableStateOf(initialText) }

    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Back") },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Arrow Back",
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp).verticalScroll(rememberScrollState())
            ,
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            TextField(
                value = editedText,
                onValueChange = { newText ->
                    editedText = newText
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // Handle the save action here
                    }
                ),
                textStyle = TextStyle.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = {

                    val database = FirebaseDatabase.getInstance()
                    var reference = database
                        .getReference("clipboardItems")
                        .child(
                            currentUser!!.uid
                        )

                    reference.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (childSnapshot in dataSnapshot.children) {
                                    val value = childSnapshot.child("text").value
                                    if (value.toString() == oldtext) {

                                        var key = childSnapshot.key.toString()

                                        reference.child("$key").child("text").setValue(editedText)

                                        val clip = ClipData.newPlainText("latesttext", editedText)
                                        clipboardManager.setPrimaryClip(clip)

                                        navController.popBackStack()
                                    }
                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e("FirebaseData", "Error: ${databaseError.message}")
                        }
                    })


                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

            ) {
                Text(text = "Save")
            }
        }
    }
}






