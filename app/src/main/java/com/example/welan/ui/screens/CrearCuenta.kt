package com.example.welan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.welan.R
import com.example.welan.model.CreateBusinessUserDTO
import com.example.welan.model.CreateCommonUserDTO
import com.example.welan.ui.componentes.compPassword
import com.example.welan.ui.componentes.esCorreoValido
import com.example.welan.ui.theme.WeLanTheme
import com.example.welan.ui.theme.primaryColor
import com.example.welan.ui.viewmodels.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun CrearCuentaScreen(navController: NavController, userViewModal: UserViewModel) {

    var choose by remember { mutableStateOf(false) }
    var nameEmpresa by remember {mutableStateOf("")}
    var nombre by remember { mutableStateOf("") }
    var emailUsuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailEmpresa by remember { mutableStateOf("") }
    var certificado by remember { mutableStateOf("") }
    var errorEmail by remember { mutableStateOf(false) }
    var errorPassword by remember { mutableStateOf(false) }
    var errorNombre by remember { mutableStateOf(false) }
    var errorNameEmpresa by remember { mutableStateOf(false) }
    var errorEmailEmpresa by remember { mutableStateOf(false) }
    var errorCertificado by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var currentMessage by remember { mutableStateOf<String?>(null) }
    val errorMessage by userViewModal.errorMessage.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            coroutineScope.launch {
                currentMessage = message
                showError = true
            }
        }
    }

    WeLanTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = R.drawable.welan_logo_text),
                contentDescription = null,
                modifier = Modifier.size(120.dp)
            )

            Spacer(Modifier.height(24.dp))

            MultiChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth(0.7f)) {

                SegmentedButton(
                    checked = !choose,
                    onCheckedChange = { choose = false },
                    shape = SegmentedButtonDefaults.itemShape(0, 2),
                    colors = SegmentedButtonColors(
                        activeBorderColor = Color.Black,
                        activeContainerColor = Color(0xABFC5656),
                        activeContentColor = Color.White,

                        inactiveContainerColor = Color.Transparent,
                        inactiveContentColor = Color.White,
                        inactiveBorderColor = Color.Black,

                        disabledActiveContainerColor = Color.Transparent,
                        disabledActiveContentColor = Color.White,
                        disabledActiveBorderColor = Color.Black,
                        disabledInactiveContainerColor = Color.Transparent,
                        disabledInactiveContentColor = Color.White,
                        disabledInactiveBorderColor = Color.Black
                    ),
                    modifier = Modifier.height(50.dp),
                    icon = {}
                ) {
                    Text("Usuario")
                }

                SegmentedButton(
                    checked = choose,
                    onCheckedChange = { choose = true },
                    shape = SegmentedButtonDefaults.itemShape(1, 2),
                    colors = SegmentedButtonColors(
                        activeBorderColor = Color.Black,
                        activeContainerColor = Color(0xABFC5656),
                        activeContentColor = Color.White,

                        inactiveContainerColor = Color.Transparent,
                        inactiveContentColor = Color.White,
                        inactiveBorderColor = Color.Black,

                        disabledActiveContainerColor = Color.Transparent,
                        disabledActiveContentColor = Color.White,
                        disabledActiveBorderColor = Color.Black,
                        disabledInactiveContainerColor = Color.Transparent,
                        disabledInactiveContentColor = Color.White,
                        disabledInactiveBorderColor = Color.Black
                    ),
                    modifier = Modifier.height(50.dp),
                    icon = {}
                ) {
                    Text("Empresa")
                }
            }

            Spacer(Modifier.height(30.dp))


            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {

                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.Start
                ) {

                    Text(
                        "Crear cuenta",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Spacer(Modifier.height(16.dp))

                    if (showError) {
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = primaryColor
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = currentMessage ?: "Error",
                                color = Color.White,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }

                    if (!choose) {

                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary, unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary),
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text(
                                text ="Nombre:",
                                color = Color.White)
                                },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                        )

                        Text(
                            text = if (errorNombre) "Error: El nombre no debe de estar vacio" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(0.65f),
                            textAlign = TextAlign.Start
                        )

                        Spacer(Modifier.height(16.dp))

                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary, unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary),
                            value = emailUsuario,
                            onValueChange = { emailUsuario = it },
                            label = { Text("Correo electrónico:",
                                color = Color.White)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                        Text(
                            text = if (errorEmail) "Error: No es un correo" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(0.65f),
                            textAlign = TextAlign.Start
                        )

                        Spacer(Modifier.height(16.dp))

                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary, unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary),
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Contraseña:",
                                color = Color.White)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            visualTransformation =
                                if (passwordVisible) VisualTransformation.None
                                else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Icon(
                                        imageVector = if (passwordVisible)
                                            Icons.Default.Visibility
                                        else
                                            Icons.Default.VisibilityOff,
                                        contentDescription = null
                                    )
                                }
                            },
                            singleLine = true,
                        )

                        Text(
                            text = if (errorPassword) "Error: La contraseña no cumple los parametros necesarios" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(0.65f),
                            textAlign = TextAlign.Start
                        )
                    }

                    if (choose) {
                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary, unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary),
                            value = nameEmpresa,
                            onValueChange = { nameEmpresa = it },
                            label = { Text("Nombre",
                                color = Color.White)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            singleLine = true
                        )
                        Text(
                            text = if (errorNameEmpresa) "Error: El nombre no debe de estar vacio" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(0.65f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(Modifier.height(16.dp))
                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary, unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary),
                            value = emailEmpresa,
                            onValueChange = { emailEmpresa = it },
                            label = { Text("Correo corporativo",
                                color = Color.White)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                        Text(
                            text = if (errorEmailEmpresa) "Error: No es un correo" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(0.65f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Subir certificado",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        )

                        Spacer(Modifier.height(16.dp))

                        Button(onClick = {  }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Subir certificado")
                        }
                        Text(
                            text = if (errorCertificado) "Error: El certificado no es valido" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(0.65f),
                            textAlign = TextAlign.Start
                        )
                    }

                    Spacer(Modifier.height(20.dp))

                    Row (verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "¿Tienes cuenta?",
                            modifier = Modifier.padding(end = 10.dp)
                        )
                        Text(
                            text = "Inicia Sesión",
                            color = Color.Blue,
                            textDecoration = TextDecoration.Underline,
                            modifier = Modifier.clickable {
                                navController.navigate("Login") {
                                    popUpTo("Register") {
                                        inclusive = true
                                    }
                                }
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = {
                    if (!choose) {
                        if (nombre == "") {
                            errorNombre = true
                        }
                        if (!esCorreoValido(emailUsuario)) {
                            errorEmail = true
                        }
                        if (!compPassword(password)) {
                            errorPassword = true
                        }
                        if (!errorNombre && !errorEmail && !errorPassword) {
                            val usercommon = CreateCommonUserDTO(
                                nombre = nombre,
                                correo = emailUsuario,
                                passHash = password
                            )
                            userViewModal.postCommonUser(usercommon)
                        }
                    } else {
                        if (nameEmpresa == "") {
                            errorNameEmpresa = true
                        }
                        if (!esCorreoValido(emailEmpresa)) {
                            errorEmailEmpresa = true
                        }
//                        if (certificado == "") {
//                            errorCertificado = true
//                        }
                        if (!errorNameEmpresa && !errorEmailEmpresa && !errorCertificado) {
                            val userbusiness = CreateBusinessUserDTO(
                                nombre = nombre,
                                correo = emailUsuario,
                                certificado = "certificado def"
                            )
                            userViewModal.postBusinessUser(userbusiness)
                        }
                    }
                    navController.navigate("Contenedor_Principal") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
            ) {
                Text("Crear Cuenta")
            }
        }
    }
}
