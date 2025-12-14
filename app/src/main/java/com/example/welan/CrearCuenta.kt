package com.example.welan



import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.welan.data.User
import com.example.welan.ui.theme.WeLanTheme

@Composable
fun CrearCuentaScreen(navController: NavController, modifier: Modifier = Modifier) {

    var choose by remember { mutableStateOf(false) }

    var nombre by remember { mutableStateOf("") }
    var emailUsuario by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var emailEmpresa by remember { mutableStateOf("") }

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
                    }


                    if (choose) {

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
                    if (nombre.isEmpty()) {
                        nombre = "NameDefault"
                    }
                    val email: String
                    val usuario: User

                    if (!choose) {
                        email = emailEmpresa.ifEmpty { "emaildefault@default.def2" }
                        usuario = User(
                            imageRes = R.drawable.welan_logo,
                            type = "User",
                            name = nombre,
                            email = email,
                            descripcion = null,
                            intereses = emptyList()
                        )
                    } else {
                        email = emailEmpresa.ifEmpty { "emaildefault@default.def2" }
                        usuario = User(
                            imageRes = R.drawable.welan_logo,
                            type = "Business",
                            name = nombre,
                            email = email,
                            descripcion = null,
                            intereses = emptyList()
                        )
                    }
                    val nameEncoded = Uri.encode(usuario.name)
                    val emailEncoded = Uri.encode(usuario.email)
                    val typeEncoded = Uri.encode(usuario.type)

                    val descripcionForNav = when (usuario.type) {
                        "User" -> "_NULL_"
                        else -> "_EMPTY_"
                    }
                    val descripcionEncoded = Uri.encode(descripcionForNav)
                    val interesesEncoded = Uri.encode("")

                    navController.navigate(
                        "Contenedor_Principal" +
                                "/${usuario.imageRes}" +
                                "/$typeEncoded" +
                                "/$nameEncoded" +
                                "/$emailEncoded" +
                                "/$descripcionEncoded" +
                                "/$interesesEncoded"
                    ) {
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

@Preview(showBackground = true)
@Composable
fun PreviewCrearCuenta() {
    CrearCuentaScreen(rememberNavController())
}
