package com.example.welan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.welan.data.User
import com.example.welan.data.getChatMessages

@Composable
fun Chat(originUser: User, destinationUser: User, navController: NavController, modifier: Modifier = Modifier){
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxSize()) {
        items(getChatMessages()){ message ->
            if (message.origin == originUser.name){
                Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.8f)) {
                    Surface(color = Color.Transparent, border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary)) {
                        Text(text = message.message)
                    }
                    Image(
                        painter = painterResource(originUser.imageRes),
                        contentDescription = originUser.name
                    )
                }
            }
        }
    }
}