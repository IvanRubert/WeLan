package com.example.welan.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.welan.model.MensajesData
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.componentes.parseFecha
import com.example.welan.ui.viewmodels.MensajesUiState
import com.example.welan.ui.viewmodels.MessagesViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MensajesScreen(
      mensajesViewModel: MessagesViewModel,
    navController: NavHostController,
    retryAction: () -> Unit,
    modifier: Modifier,
    ){
    when(mensajesViewModel.mensajesUiState){
        is MensajesUiState.Success -> {Mensajes(
            navController,
            modifier,
            (mensajesViewModel.mensajesUiState as MensajesUiState.Success).data,mensajesViewModel) }
        is MensajesUiState.Error -> {
            ErrorScreen(retryAction)
        }
        is MensajesUiState.Loading -> {LoadingScreen()}
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Mensajes(navController: NavHostController, modifier: Modifier = Modifier,
             mesnajes : List<MensajesData>, messagesViewModel: MessagesViewModel){
        Box(modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.TopCenter)) {
            items(mesnajes) { message ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).heightIn(min = 50.dp)
                    .clickable{
                        messagesViewModel.mensajeSelected = message
                        navController.navigate("Chat")
                    }) {
                    var lastmess =  if (message.lastMessage.isNullOrBlank()){""
                        }else if(message.lastMessage.length > 15) {
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
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.weight(0.2f)
                                )

                                Text(
                                    text = parseFecha( message.dateLastMessage),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontSize = 8.sp,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier.weight(0.3f)

                                )
                            }
                        }
                    }
                }
            }
        }
            FABSMesage(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp), messagesViewModel)
    }
}

@Composable
fun FABSMesage(modifier: Modifier, messagesViewModel: MessagesViewModel) {
    var showDialogPut by remember { mutableStateOf(false) }

    if (showDialogPut) {
        CustomDialog(
            onDismiss = { showDialogPut = false },
            messagesViewModel = messagesViewModel
        )
    }

    FloatingActionButton(
        onClick = { showDialogPut = true },
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Icon(Icons.Filled.Add, "Crear servicio o producto")
    }
}

@Composable
fun CustomDialog(onDismiss: () -> Unit, messagesViewModel: MessagesViewModel) {
    var correo by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorText by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    AlertDialog(
        title = {
            Text(text = "Añadir Correo")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    isError = isError,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .width(200.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        errorBorderColor = Color.Red,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                if (isError && errorText != null){
                    Text(
                        text = errorText!!,
                        color = Color.Red
                    )
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    scope.launch {
                        if (correo.isBlank()) {
                            isError = true
                            errorText = "Introduce un correo"
                            return@launch
                        }
                        isError = false
                        errorText = null
                        val (existe, tieneMensaje) =
                            messagesViewModel.validarCorreoYMensaje(correo)
                        if (!existe) {
                            isError = true
                            errorText = "Error: El correo no existe"
                        } else if (tieneMensaje) {
                            isError = true
                            errorText = "Ya existe una conversación con este correo"
                        } else {
                            isError = false
                            errorText = null
                            messagesViewModel.createMessage(correo)
                            onDismiss()
                        }
                    }
                }
            ) {
                Text("Añadir")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}
