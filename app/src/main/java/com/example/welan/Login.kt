package com.example.welan

import android.net.Uri
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
import com.example.welan.data.User

@Composable
fun Login(navController: NavController,modifier: Modifier) {
    var choose by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }
    var option by remember { mutableStateOf(true) }
    var emailEmpresa by remember { mutableStateOf("") }



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
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Subir certificado",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
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
                if (email == "") {
                    email = "emaildefault@default.def"
                }
                val usuario: User
                if (!choose) {
                    usuario = User(
                        imageRes = R.drawable.welan_logo,
                        type = "User",
                        name = "NameDefault",
                        email = email,
                        descripcion = null,
                        intereses = emptyList()
                    )
                } else {
                    usuario = User(
                        imageRes = R.drawable.welan_logo,
                        type = "Business",
                        name = "NameDefault",
                        email = emailEmpresa,
                        descripcion = "",
                        intereses = emptyList()
                    )
                }
                val nameEncoded = Uri.encode(usuario.name)
                val emailEncoded = Uri.encode(usuario.email)
                val typeEncoded = Uri.encode(usuario.type)
                val descripcionForNav = when (usuario.type) {
                    "User" -> "_NULL_"
                    else -> {
                        if (usuario.descripcion.isNullOrEmpty()) "_EMPTY_"
                        else usuario.descripcion
                    }
                }
                val descripcionEncoded = Uri.encode(descripcionForNav)
                val interesesString = usuario.intereses.joinToString("→←")
                val interesesEncoded = Uri.encode(interesesString)
                navController.navigate("Contenedor_Principal" +
                        "/${usuario.imageRes}" +
                        "/$typeEncoded" +
                        "/$nameEncoded" +
                        "/$emailEncoded" +
                        "/$descripcionEncoded" +
                        "/$interesesEncoded"
                ) {
                    popUpTo(0) {
                        inclusive = true
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