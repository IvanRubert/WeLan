package com.example.welan.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.welan.R
import com.example.welan.model.UserWelan
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.viewmodels.ChatUiState
import com.example.welan.ui.viewmodels.ChatViewModel

@Composable
fun Chat(originUser: UserWelan, destinationImgRef: String, destinationName: String, modifier: Modifier = Modifier,
         viewModel: ChatViewModel) {
    val listState = rememberLazyListState()
    val chatUiState = viewModel.chatUiState
    val chat = viewModel.chat

    LaunchedEffect(chat.size) {
        if (chat.isNotEmpty()) {
            listState.scrollToItem(chat.size - 1)
        }
    }

    when {
        chatUiState is ChatUiState.Loading && chat.isEmpty() -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        chatUiState is ChatUiState.Error && chat.isEmpty() -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Error al cargar el chat",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        else -> ChatContent(
            originUser = originUser,
            destinationImgRef = destinationImgRef,
            destinationName = destinationName,
            modifier = modifier,
            viewModel = viewModel,
            listState = listState
        )
    }
}

@Composable
private fun ChatContent(
    originUser: UserWelan,
    destinationImgRef: String,
    destinationName: String,
    modifier: Modifier,
    viewModel: ChatViewModel,
    listState: LazyListState
) {
    val chat = viewModel.chat
    LazyColumn(state = listState,
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxSize()) {
        items(chat) { message ->
            if (message.correo == originUser.correo) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 8.dp)
                ) {
                    Surface(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(
                            topStart = 24.dp,
                            topEnd = 16.dp,
                            bottomStart = 24.dp,
                            bottomEnd = 0.dp
                        ), border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Text(
                            text = message.mensaje!!,
                            modifier = Modifier.padding(8.dp).widthIn(max = 200.dp)
                        )
                    }
                    Spacer(Modifier.padding(horizontal = 8.dp))
                    val originBitmap = originUser.pathImage?.let { base64ToImageBitmap(it) }
                    if (originBitmap != null) {
                        Image(
                            bitmap = originBitmap,
                            contentDescription = originUser.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .requiredSize(40.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.imaguserdef),
                            contentDescription = originUser.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .requiredSize(40.dp)
                        )
                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 8.dp)
                ) {
                    val otherUserBitmap =
                        if (destinationImgRef.isNotBlank()) base64ToImageBitmap(destinationImgRef) else null
                    if (otherUserBitmap != null) {
                        Image(
                            bitmap = otherUserBitmap,
                            contentDescription = destinationName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .requiredSize(40.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.imaguserdef),
                            contentDescription = destinationName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .requiredSize(40.dp)
                        )
                    }
                    Spacer(Modifier.padding(horizontal = 8.dp))
                    Surface(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(
                            topEnd = 24.dp,
                            topStart = 16.dp,
                            bottomEnd = 24.dp,
                            bottomStart = 0.dp
                        ), border = BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary)
                    ) {
                        Text(
                            text = message.mensaje!!,
                            modifier = Modifier.padding(8.dp).widthIn(max = 200.dp)
                        )
                    }
                }
            }
        }
    }

}