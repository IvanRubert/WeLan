package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.welan.model.MessagesViewModel

@Composable
fun Mensajes(navController: NavController, modifier: Modifier = Modifier,
             viewModel: MessagesViewModel){
        Box(modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.TopCenter)) {
            items(viewModel.messages) { message ->
                val noReadMsg = message.msgNoRead
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).heightIn(min = 50.dp)
                    .clickable{
                        val index = viewModel.messages.indexOf(message)
                        viewModel.markAsRead(index)
                    }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(message.imageUserRef),
                            contentDescription = message.nameUser,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                        )
                        Text(
                            text = message.nameUser,
                            style = MaterialTheme.typography.titleMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp).weight(1f)
                        )
                        if (noReadMsg) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.secondary)
                            )
                        }
                    }
                }
            }
        }
    }
}