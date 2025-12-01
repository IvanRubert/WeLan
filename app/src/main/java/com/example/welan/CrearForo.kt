package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun CreateForo(modifier: Modifier, navController: NavHostController){
    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text("Crear Foro",
            fontSize = 45.sp)
        Spacer(Modifier.padding(15.dp))
        Column  (modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp), horizontalAlignment = Alignment.CenterHorizontally){
            Text(
                "Nombre:",
                modifier = Modifier.align(Alignment.Start).padding(bottom = 5.dp)
            )
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                modifier = Modifier.fillMaxWidth(0.8f)
                    .padding(start = 10.dp).height(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(35.dp))
        Row  (modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
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

        Column (modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Descripción:",
                modifier = Modifier.align(Alignment.Start).padding(bottom = 5.dp)
            )
            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                modifier = Modifier.fillMaxWidth(0.8f).height(100.dp)
                    .padding(start = 10.dp)
            )
        }
        Spacer(modifier = Modifier.height(35.dp))
        Column  (modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp), horizontalAlignment = Alignment.CenterHorizontally){
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
        Column (modifier = Modifier.fillMaxWidth(0.8f).padding(start = 10.dp), horizontalAlignment = Alignment.CenterHorizontally){
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
        Button(onClick = {},
                modifier = Modifier.fillMaxWidth(0.25f)
            ) {
            Text("Crear")
        }
    }

}