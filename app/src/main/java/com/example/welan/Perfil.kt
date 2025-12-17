package com.example.welan

import androidx.activity.OnBackPressedDispatcher
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.welan.data.User

var interestList: List<String> = emptyList()
var name: String = ""
var descripcion: String = ""
@Composable
fun PerfilUser(user: User, navController: NavController, modifier: Modifier, navControllerPrincipal: NavHostController) {
    if (interestList.isEmpty()) interestList = getInterests(user)
    if (name.isBlank()) name = getName(user)
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
                    Image(
                        painter = painterResource(user.imageRes),
                        contentDescription = user.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .padding(top = 16.dp, end = 16.dp, bottom = 8.dp)
                    )
                    Button(
                        onClick = { navController.navigate("UserEdit") },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) { Text("Modificar perfil") }
                }
                Column(modifier = Modifier.padding(top = 8.dp, end = 16.dp, bottom = 16.dp)) {
                    Text(
                        text = "Nombre completo",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = name, modifier = Modifier.padding(bottom = 16.dp))

                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = user.email, modifier = Modifier.padding(bottom = 16.dp))
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
                    items(interestList) { interes ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = interes, textAlign = TextAlign.Center,
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
fun PerfilEmpresa(user: User, navController: NavController, modifier: Modifier, navControllerPrincipal: NavHostController) {
    if (name.isBlank()) name = getName(user)
    if (descripcion.isBlank()) descripcion = user.descripcion ?: ""
    if (interestList.isEmpty()) {
        interestList = getInterests(user)
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
                    Image(
                        painter = painterResource(user.imageRes),
                        contentDescription = user.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .padding(top = 16.dp, end = 16.dp)
                    )
                    Button(
                        onClick = { navController.navigate("BusinessEdit") },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) { Text("Modificar perfil") }
                }
                Column(modifier = Modifier.padding(top = 8.dp, end = 16.dp, bottom = 16.dp)) {
                    Text(
                        text = "Nombre completo",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = name, modifier = Modifier.padding(bottom = 16.dp))

                    Text(
                        text = "Email",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(text = user.email, modifier = Modifier.padding(bottom = 16.dp))

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
                            text = descripcion, modifier = Modifier
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
                    items(interestList) { interes ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = interes, textAlign = TextAlign.Center,
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
fun UserEdit(user: User, navController: NavController, modifier: Modifier = Modifier) {
    var passTF by rememberSaveable { mutableStateOf("") }
    var showPass by rememberSaveable { mutableStateOf(false) }
    var nameTF by rememberSaveable { mutableStateOf(name) }
    val interests = rememberSaveable { mutableStateListOf<String>().apply{addAll(interestList)} }
    var showDialogAdd by remember { mutableStateOf(false) }
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
                    Image(
                        painter = painterResource(user.imageRes),
                        contentDescription = user.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .padding(top = 16.dp, end = 16.dp, bottom = 8.dp)
                    )
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) { Text("Cambiar imagen") }
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
                    Text(text = user.email, modifier = Modifier.padding(bottom = 16.dp))

                    Text(
                        text = "Contraseña",
                        style = MaterialTheme.typography.titleLarge
                    )
                    OutlinedTextField(
                        value = passTF,
                        onValueChange = { passTF = it },
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
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary)
                    )
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
                                    text = interes, textAlign = TextAlign.Center,
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
                interestList = interests
                name = nameTF
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
            AddInterestDialog(
                type = "interés",
                list = interests,
                onDismiss = { showDialogAdd = false }
            )
        }
    }
}

@Composable
fun BusinessEdit(user: User, navController: NavController, modifier: Modifier = Modifier) {
    var nameTF by rememberSaveable { mutableStateOf(name) }
    var descripcionTF by rememberSaveable { mutableStateOf(descripcion) }
    val interests = rememberSaveable { mutableStateListOf<String>().apply{addAll(interestList)} }
    var showDialogAdd by remember { mutableStateOf(false) }
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
                    Image(
                        painter = painterResource(user.imageRes),
                        contentDescription = user.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .padding(top = 16.dp, end = 16.dp, bottom = 8.dp)
                    )
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) { Text("Cambiar imagen") }
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
                    Text(text = user.email, modifier = Modifier.padding(bottom = 16.dp))

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
                    items(interests) { interes ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Box(Modifier.fillMaxWidth()) {
                                Text(
                                    text = interes, textAlign = TextAlign.Center,
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
                interestList = interests
                name = nameTF
                descripcion = descripcionTF
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
            AddInterestDialog(
                type = "servicio",
                list = interests,
                onDismiss = { showDialogAdd = false }
            )
        }
    }
}

@Composable
fun AddInterestDialog(type: String, list: SnapshotStateList<String>, onDismiss: () -> Unit){
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


fun getName(user: User): String{
    return user.name
}
fun getInterests(user: User): List<String>{
    return user.intereses
}