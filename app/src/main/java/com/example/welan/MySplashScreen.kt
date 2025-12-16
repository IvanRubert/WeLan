package com.example.welan

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun MySplashScreen(navController: NavController, modifier: Modifier = Modifier){
    Box(modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.welan_logo_text),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .size(200.dp)
        )
    }
    val context = LocalContext.current

    if (UserExist(context)) {
        val usuario = remember { UsuarioJson(context) }

        LaunchedEffect(Unit) {
            delay(1000)

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
            val interesesEncoded = Uri.encode(usuario.intereses.joinToString("→←"))

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
        }
    } else {
        LaunchedEffect(Unit) {
            delay(1000)
            navController.navigate("Login") {
                popUpTo("SplashScreen") {
                    inclusive = true
                }
            }
        }
    }
}