package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.welan.ui.theme.WeLanTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearCuentaUsuario(navController: NavController, modifier: Modifier = Modifier) {

    var choose by remember { mutableStateOf(false) }
    var usuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

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


            MultiChoiceSegmentedButtonRow(modifier = modifier.fillMaxWidth(0.6f)) {
                SegmentedButton(
                    checked = false,
                    onCheckedChange = {
                        choose = true
                    },
                    shape = RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp),
                    colors = SegmentedButtonColors(
                        activeBorderColor = Color.Unspecified,
                        activeContainerColor = Color.Unspecified,
                        activeContentColor = Color.Unspecified,
                        inactiveContainerColor = if (choose) Color(0xABFC5656) else Color.Unspecified,
                        inactiveContentColor = Color.Unspecified,
                        inactiveBorderColor = Color(0xFF000000),
                        disabledActiveContainerColor = Color.Unspecified,
                        disabledActiveContentColor = Color.Unspecified,
                        disabledActiveBorderColor = Color(0xFF000000),
                        disabledInactiveContainerColor = Color.Unspecified,
                        disabledInactiveContentColor = Color.Unspecified,
                        disabledInactiveBorderColor = Color(0xFF000000)
                    ),
                    modifier = Modifier.height(60.dp)
                ){
                    Text("Usuario")
                }

                SegmentedButton(
                    checked = false,
                    onCheckedChange = {
                        choose = false
                    },
                    shape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
                    colors = SegmentedButtonColors(
                        activeBorderColor = Color.Unspecified,
                        activeContainerColor = Color.Unspecified,
                        activeContentColor =  Color.Unspecified,
                        inactiveContainerColor = if (!choose) Color(0xABFC5656) else Color.Unspecified,
                        inactiveContentColor =Color.Unspecified,
                        inactiveBorderColor = Color(0xFF000000),
                        disabledActiveContainerColor = Color.Unspecified,
                        disabledActiveContentColor = Color.Unspecified,
                        disabledActiveBorderColor = Color(0xFF000000),
                        disabledInactiveContainerColor = Color.Unspecified,
                        disabledInactiveContentColor = Color.Unspecified,
                        disabledInactiveBorderColor = Color(0xFF000000)
                    ),
                    modifier = Modifier.height(60.dp)
                ) {
                    Text("Empresa")
                }
            }

            Spacer(Modifier.height(24.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
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

                    OutlinedTextField(
                        value = usuario,
                        onValueChange = { usuario = it },
                        label = { Text("Usuario:") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))


                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Correo electrónico:") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Contraseña:") },
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
                                    contentDescription = "Toggle Password"
                                )
                            }
                        }
                    )

                    Spacer(Modifier.height(20.dp))

                    ClickableText(
                        text = buildAnnotatedString {
                            append("¿Tienes cuenta? ")
                            pushStringAnnotation("login", "login")
                            withStyle(SpanStyle(color = Color(0xFF4BA3FF))) {
                                append("Inicia Sesión")
                            }
                            pop()
                        },
                        onClick = { /* navigate */ }
                    )
                }
            }

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { /* acción */ },
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
fun CrearUsuarioPreview() {
    val navController = rememberNavController()
    CrearCuentaUsuario(navController)
}