package com.example.welan.ui.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.welan.R

@Composable
fun CreateForo(modifier: Modifier, navController: NavHostController) {
    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }

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
        }
        Spacer(modifier = Modifier.height(35.dp))
        Row(
            modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {}
            ) {
                Text("Subir Imagen")
            }
            Spacer(modifier = Modifier.width(30.dp))
            Image(
                painter = painterResource(R.drawable.welan_logo),
                contentDescription = "",
                modifier = Modifier.size(75.dp)
            )
        }
        Spacer(modifier = Modifier.height(35.dp))

        Column(
            modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Descripción:",
                modifier = Modifier.align(Alignment.Start).padding(bottom = 5.dp)
            )
            OutlinedTextField(
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary
                ),
                value = descripcion,
                onValueChange = { descripcion = it },
                modifier = Modifier.fillMaxWidth(0.8f)
                    .padding(start = 10.dp),
                maxLines = 3,
                minLines = 3
            )
        }
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
                onClick = {},
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
                onClick = {},
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Add#+")
            }
        }
        Spacer(modifier = Modifier.height(35.dp))
    }
    Button(
        onClick = {},
        modifier = Modifier.fillMaxWidth(0.9f).align(Alignment.BottomCenter)
    ) {
        Text("Crear")
    }
}

}