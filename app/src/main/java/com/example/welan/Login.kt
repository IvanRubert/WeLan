package com.example.welan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonColors
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Login(navController: NavController,modifier: Modifier){
    var choose by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var visible by remember { mutableStateOf(false) }


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
        Spacer(Modifier.height((50.dp)))
        Card(
            modifier = Modifier
                .fillMaxWidth(0.75F)
                .fillMaxHeight(0.75f),
            shape = RoundedCornerShape(40.dp),

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize().padding(top = 25.dp), verticalArrangement = Arrangement.spacedBy(35.dp)) {
                Text(
                    text = stringResource(R.string.IniciarSesión),
                    fontSize = 20.sp
                )
                Column {
                    Text(
                        text = stringResource(id = R.string.fileduse)
                    )
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        modifier = Modifier.border(1.dp, Color.White).fillMaxWidth(0.9f)
                    )
                    Spacer(Modifier.height(30.dp))
                    Text(
                        text = stringResource(id = R.string.paswdField)
                    )
                    TextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña") },
                        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            val icon =
                                if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                            IconButton(onClick = { visible = !visible }) {
                                Icon(imageVector = icon, contentDescription = null)
                            }
                        },
                        modifier = Modifier.border(1.dp, Color.White)
                    )
                }
                Spacer(modifier = Modifier.weight(0.9f))
                    Row(horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                        ) {
                        Text("¿No tienes una cuenta? ",
                            fontSize = 12.sp)
                        Text("Registrate",
                            modifier = Modifier.clickable{navController.navigate("Register")},
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
                navController.navigate("Contenedor_Principal") {
                    popUpTo("Login"){
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