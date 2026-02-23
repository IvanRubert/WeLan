package com.example.welan.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.welan.model.User
import com.example.welan.model.UserWelan
import com.example.welan.ui.viewmodels.ForoViewModel

@Composable
fun ForoChat(originUser: UserWelan, modifier: Modifier = Modifier,
         viewModel: ForoViewModel
){
    val listState = rememberLazyListState()

    LaunchedEffect(viewModel.chat.size) {
        if (viewModel.chat.isNotEmpty()) {
            listState.scrollToItem(viewModel.chat.size - 1)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.scrollToEnd.collect {
            if (viewModel.chat.isNotEmpty()) {
                listState.animateScrollToItem(viewModel.chat.size - 1)
            }
        }
    }

    LazyColumn(state = listState,
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier.fillMaxSize()) {
        items(viewModel.chat){ message ->
            if (message.origin == originUser.nombre){
                Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 8.dp)) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(message.origin, textAlign = TextAlign.End, modifier = Modifier
                            .padding(end = 8.dp)
                        )
                        Spacer(Modifier.padding(end = 8.dp, top = 8.dp))
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
                                text = message.message,
                                modifier = Modifier.padding(8.dp).widthIn(max = 200.dp)
                            )
                        }
                    }
                }
            } else{
                Row(horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 8.dp)) {
                    Column {
                        Text(message.origin)
                        Spacer(Modifier.padding(start = 8.dp, top = 8.dp))
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
                                text = message.message,
                                modifier = Modifier.padding(8.dp).widthIn(max = 200.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}