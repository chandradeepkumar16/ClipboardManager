package com.example.clipboardmanager.widgets


import android.annotation.SuppressLint
import android.graphics.drawable.Animatable
import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clipboardmanager.data.ClipboardItem
import com.example.clipboardmanager.navigation.AppScreens
import com.example.clipboardmanager.util.DateFormatting
import com.example.clipboardmanager.util.shareText
import kotlinx.coroutines.flow.filter
import kotlin.math.absoluteValue

@SuppressLint("RememberReturnType")
@Composable
fun ClipboardItemCard(
    clipboardItem: ClipboardItem,
    navController: NavController
) {

    val context = LocalContext.current
    var isFlipped by remember {
        mutableStateOf(false)
    }

    val frontContent: @Composable () -> Unit = {

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
                        .clickable { isFlipped = !isFlipped }
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
                        .clickable { isFlipped = !isFlipped }
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
                                    shareText(text = clipboardItem.text, context = context)
                                    Toast
                                        .makeText(
                                            context.applicationContext,
                                            "share on your apps",
                                            Toast.LENGTH_SHORT
                                        )
                                        .show()
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

    val backContent: @Composable () -> Unit = {
        // Back side of the card with delete button
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .height(60.dp)
                .clickable { isFlipped = !isFlipped },
            shape = RoundedCornerShape(8.dp),
//            colors = Color.Gray
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(android.graphics.Color.parseColor("#FA6060"))) //#FFCCA5
                ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center

            ) {
                Text(
                    text = "Delete",
                    color = Color.Black,
                    modifier = Modifier.padding(3.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Black,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable {
                            // Handle delete action here
                        }
                )
            }
        }
    }




    val content: @Composable () -> Unit = if (isFlipped) backContent else frontContent

    Box {
        content()
    }
}







