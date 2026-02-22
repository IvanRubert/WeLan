package com.example.welan.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.welan.R
import com.example.welan.ui.componentes.UserExist
import com.example.welan.ui.componentes.UsuarioJson
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
        LaunchedEffect(Unit) {
            delay(1000)
            navController.navigate(
                "Contenedor_Principal"
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