package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun CrearServicioProducto(modifier: Modifier, navController: NavHostController) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var unidadmedida by remember { mutableStateOf("") }
    var envio by rememberSaveable { mutableStateOf("") }
    var visibilidad by remember { mutableStateOf(false) }
    val series = listOf("Unidad", "Horas", "Kilogramos")


    Column (modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Crear Servicio",
            fontSize = 30.sp,
            color = White,
            modifier = Modifier.padding(15.dp)
        )
        Text(
            text = "Titulo:",
            fontSize = 18.sp,
            color = White,
            modifier = Modifier.padding(bottom = 10.dp, end = 200.dp)
        )
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            modifier = Modifier.border(1.dp, White)
        )

        Row (verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                },
                modifier = Modifier.weight(0.4f)
                    .height(40.dp)
                    .size(20.dp)
                    .padding(start = 40.dp, end = 40.dp)
            ) {
                Text("Subir Imagen")
            }
            Image(
                painter = painterResource(id = R.drawable.tomates),
                contentDescription = "Contactos",
                contentScale = ContentScale.Fit,
                modifier = Modifier.height(130.dp).weight(0.2f).padding(end = 20.dp)
            )
        }
        Text(
            text = "Descripcíon:",
            fontSize = 18.sp,
            color = White,
            modifier = Modifier.padding(bottom = 10.dp, end = 200.dp)
        )
        OutlinedTextField(
            value = descripcion,
            minLines = 2,
            onValueChange = { descripcion = it },
            modifier = Modifier.border(1.dp, White)
        )

        Spacer(modifier = Modifier.padding(bottom = 10.dp))

        Row (verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 40.dp, bottom = 5.dp)
        ) {
            Column (modifier = Modifier.weight(0.5f).padding(20.dp)) {
                Text(
                    text = "Precio:",
                    fontSize = 18.sp,
                    color = White,
                    modifier = Modifier.padding(bottom = 10.dp, end = 50.dp)
                )
                Row (verticalAlignment = Alignment.CenterVertically){
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        modifier = Modifier.border(1.dp, White)
                    )
                    Text(
                        text = "€",
                        fontSize = 18.sp,
                        color = White,
                        modifier = Modifier.padding(start = 5.dp, bottom = 10.dp, end = 50.dp)
                    )
                }
            }

            Column (modifier = Modifier.weight(0.5f)) {
                Text(
                    text = "Unidad de medida:",
                    fontSize = 18.sp,
                    color = White,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                Column {
                    OutlinedTextField(
                        value = unidadmedida,
                        onValueChange = {
                            unidadmedida = it
                        },
                        enabled = false,
                        readOnly = true,
                        modifier = Modifier
                            .clickable { visibilidad = true },
                        label = { Text(text = "Unidad") }
                    )
                    DropdownMenu(
                        expanded = visibilidad,
                        onDismissRequest = { visibilidad = false },
                        modifier = Modifier.width(300.dp)
                    ) {
                        series.forEach { serie ->
                            DropdownMenuItem(
                                onClick = {
                                    visibilidad = false
                                    unidadmedida = serie
                                },
                                text = {Text(serie)}
                            )
                        }
                    }
                }
            }
        }
        Text(
            text = "Envio:",
            fontSize = 18.sp,
            color = White,
            modifier = Modifier.padding(end = 220.dp)
        )
        MyRadioButton(envio, { envio = it })
        Spacer(Modifier.padding(bottom = 5.dp))

        Button(
            onClick = { navController.navigate("BuscarServicioProducto") },
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp)
        ) {
            Text("Crear")
        }
    }
}

@Composable
fun MyRadioButton(envio: String, onItemSelected: (String) -> Unit) {
    val lista = arrayListOf(
        "No enviar", "Posibilidad envio", "Envio Obligatorio"
    )

    Row (verticalAlignment = Alignment.CenterVertically) {
        lista.forEach { nom ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = envio == nom, onClick = { onItemSelected(nom) })
                Text(text = nom, fontSize = 10.sp,)
            }
        }
    }

}