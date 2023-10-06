package com.example.clipboardmanager.widgets


import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.navigation.NavController
import com.example.clipboardmanager.data.ClipboardItem
import com.example.clipboardmanager.navigation.AppScreens
import com.example.clipboardmanager.util.DateFormatting

@Composable
fun ClipboardItemCard(clipboardItem: ClipboardItem , navController: NavController) {


    val context = LocalContext.current
    val clipboardManager = remember { context.getSystemService<ClipboardManager>() }


    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        color = Color(android.graphics.Color.parseColor("#A8DADC")) //"#A8DADC"
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(android.graphics.Color.parseColor("#F5F5F9"))) //#FFCCA5
            ) {
                Text(
                    text = clipboardItem.text,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(3.dp)
                )
            }

            Divider(thickness = 1.dp, color = Color.Black)


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .horizontalScroll(rememberScrollState()), // Enable horizontal scrolling
                horizontalArrangement = Arrangement.SpaceBetween // Align items to the end (right)
            ) {

                DateFormatting()

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .clickable {
                                shareText(clipboardItem.text,context)
                                Toast.makeText(context.applicationContext,"share on your apps",Toast.LENGTH_SHORT).show()
                            }
                    ) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Copy")
                    }

                    Spacer(modifier = Modifier.width(18.dp))


                    Box(
                        modifier = Modifier
                            .size(15.dp)
                            .clickable {
                                navController.navigate(route = AppScreens.EditScreen.name)
                            }
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            }
        }
    }
}



// Function to share text with other apps
private fun shareText(text: String, context: Context) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    val shareIntent = Intent.createChooser(intent, null)
    context.startActivity(shareIntent)
}




