package com.example.welan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.welan.model.MensajesData
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.viewmodels.MensajesUiState
import com.example.welan.ui.viewmodels.MessagesViewModel
import com.example.welan.ui.viewmodels.NoticiasUiState

@Composable
fun MensajesScreen(
      mensajesViewModel: MessagesViewModel,
    navController: NavController,
    retryAction: () -> Unit,
    modifier: Modifier,
    ){
    when(mensajesViewModel.mensajesUiState){
        is MensajesUiState.Success -> {Mensajes(navController,modifier, (mensajesViewModel.mensajesUiState as MensajesUiState.Success).data) }
        is MensajesUiState.Error -> {
            ErrorScreen(retryAction)
        }
        is MensajesUiState.Loading -> {LoadingScreen()}
    }
}
@Composable
fun Mensajes(navController: NavController, modifier: Modifier = Modifier,
             mesnajes : List<MensajesData>){
        Box(modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.TopCenter)) {
            items(mesnajes) { message ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).heightIn(min = 50.dp)
                    .clickable{
                     //   navController.navigate("Chat/${message.imageUserRef}/${message.nameUser}")
                    }) {
                    var lastmess =  if (message.lastMessage.length > 15) {
                        "${message.lastMessage.take(15)}..."
                    } else {
                        message.lastMessage
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxSize()
                    ) {

                        Image(
                            bitmap = base64ToImageBitmap(message.img_conversation)!!,
                            contentDescription = message.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(40.dp)
                        )

                        Column (
                        ){
                            Text(
                                text = message.nombre,
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth(0.9f)
                                )
                            Row (
                                modifier = Modifier.fillMaxWidth()
                            ){
                                Text(
                                    text = lastmess,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontSize = 8.sp,
                                    textAlign = TextAlign.End,
                                )
                                Text(
                                    text = message.dateLastMessage.toString(),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontSize = 8.sp,
                                    textAlign = TextAlign.End,

                                )
                            }
                        }
                    }
                }
            }
        }
    }
}