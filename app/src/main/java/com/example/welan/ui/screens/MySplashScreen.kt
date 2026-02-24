package com.example.welan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.welan.R

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
    navController.navigate("Contenedor_Principal") {
        popUpTo(0) { inclusive = true }
    }
}