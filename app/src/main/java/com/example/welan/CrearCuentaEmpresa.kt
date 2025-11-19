package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CrearCuentaEmpresa(navController: NavController, modifier: Modifier = Modifier){
    Image(painterResource(id = R.drawable.logo), contentDescription = null)
    Text("WeLan", style = MaterialTheme.typography.titleLarge)

    Row {
        var selected by remember { mutableStateOf(setOf<Int>()) }

        MultiChoiceSegmentedButtonRow {
            SegmentedButton(
                0 in selected,
                onCheckedChange = {
                    selected =
                        if (0 in selected) selected - 0 else setOf(0)   // Solo uno a la vez
                },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
            ) {
                Text("Usuario")
            }

            SegmentedButton(
                checked =  1 in selected,
                onCheckedChange = {
                    selected =
                        if (1 in selected) selected - 1 else setOf(1)
                },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
            ) {
                Text("Empresa")
            }
        }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF3F3F3F))
    ) {
        var email by remember { mutableStateOf("") }


        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo corporativo") }
        )

        Button(onClick = { /* subir archivo */ }) {
            Icon(Icons.Filled.Add, contentDescription = null)
            Text("Subir certificado")
        }

        ClickableText(
            text = buildAnnotatedString {
                append("¿Tienes cuenta? ")
                pushStringAnnotation("login", "login")
                withStyle(SpanStyle(color = Color(0xFF4BA3FF))) {
                    append("Inicia Sesión")
                }
                pop()
            },
            onClick = { offset -> /* navegar */ }
        )
    }

    Button(
        onClick = { /* acción */ },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Iniciar sesión")
    }

}