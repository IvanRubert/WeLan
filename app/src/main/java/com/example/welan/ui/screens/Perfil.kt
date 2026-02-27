package com.example.welan.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.welan.R
import com.example.welan.data.GuardarUsuario
import com.example.welan.model.BusinessUserEdit
import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserEdit
import com.example.welan.model.CommonUserWeLan
import com.example.welan.model.Interes
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.componentes.compPassword
import com.example.welan.ui.componentes.imageUriToBase64
import com.example.welan.ui.theme.primaryColor
import com.example.welan.ui.viewmodels.UserViewModel
import kotlinx.coroutines.runBlocking

@Composable
fun PerfilUser(user: CommonUserWeLan, navController: NavController, modifier: Modifier, navControllerPrincipal: NavHostController) {
    var intereses: List<Interes> = emptyList()
    val context = LocalContext.current

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = "Tu Perfil",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    if (user.pathImage != null) {
                        Image(
                            bitmap = base64ToImageBitmap(user.pathImage!!)!!,
                            contentDescription = user.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.imaguserdef),
                            contentDescription = user.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                    }
                    Spacer(Modifier.padding(bottom = 8.dp))
                    Button(
                        onClick = { navController.navigate("UserWeLanEdit") },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) { Text(text = "Modificar perfil", textAlign = TextAlign.Center) }
                }
                Column(modifier = Modifier.padding(top = 8.dp, end = 16.dp, bottom = 16.dp)) {
                    Text(
                        text = "Nombre completo",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = user.nombre, modifier = Modifier.padding(bottom = 16.dp))

                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = user.correo, modifier = Modifier.padding(bottom = 16.dp))
                }
            }
            Text(
                text = "Intereses",
                style = MaterialTheme.typography.titleMedium
            )
            Card(Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .requiredHeight(190.dp)) {
                Spacer(Modifier
                    .height(8.dp)
                    .fillMaxWidth())
                LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                    if (!user.intereses.isNullOrEmpty()) {
                        intereses = user.intereses
                    }
                    items(intereses) { interes ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = Color(interes.color)
                            ),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = interes.nombre, textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                runBlocking{
                    GuardarUsuario(context, "", "")
                }
                navControllerPrincipal.navigate("Login") {
                    popUpTo(0){
                        inclusive = true
                    }
                }
            },
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun PerfilEmpresa(user: BusinessUserWeLan, navController: NavController, modifier: Modifier, navControllerPrincipal: NavHostController) {
    var servicios: List<String> = emptyList()
    val context = LocalContext.current
    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = "Tu Perfil",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    if (user.pathImage != null) {
                        Image(
                            bitmap = base64ToImageBitmap(user.pathImage!!)!!,
                            contentDescription = user.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.ic_connection_error),
                            contentDescription = user.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                    }
                    Spacer(Modifier.padding(bottom = 8.dp))
                    Button(
                        onClick = { navController.navigate("UserWeLanEdit") },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) { Text("Modificar perfil", textAlign = TextAlign.Center) }
                }
                Column(modifier = Modifier.padding(top = 8.dp, end = 16.dp, bottom = 16.dp)) {
                    Text(
                        text = "Nombre completo",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = user.nombre, modifier = Modifier.padding(bottom = 16.dp))

                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = user.correo, modifier = Modifier.padding(bottom = 16.dp))

                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .width(200.dp)
                            .height(150.dp)
                            .padding(top = 8.dp)
                    ) {
                        Text(
                            text = user.descripcion ?: "",
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .verticalScroll(rememberScrollState())
                        )
                    }
                }
            }
            Text(
                text = "Servicios",
                style = MaterialTheme.typography.titleMedium
            )
            Card(Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .requiredHeight(170.dp)) {
                Spacer(Modifier
                    .height(8.dp)
                    .fillMaxWidth())
                LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                    if (!user.servicios.isNullOrEmpty()) {
                        servicios = user.servicios
                    }
                    items(servicios) { servicio ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = servicio, textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                runBlocking{
                    GuardarUsuario(context, "", "")
                }
                navControllerPrincipal.navigate("Login") {
                    popUpTo(0){
                        inclusive = true
                    }
                }
            },
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Cerrar Sesión")
        }
    }
}

@Composable
fun UserEdit( userViewModel: UserViewModel, user: CommonUserWeLan, navController: NavController, modifier: Modifier = Modifier) {
    var nameTF by rememberSaveable { mutableStateOf(user.nombre) }
    val interests = rememberSaveable { mutableStateListOf<Interes>().apply{addAll(
        if (!user.intereses.isNullOrEmpty()) user.intereses else emptyList()
    )} }
    var image by rememberSaveable { mutableStateOf(user.pathImage) }
    var showDialogAdd by remember { mutableStateOf(false) }
    var showDialogPut by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val base64 = imageUriToBase64(context, uri)
            user.pathImage = base64
            image = base64
        }
    }

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = "Tu Perfil",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    if (image != null) {
                        Image(
                            bitmap = base64ToImageBitmap(image!!)!!,
                            contentDescription = user.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.ic_connection_error),
                            contentDescription = user.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                    }
                    Spacer(Modifier.padding(bottom = 8.dp))
                    Button(
                        onClick = {
                            launcher.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) { Text("Cambiar imagen", textAlign = TextAlign.Center) }
                }
                Column(modifier = Modifier.padding(top = 8.dp, end = 16.dp, bottom = 16.dp)) {
                    Text(
                        text = "Nombre completo",
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        value = nameTF,
                        onValueChange = { nameTF = it },
                        singleLine = true,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .width(200.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary)
                    )

                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = user.correo, modifier = Modifier.padding(bottom = 16.dp))

                    Text(
                        text = "Contraseña",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Button(
                        onClick = { showDialogPut = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        shape = RoundedCornerShape(8.dp)
                    ) { Text("Cambiar Contraseña") }

                    if (showDialogPut){
                        AddPasswordDialog(
                            user,
                            userViewModel,
                            onDismiss = { showDialogPut = false }
                        )
                    }
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Intereses",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.padding(16.dp))
                Button(
                    onClick = { showDialogAdd = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(8.dp)
                ) { Text("Añadir interés") }
            }
            Card(Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .requiredHeight(190.dp)) {
                Spacer(Modifier
                    .height(8.dp)
                    .fillMaxWidth())
                LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                    items(interests) { interes ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Box(Modifier.fillMaxWidth()) {
                                Text(
                                    text = interes.nombre, textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(
                                            top = 10.dp,
                                            bottom = 8.dp,
                                            start = 8.dp,
                                            end = 40.dp
                                        )
                                )
                                IconButton(onClick = { interests.remove(interes) },
                                    modifier = Modifier.align(Alignment.TopEnd)) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Delete interest"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                val useredit = CommonUserEdit(
                    pathImage = image,
                    nombre = nameTF,
                    correo = user.correo,
                    interesList = interests,
                )
                userViewModel.putCommonUser(useredit)
                navController.navigate("Perfil") {
                    popUpTo("Perfil"){
                        inclusive = true
                    }
                }
            },
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Guardar cambios")
        }

        if (showDialogAdd){
            AddInterestDialogInteres(
                type = "interés",
                list = interests,
                onDismiss = { showDialogAdd = false }
            )
        }
    }
}

@Composable
fun BusinessEdit(userViewModel: UserViewModel, user: BusinessUserWeLan, navController: NavController, modifier: Modifier = Modifier) {
    var nameTF by rememberSaveable { mutableStateOf(user.nombre) }
    var descripcionTF by rememberSaveable { mutableStateOf(if (!user.descripcion.isNullOrEmpty()) user.descripcion else "") }
    var image by rememberSaveable { mutableStateOf(user.pathImage) }
    val services = rememberSaveable { mutableStateListOf<String>().apply{addAll(
        if (!user.servicios.isNullOrEmpty()) user.servicios else emptyList()
    )} }
    var showDialogAdd by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val base64 = imageUriToBase64(context, uri)
            user.pathImage = base64
            image = base64
        }
    }
    Box() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier.fillMaxSize()
        ) {
            Text(
                text = "Tu Perfil",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    if (user.pathImage != null) {
                        Image(
                            bitmap = base64ToImageBitmap(user.pathImage!!)!!,
                            contentDescription = user.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.ic_connection_error),
                            contentDescription = user.nombre,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(100.dp)
                        )
                    }
                    Spacer(Modifier.padding(bottom = 8.dp))
                    Button(
                        onClick = {
                            launcher.launch("image/*")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) { Text("Cambiar imagen", textAlign = TextAlign.Center) }
                }
                Column(modifier = Modifier.padding(top = 8.dp, end = 16.dp, bottom = 16.dp)) {
                    Text(
                        text = "Nombre completo",
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        value = nameTF,
                        onValueChange = { nameTF = it },
                        singleLine = true,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .width(200.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary)
                    )

                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = user.correo, modifier = Modifier.padding(bottom = 16.dp))

                    Text(
                        text = "Descripción",
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        value = descripcionTF,
                        onValueChange = { descripcionTF = it },
                        maxLines = 2,
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .width(200.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary)
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Servicios",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.padding(16.dp))
                Button(
                    onClick = { showDialogAdd = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(8.dp)
                ) { Text("Añadir servicio") }
            }
            Card(Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                .requiredHeight(170.dp)) {
                Spacer(Modifier
                    .height(8.dp)
                    .fillMaxWidth())
                LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(2)) {
                    items(services) { servicio ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Box(Modifier.fillMaxWidth()) {
                                Text(
                                    text = servicio, textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(
                                            top = 10.dp,
                                            bottom = 8.dp,
                                            start = 8.dp,
                                            end = 40.dp
                                        )
                                )
                                IconButton(onClick = { services.remove(servicio) },
                                    modifier = Modifier.align(Alignment.TopEnd)) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Delete service"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Button(
            onClick = {
                val useredit = BusinessUserEdit(
                    pathImage = image,
                    nombre = nameTF,
                    correo = user.correo,
                    descripcion = descripcionTF,
                    serviciosList = services
                )
                userViewModel.putBusinessUser(useredit)
                navController.navigate("Perfil") {
                    popUpTo("Perfil"){
                        inclusive = true
                    }
                }
            },
            modifier = modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Guardar cambios")
        }
        if (showDialogAdd){
            AddInterestDialogString(
                type = "servicio",
                list = services,
                onDismiss = { showDialogAdd = false }
            )
        }
    }
}

@Composable
fun AddPasswordDialog(user: CommonUserWeLan, userViewModel: UserViewModel, onDismiss: () -> Unit){
    var passTF by rememberSaveable { mutableStateOf(user.passHash) }
    var showPass by rememberSaveable { mutableStateOf(false) }
    var isError by remember { mutableStateOf(false) }
    AlertDialog(
        title = {
            Text(text = "Añadir Contraseña")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = passTF,
                    onValueChange = { passTF = it },
                    isError = isError,
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { showPass = !showPass }) {
                            Icon(
                                imageVector = if (showPass) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Show or occult password"
                            )
                        }
                    },
                    visualTransformation = if (showPass) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .width(200.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        errorBorderColor = Color.Red,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                if (isError){
                    Text(text = "Error: La contraseña no tiene el formato correcto:\nUna letra Majuscula\nUna letra minuscula\nUn Numero\nUn caracter Especial",
                        color = Color.Red)
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (compPassword(passTF)){
                        userViewModel.putCommonUserPassword(user.correo, passTF)
                    } else {
                        isError = true
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

@Composable
fun AddInterestDialogInteres(type: String, list: SnapshotStateList<Interes>, onDismiss: () -> Unit){
    var interes by remember { mutableStateOf("") }
    var interestTF by remember { mutableStateOf(Interes("", primaryColor.value.toLong())) }
    var isError by remember { mutableStateOf(false) }
    var eMessage = ""
    AlertDialog(
        title = {
            Text(text = "Añadir $type")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = interes,
                    onValueChange = { interes = it },
                    isError = isError,
                    singleLine = true,
                    label = {Text(text = type)},
                    colors = OutlinedTextFieldDefaults.colors(
                        errorBorderColor = Color.Red,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                if (isError){
                    Text(text = "Error: No se puede añadir un $type $eMessage",
                        color = Color.Red)
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    interestTF.nombre = interes
                    if (interestTF.nombre.isNotBlank()){
                        val temp = interestTF.nombre.trim().lowercase().toCharArray()
                        temp[0] = temp[0].uppercaseChar()
                        interestTF.nombre = String(temp)
                        if (!list.contains(interestTF)){
                            isError = false
                            list.add(interestTF)
                            onDismiss.invoke()
                        }else {
                            isError = true
                            eMessage = "repetido"
                        }
                    } else {
                        isError = true
                        eMessage = "vacio"
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

@Composable
fun AddInterestDialogString(type: String, list: SnapshotStateList<String>, onDismiss: () -> Unit){
    var interestTF by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var eMessage = ""
    AlertDialog(
        title = {
            Text(text = "Añadir $type")
        },
        text = {
            Column {
                OutlinedTextField(
                    value = interestTF,
                    onValueChange = { interestTF = it },
                    isError = isError,
                    singleLine = true,
                    label = {Text(text = type)},
                    colors = OutlinedTextFieldDefaults.colors(
                        errorBorderColor = Color.Red,
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
                if (isError){
                    Text(text = "Error: No se puede añadir un $type $eMessage",
                        color = Color.Red)
                }
            }
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    if (interestTF.isNotBlank()){
                        val temp = interestTF.trim().lowercase().toCharArray()
                        temp[0] = temp[0].uppercaseChar()
                        interestTF = String(temp)
                        if (!list.contains(interestTF)){
                            isError = false
                            list.add(interestTF)
                            onDismiss.invoke()
                        }else {
                            isError = true
                            eMessage = "repetido"
                        }
                    } else {
                        isError = true
                        eMessage = "vacio"
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