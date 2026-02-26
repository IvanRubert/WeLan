package com.example.welan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.welan.R
import com.example.welan.ui.componentes.compPassword
import com.example.welan.ui.componentes.esCorreoValido
import com.example.welan.ui.theme.primaryColor
import com.example.welan.ui.viewmodels.UserViewModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.welan.data.UserExist
import com.example.welan.ui.viewmodels.BusinessUserUiState
import com.example.welan.ui.viewmodels.CommonUserUiState
import kotlinx.coroutines.launch

@Composable
fun Login(navController: NavController,modifier: Modifier, userViewModel: UserViewModel) {
    var choose by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    var option by remember { mutableStateOf(true) }
    var emailEmpresa by remember { mutableStateOf("") }
    var errorEmail by remember { mutableStateOf(false) }
    var errorPassword by remember { mutableStateOf(false) }
    var errorEmailEmpresa by remember { mutableStateOf(false) }
    var errorCertificado by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    var currentMessage by remember { mutableStateOf<String?>(null) }
    var existuser by remember { mutableStateOf(false) }
    val errorMessage by userViewModel.errorMessage.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    // Navegar SOLO cuando el estado del VM llegue a Success (login correcto)
    val commonState = userViewModel.commonUserUiState
    val businessState = userViewModel.businessUserUiState
    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(commonState, businessState) {
        if (hasNavigated) return@LaunchedEffect
        when {
            commonState is CommonUserUiState.Success -> {
                hasNavigated = true
                existuser = UserExist(context)
                if (existuser) {
                    navController.navigate("Contenedor_Principal") {
                        popUpTo("Login") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
            businessState is BusinessUserUiState.Success -> {
                hasNavigated = true
                existuser = UserExist(context)
                if (existuser) {
                    navController.navigate("Contenedor_Principal") {
                        popUpTo("Login") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            coroutineScope.launch {
                currentMessage = message
                showError = true
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.welan_logo_text),
            contentDescription = "",
            modifier = Modifier.size(150.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(30.dp))
        MultiChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth(0.7f)) {

            SegmentedButton(
                checked = !choose,
                onCheckedChange = {
                    choose = false
                    option = true
                },
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
                onCheckedChange = {
                    choose = true
                    option = false
                },
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
        Spacer(Modifier.height((50.dp)))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.75F)
                .fillMaxHeight(0.75f),
            shape = RoundedCornerShape(40.dp),
            ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().padding(top = 25.dp),
                verticalArrangement = Arrangement.spacedBy(35.dp)
            ) {
                Text(
                    text = stringResource(R.string.IniciarSesión),
                    fontSize = 20.sp
                )

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

                Column {
                    if (option) {
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier.fillMaxWidth(0.9f),
                            singleLine = true,
                            label = {Text(text = stringResource(id = R.string.fileduse),
                                color = Color.White)},
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary, unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary),

                            )
                        Text(
                            text = if (errorEmail) "Error: El correo no es correcto" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(0.65f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(Modifier.height(30.dp))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text(text = stringResource(id = R.string.paswdField),
                                color = Color.White) },
                            visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                val icon =
                                    if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                                IconButton(onClick = { visible = !visible }) {
                                    Icon(imageVector = icon, contentDescription = null)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(0.9f),
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary, unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary),
                        )
                        Text(
                            text = if (errorPassword) "Error: La contraseña no cumple los parametros necesarios" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(0.65f),
                            textAlign = TextAlign.Start
                        )
                    }else{


                        OutlinedTextField(
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = MaterialTheme.colorScheme.onPrimary, unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary),
                            value = emailEmpresa,
                            onValueChange = { emailEmpresa = it },
                            label = { Text("Correo corporativo",
                                color = Color.White)
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            singleLine = true
                        )
                        Text(
                            text = if (errorEmailEmpresa) "Error: No es un correo electronico" else "",
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
                        Text(
                            text = if (errorCertificado) "Error: No es un certificado valido" else "",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Red,
                            modifier = Modifier.fillMaxWidth(0.65f),
                            textAlign = TextAlign.Start
                        )
                        Spacer(Modifier.height(16.dp))

                        Button(onClick = {  },
                            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)) {
                            Icon(Icons.Default.Add, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Subir certificado")
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(0.9f))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Text(
                        "¿No tienes una cuenta? ",
                        fontSize = 12.sp
                    )
                    Text(
                        "Registrate",
                        modifier = Modifier.clickable { navController.navigate("Register") },
                        color = Color.Blue,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.weight(0.1f))
            }
        }
        Spacer(modifier = Modifier.weight(0.5f))
        Button(
            onClick = {
                if (!choose) {
                    if (esCorreoValido(email)) {
                        if (compPassword(password)) {
                            userViewModel.getCommonUserValidate(email, password, context)
                            navController.navigate("Contenedor_Principal")
                        } else {
                            errorPassword = true
                        }
                    } else {
                        errorEmail = true
                    }
                } else {
                    if (esCorreoValido(emailEmpresa)) {
                        val certificate: String = "certificado def"

                        if (certificate != "") {
                            userViewModel.getBusinessUserValidate(emailEmpresa, certificate, context)
                        }
                        navController.navigate("Contenedor_Principal")
                    } else {
                        errorEmailEmpresa = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text("Iniciar sesión")
        }
        Spacer(modifier = Modifier.weight(0.5f))

    }
}