package com.example.welan.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.welan.R
import com.example.welan.model.ProductoOServicio
import com.example.welan.model.UserWelan
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.componentes.imageUriToBase64
import com.example.welan.ui.theme.primaryColor
import com.example.welan.ui.viewmodels.SerOProdViewModel

@Composable
fun CrearServicioProducto(modifier: Modifier, navController: NavHostController, userWeLan: UserWelan, serOProdViuewModel: SerOProdViewModel) {
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var unidadmedida by remember { mutableStateOf("") }
    var envio by rememberSaveable { mutableStateOf("") }
    var visibilidad by remember { mutableStateOf(false) }
    var choose by remember { mutableStateOf(false) }
    val series = listOf("Unidad", "Servicio", "Horas", "Kilogramos")
    var errortitulo by remember { mutableStateOf(false) }
    var errordescripcion by remember { mutableStateOf(false) }
    var errorprecio by remember { mutableStateOf(false) }
    var errorunidadmedida by remember { mutableStateOf(false) }
    var errorenvio by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var image :String? by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val base64 = imageUriToBase64(context, uri)
            image = base64
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Column (modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            MultiChoiceSegmentedButtonRow(
                modifier = Modifier.fillMaxWidth(0.7f).padding(vertical = 10.dp)
            ) {

                SegmentedButton(
                    checked = !choose,
                    onCheckedChange = { choose = false
                                      unidadmedida = if (unidadmedida == "Kilogramos") "" else unidadmedida
                                      },
                    shape = SegmentedButtonDefaults.itemShape(0, 2),
                    colors = SegmentedButtonColors(
                        activeBorderColor = Color.Black,
                        activeContainerColor = Color(0xABFC5656),
                        activeContentColor = White,

                        inactiveContainerColor = Color.Transparent,
                        inactiveContentColor = White,
                        inactiveBorderColor = Color.Black,

                        disabledActiveContainerColor = Color.Transparent,
                        disabledActiveContentColor = White,
                        disabledActiveBorderColor = Color.Black,
                        disabledInactiveContainerColor = Color.Transparent,
                        disabledInactiveContentColor = White,
                        disabledInactiveBorderColor = Color.Black
                    ),
                    modifier = Modifier.height(50.dp),
                    icon = {}
                ) {
                    Text("Servicio")
                }

                SegmentedButton(
                    checked = choose,
                    onCheckedChange = { choose = true },
                    shape = SegmentedButtonDefaults.itemShape(1, 2),
                    colors = SegmentedButtonColors(
                        activeBorderColor = Color.Black,
                        activeContainerColor = Color(0xABFC5656),
                        activeContentColor = White,

                        inactiveContainerColor = Color.Transparent,
                        inactiveContentColor = White,
                        inactiveBorderColor = Color.Black,

                        disabledActiveContainerColor = Color.Transparent,
                        disabledActiveContentColor = White,
                        disabledActiveBorderColor = Color.Black,
                        disabledInactiveContainerColor = Color.Transparent,
                        disabledInactiveContentColor = White,
                        disabledInactiveBorderColor = Color.Black
                    ),
                    modifier = Modifier.height(50.dp),
                    icon = {}
                ) {
                    Text("Producto")
                }
            }
            Text(
                text = "Titulo:",
                fontSize = 18.sp,
                color = White,
                modifier = Modifier.padding(bottom = 10.dp, end = 200.dp)
            )
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                singleLine = true,
                isError = errortitulo,
                colors = OutlinedTextFieldDefaults.colors(errorBorderColor = primaryColor, focusedBorderColor = White),
            )
            Text(
                text = if (errortitulo) "Error: Obligatorio" else "",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(0.65f),
                textAlign = TextAlign.Start
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(
                    onClick = {
                        launcher.launch("image/*")
                    },
                    modifier = Modifier.weight(0.4f)
                        .height(40.dp)
                        .size(20.dp)
                        .padding(start = 40.dp, end = 40.dp)
                ) {
                    Text("Subir Imagen")
                }
                if (image != null) {
                    Image(
                        bitmap = base64ToImageBitmap(image!!)!!,
                        contentDescription = "ServicioOProducto",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.height(50.dp)
                    )
                } else {
                    val imagendef = if (!choose) painterResource(R.drawable.ic_producto) else painterResource(R.drawable.ic_servicio)
                    Image(
                        painter = imagendef,
                        contentDescription = "ServicioOProducto",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.height(50.dp)
                    )
                }
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
                maxLines = 2,
                onValueChange = { descripcion = it },
                isError = errordescripcion,
                colors = OutlinedTextFieldDefaults.colors(errorBorderColor = primaryColor, focusedBorderColor = White),
            )
            Text(
                text = if (errordescripcion) "Error: Obligatorio" else "",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(0.65f),
                textAlign = TextAlign.Start
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 40.dp, bottom = 5.dp)
            ) {
                Column(modifier = Modifier.weight(0.5f).padding(20.dp)) {
                    Text(
                        text = "Precio:",
                        fontSize = 18.sp,
                        color = White,
                        modifier = Modifier.padding(bottom = 10.dp, end = 50.dp)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            OutlinedTextField(
                                value = precio,
                                onValueChange = { precio = it },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine = true,
                                isError = errorprecio,
                                colors = OutlinedTextFieldDefaults.colors(errorBorderColor = primaryColor, focusedBorderColor = White),
                            )
                            Text(
                                text = if (errorprecio) "Error: Obligatorio" else "",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Red
                            )
                        }
                        Text(
                            text = "€",
                            fontSize = 18.sp,
                            color = White,
                            modifier = Modifier.padding(start = 5.dp, end = 50.dp)
                        )
                    }
                }
                Spacer(Modifier.padding(end = 8.dp))
                Column(modifier = Modifier.weight(0.5f)) {
                    if (choose) {
                        Text(
                            text = "Tipo de Unidad:",
                            fontSize = 18.sp,
                            color = White,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                    } else {
                        Text(
                            text = "Forma de Cobro:",
                            fontSize = 18.sp,
                            color = White,
                            modifier = Modifier.padding(bottom = 5.dp)
                        )
                    }
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
                            label = { Text(text = "Tipo") },
                            isError = errorunidadmedida,
                            colors = OutlinedTextFieldDefaults.colors(errorBorderColor = primaryColor, focusedBorderColor = White),
                        )
                        DropdownMenu(
                            expanded = visibilidad,
                            onDismissRequest = { visibilidad = false },
                            modifier = Modifier.width(300.dp)
                        ) {
                            series.forEach { serie ->
                                if (!choose) {
                                    if (serie != "Kilogramos" && serie != "Unidad") {
                                        DropdownMenuItem(
                                            onClick = {
                                                visibilidad = false
                                                unidadmedida = serie
                                            },
                                            text = { Text(serie) }
                                        )
                                    }
                                } else if (serie != "Servicio") {
                                    DropdownMenuItem(
                                        onClick = {
                                            visibilidad = false
                                            unidadmedida = serie
                                        },
                                        text = { Text(serie) }
                                    )
                                }
                            }
                        }
                        Text(
                            text = if (errorunidadmedida) "Error: Obligatorio" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red
                        )
                    }
                }
            }
            if (choose) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Envio:",
                        fontSize = 18.sp,
                        color = White,
                        modifier = Modifier.padding(end = 60.dp)
                    )
                    Text(
                        text = if (errorenvio) "Error: Obligatorio" else "",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Red,
                        modifier = Modifier.padding(end = 60.dp)
                    )
                }
                MyRadioButton(envio, { envio = it })
                Spacer(Modifier.padding(bottom = 70.dp))
            }
        }
        Button(
            onClick = {
                if (titulo == "") errortitulo = true else errortitulo = false
                if (descripcion == "") errordescripcion = true else errordescripcion = false
                if (precio == "") errorprecio = true else errorprecio = false
                if (unidadmedida == "") errorunidadmedida = true else errorunidadmedida = false
                if (choose && envio == "") errorenvio = true else errorenvio = false
                if (!errortitulo && !errordescripcion && !errorprecio && !errorunidadmedida && ((choose && !errorenvio) || (!choose))) {
                    val unidad = when(unidadmedida) {
                        "Kilogramos " -> "KILOGRAMOS"
                        "Horas" -> "HORAS"
                        else -> "UNIDAD"
                    }
                    val enviar = when(unidadmedida) {
                        "No enviar " -> "NOENVIO"
                        "Posibilidad envio" -> "POSSIBILIDADENVIO"
                        else -> "OBLIGACIONENVIO"
                    }
                    val serOProd = ProductoOServicio(
                        precio = precio.toFloat(),
                        tipo = if (choose) "Servicio" else "Producto",
                        tipo_precio = unidad,
                        img_path = image,
                        descripcion = descripcion,
                        envio = if (choose) enviar else "NOENVIO",
                        correo = userWeLan.correo,
                        nombre = titulo
                    )
                    serOProdViuewModel.postSerOProd(serOProd)
                    serOProdViuewModel.getSerOProd()
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 20.dp).align(Alignment.BottomEnd)
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