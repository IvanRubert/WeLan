package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MultiChoiceSegmentedButtonRow
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
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
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearCuentaEmpresa(navController: NavController, modifier: Modifier = Modifier) {

    var selected by remember { mutableStateOf(setOf(1)) }   // Empresa por defecto
    var email by remember { mutableStateOf("") }

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

        MultiChoiceSegmentedButtonRow {
            SegmentedButton(
                checked = 0 in selected,
                onCheckedChange = {
                    selected = setOf(0)
                },
                shape = SegmentedButtonDefaults.itemShape(0, 2)
            ) {
                Text("Usuario")
            }

            SegmentedButton(
                checked = 1 in selected,
                onCheckedChange = {
                    selected = setOf(1)
                },
                shape = SegmentedButtonDefaults.itemShape(1, 2)
            ) {
                Text("Empresa")
            }
        }

        Spacer(Modifier.height(24.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF3F3F3F)),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo corporativo") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                Button(onClick = { /* subir archivo */ }) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Subir certificado")
                }

                Spacer(Modifier.height(16.dp))

                ClickableText(
                    text = buildAnnotatedString {
                        append("¿Tienes cuenta? ")
                        pushStringAnnotation("login", "login")
                        withStyle(SpanStyle(color = Color(0xFF4BA3FF))) {
                            append("Inicia Sesión")
                        }
                        pop()
                    },
                    onClick = { /* nav */ }
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { /* acción */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Iniciar sesión")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CrearEmpresa(){
    val navController = rememberNavController()
    CrearCuentaEmpresa(navController, modifier = Modifier)
}