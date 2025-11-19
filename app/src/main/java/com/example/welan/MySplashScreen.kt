package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    LaunchedEffect(true) {
        delay(2000)
        navController.navigate("Login") {
            popUpTo("SplashScreen"){
                inclusive = true
            }
        }
    }
}