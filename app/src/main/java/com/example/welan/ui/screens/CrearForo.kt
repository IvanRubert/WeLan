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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.welan.R
import com.example.welan.model.ForoDTO
import com.example.welan.model.Interes
import com.example.welan.model.UserWelan
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.componentes.imageUriToBase64
import com.example.welan.ui.viewmodels.ForoViewModel
import com.example.welan.ui.viewmodels.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun CreateForo(modifier: Modifier, navController: NavHostController, foroViewModel: ForoViewModel, usuario: UserWelan, userViewModel: UserViewModel) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var image :String? by remember { mutableStateOf(null) }
    val admins = rememberSaveable { mutableStateListOf<String>().apply{addAll(listOf(usuario.correo,))}}
    val intereses = rememberSaveable { mutableStateListOf<Interes>().apply{addAll(emptyList())}}
    val interes = rememberSaveable { mutableStateListOf<String>().apply{addAll(emptyList())}}
    var errortitulo by remember { mutableStateOf(false) }
    var errorimage by remember { mutableStateOf(false) }
    var showDialogAdd by remember { mutableStateOf(false) }
    var showDialogPut by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val base64 = imageUriToBase64(context, uri)
            image = base64
        }
    }

    Box(modifier = modifier.fillMaxSize()){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Titulo:",
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 5.dp)
                )
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                        .padding(start = 10.dp)

                )
                Text(
                    text = if (errortitulo) "Error: El titulo es Obligatorio" else "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth(0.65f),
                    textAlign = TextAlign.Start
                )
            }
            Spacer(modifier = Modifier.height(35.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { launcher.launch("image/*") }
                ) {
                    Text("Subir Imagen")
                }
                Spacer(modifier = Modifier.width(30.dp))
                if (image != null) {
                    Image(
                        bitmap = base64ToImageBitmap(image!!)!!,
                        contentDescription = "Foro",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.height(100.dp).widthIn(max = 100.dp).padding(10.dp)
                    )
                } else {
                    Image(
                        painterResource(R.drawable.ic_forodef),
                        contentDescription = "Foro",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier.height(100.dp).widthIn(max = 100.dp).padding(10.dp)
                    )
                }
            }
            Text(
                text = if (errorimage) "Error: La imagen es Obligatorio" else "",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Red,
                modifier = Modifier.fillMaxWidth(0.65f),
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Administradores:",
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 5.dp)
                )
                Button(
                    onClick = {showDialogPut = true},
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Buscar User")
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Categoria:",
                    modifier = Modifier.align(Alignment.Start).padding(bottom = 5.dp)
                )
                Button(
                    onClick = { showDialogAdd = true },
                    modifier = Modifier.fillMaxWidth(0.8f)
                ) {
                    Text("Add#+")
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
        }
        Button(
            onClick = {
                errortitulo = titulo == ""
                errorimage = image == null
                if (!errortitulo && !errorimage) {
                    intereses.forEach { inter ->
                        interes.add(inter.nombre)
                    }
                    val foro = ForoDTO(titulo,  interes,admins,image!!)
                    foroViewModel.postForo(foro)
                    navController.popBackStack()
                }
            },
            modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.BottomCenter)
        ) {
            Text("Crear")
        }

        if (showDialogPut) {
            AdminsForoDialog(
                onDismiss = { showDialogPut = false },
                list = admins,
                userViewModel = userViewModel
            )
        }

        if (showDialogAdd){
            AddInterestDialogInteres(
                type = "interés",
                list = intereses,
                onDismiss = { showDialogAdd = false }
            )
        }
    }
}

@Composable
fun AdminsForoDialog(onDismiss: () -> Unit, list: SnapshotStateList<String>, userViewModel: UserViewModel) {
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
                        } else {
                            val existe = userViewModel.validarCorreo(correo)
                            if (!existe) {
                                isError = true
                                errorText = "Error: El correo no existe"
                            } else {
                                if (!list.contains(correo)){
                                    isError = false
                                    list.add(correo)
                                    onDismiss.invoke()
                                    errorText = null
                                }else {
                                    isError = true
                                    errorText = "Correo ya esta insertado"
                                }
                            }
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
