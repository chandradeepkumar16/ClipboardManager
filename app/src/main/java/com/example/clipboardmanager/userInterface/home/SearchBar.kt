package com.example.clipboardmanager.userInterface.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shadowElevation = 4.dp,
        color = Color(android.graphics.Color.parseColor("#BFBFFD")),
    ) {

        if (searchQuery.isEmpty()) {
            SearchIcon(Modifier.size(24.dp), "Search here....")
        }else{
            SearchIcon(Modifier.size(0.dp) ,"" )
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            BasicTextField(
                value = searchQuery,
                onValueChange = { newValue ->
                    onSearchQueryChange(newValue)
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch()
                    }
                ),
                textStyle = TextStyle(fontSize = 16.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(18.dp) // Adjust the height as needed
                    .padding(end = 8.dp),

            )

        }
    }
}

@Composable
private fun SearchIcon(modifier: Modifier, text:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

    )
    {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.Gray,
            modifier = modifier
        )

        Text(text = text)
    }

}

