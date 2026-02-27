package com.example.welan.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.welan.R
import com.example.welan.model.Envio
import com.example.welan.model.MensajesData
import com.example.welan.model.Producto
import com.example.welan.model.Servicio
import com.example.welan.model.Unidad
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.theme.primaryColor
import com.example.welan.ui.theme.secondaryColor
import com.example.welan.ui.viewmodels.ChatViewModel
import com.example.welan.ui.viewmodels.MessagesViewModel
import com.example.welan.ui.viewmodels.SerOProdViewModel
import com.example.welan.ui.viewmodels.UserViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime


@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SevicioProducto(
    serOProdViewModel: SerOProdViewModel,
    modifier: Modifier,
    navController: NavHostController,
    messagesViewModel: MessagesViewModel,
    chatVm: ChatViewModel,
    userViewModel: UserViewModel
) {
    var direccion by remember { mutableStateOf("") }
    var envio by remember { mutableStateOf(false) }
    var errorDireccion by remember { mutableStateOf(false) }
    val selectedFserOProd by serOProdViewModel.serOProdSelected.collectAsState()
    val scope = rememberCoroutineScope()

    Box(modifier = modifier.fillMaxSize()) {
        Column {
            if (selectedFserOProd.pathImage != null) {
                Image(
                    bitmap = base64ToImageBitmap(selectedFserOProd.pathImage!!)!!,
                    contentDescription = "ServicioOProducto",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
            } else {
                val imagendef = if (selectedFserOProd is Producto) painterResource(R.drawable.ic_producto) else painterResource(R.drawable.ic_servicio)
                Image(
                    painter = imagendef,
                    contentDescription = "ServicioOProducto",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxWidth().height(200.dp)
                )
            }
            Text(
                text = selectedFserOProd.nombre,
                fontSize = 24.sp,
                maxLines = 2,
                lineHeight = 26.sp,
                modifier = Modifier.padding(5.dp).fillMaxWidth(),
                color = White,
                textAlign = TextAlign.Center
            )
            Text(
                text = selectedFserOProd.descripcion,
                fontSize = 12.sp,
                maxLines = 5,
                lineHeight = 32.sp,
                modifier = Modifier.padding(5.dp).fillMaxWidth(),
                color = White,
                textAlign = TextAlign.Center
            )
        }
        Column (modifier = Modifier.align(Alignment.BottomEnd)){
            Row (modifier = Modifier.fillMaxWidth()) {
                if (selectedFserOProd is Producto && selectedFserOProd.envio != Envio.NOENVIO) {
                    Column (modifier = Modifier.padding(start = 20.dp).weight(0.55f)) {
                        if (selectedFserOProd.envio == Envio.POSSIBILIDADENVIO) {
                            Row ( verticalAlignment = Alignment.CenterVertically){
                                Text(text = "¿Enviar a casa?")
                                Switch(
                                    checked = envio,
                                    onCheckedChange = { envio = !envio},
                                    modifier = Modifier.padding(start = 10.dp),
                                    colors = SwitchDefaults.colors(uncheckedIconColor = secondaryColor)
                                )
                            }
                            OutlinedTextField(
                                enabled = envio,
                                value = direccion,
                                label = { Text("Direccion de envio") },
                                onValueChange = { direccion = it },
                                isError = errorDireccion,
                                colors = OutlinedTextFieldDefaults.colors(errorBorderColor = primaryColor, focusedBorderColor = White),
                            )
                            if (envio) {
                                Text(
                                    text = if (errorDireccion) "Error: Obligatorio" else "",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Red
                                )
                            }
                        } else {
                            Text(text = "Enviar obligatorio")
                            OutlinedTextField(
                                enabled = true,
                                value = direccion,
                                label = { Text("Direccion de envio") },
                                onValueChange = { direccion = it },
                                isError = errorDireccion,
                                colors = OutlinedTextFieldDefaults.colors(errorBorderColor = primaryColor, focusedBorderColor = White),

                            )
                            Text(
                                text = if (errorDireccion) "Error: Obligatorio" else "",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Red
                            )
                        }
                    }
                }
                Box(modifier = Modifier.padding(5.dp).weight(0.25f)) {
                    Text(
                        text = if (selectedFserOProd is Servicio) {
                            if (selectedFserOProd.tipo_precio == Unidad.UNIDADES) {
                                "${selectedFserOProd.precio}€/Ser"
                            } else {
                                "${selectedFserOProd.precio}€/${selectedFserOProd.tipo_precio.unidad}"
                            }
                        } else {
                            "${selectedFserOProd.precio}€/${selectedFserOProd.tipo_precio.unidad}"
                        },
                        fontSize = 18.sp,
                        maxLines = 3,
                        lineHeight = 32.sp,
                        color = White,
                        modifier = Modifier.align(Alignment.BottomEnd).padding(top = 70.dp, end = 20.dp)
                    )
                }
            }

            Button(
                onClick = {

                    if ((direccion == "" && envio) || (direccion == "" && selectedFserOProd.envio == Envio.OBLIGACIONENVIO)) {
                        errorDireccion = true
                    } else {
                        scope.launch {
                        val (existe, tieneMensaje) = messagesViewModel.validarCorreoYMensaje(serOProdViewModel.serOProdSelected.value.correo)


                             if (tieneMensaje) {
                                 chatVm.correo1 = messagesViewModel._correo
                                 chatVm.correo2 = serOProdViewModel.serOProdSelected.value.correo
                                 var tipo = if (serOProdViewModel.serOProdSelected.value is Servicio){
                                      "Servicio"
                                 }else{
                                     "Producto"
                                 }

                                 var envio = when(serOProdViewModel.serOProdSelected.value.envio){
                                     Envio.NOENVIO -> {
                                        "sin posibilidad de envio"
                                     }
                                     Envio.POSSIBILIDADENVIO -> {
                                         "con posibilidad de envio"
                                     }

                                     else -> {"con envio obligatorio" }
                                 }
                                 var tipounidad = when(selectedFserOProd.tipo_precio){
                                     Unidad.UNIDADES -> {
                                         "unidad"
                                     }
                                     Unidad.HORAS -> {
                                         "hora"
                                     }
                                     Unidad.KILOGRAMOS -> {
                                         "kilogramo"
                                     }
                                 }
                                 chatVm.postChat("Hola, me gustaria comprar el ${tipo}: ${selectedFserOProd.nombre}, ${envio}, con un precio de ${selectedFserOProd.precio} € por ${tipounidad}")
                               var user = userViewModel.getBusinessUserMessage(selectedFserOProd.correo)
                                messagesViewModel.mensajeSelected = MensajesData(
                                    img_conversation = user!!.pathImage!!,
                                    nombre = user!!.nombre,
                                    correo = selectedFserOProd.correo,
                                    lastMessage = "Hola, me gustaria comprar el ${tipo}: ${selectedFserOProd.nombre}, ${envio}, con un precio de ${selectedFserOProd.precio} € por ${tipounidad}",
                                    dateLastMessage = LocalDateTime.now()
                                )
                             }else {

                                messagesViewModel.createMessage(serOProdViewModel.serOProdSelected.value.correo)
                                chatVm.correo1 = messagesViewModel._correo
                                chatVm.correo2 = serOProdViewModel.serOProdSelected.value.correo
                                 var tipo = if (serOProdViewModel.serOProdSelected.value is Servicio){
                                     "Servicio"
                                 }else{
                                     "Producto"
                                 }

                                 var envio = when(serOProdViewModel.serOProdSelected.value.envio){
                                     Envio.NOENVIO -> {
                                         "sin posibilidad de envio"
                                     }
                                     Envio.POSSIBILIDADENVIO -> {
                                         "con posibilidad de envio"
                                     }

                                     else -> {"con envio obligatorio" }
                                 }
                                 var tipounidad = when(selectedFserOProd.tipo_precio){
                                     Unidad.UNIDADES -> {
                                         "unidad"
                                     }
                                     Unidad.HORAS -> {
                                         "hora"
                                     }
                                     Unidad.KILOGRAMOS -> {
                                         "kilogramo"
                                     }
                                     else -> {}
                                 }
                                 chatVm.postChat("Hola, me gustaria comprar el ${tipo}: ${selectedFserOProd.nombre}, ${envio}, con un precio de ${selectedFserOProd.precio} € por ${tipounidad}")

                                     var user =
                                         userViewModel.getBusinessUserMessage(selectedFserOProd.correo)
                                     messagesViewModel.mensajeSelected = MensajesData(
                                         img_conversation = user!!.pathImage!!,
                                         nombre = user!!.nombre,
                                         correo = selectedFserOProd.correo,
                                         lastMessage = "Hola, me gustaria comprar el ${tipo}: ${selectedFserOProd.nombre}, ${envio}, con un precio de ${selectedFserOProd.precio} € por ${tipounidad}",
                                         dateLastMessage = LocalDateTime.now()
                                     )
                                 }
                            navController.navigate("Chat")
                        }
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Comprar")
            }
        }
    }
}