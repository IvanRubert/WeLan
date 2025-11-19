package com.example.welan

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.welan.ui.theme.primaryColor

@Composable
fun MenuPrincipal(navController: NavController, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            MenuCard(
                navController,
                destino = "",
                imagen = R.drawable.ic_servicios,
                texto = "Servicios"
            )
            MenuCard(
                navController,
                destino = "",
                imagen = R.drawable.ic_noticias,
                texto = "Noticias"
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            MenuCard(
                navController,
                destino = "",
                imagen = R.drawable.ic_foro,
                texto = "Foros"
            )
            MenuCard(
                navController,
                destino = "",
                imagen = R.drawable.ic_add,
                texto = "Foros favoritos"
            )
        }
    }
}

@Composable
fun MenuCard(
    navController: NavController,
    destino: String,
    @DrawableRes imagen: Int,
    texto: String
) {
    Card(
        modifier = Modifier
            .padding(20.dp)
            .size(width = 160.dp, height = 200.dp),
        onClick = { navController.navigate(destino) },
        colors = CardDefaults.cardColors(containerColor = primaryColor)
    ) {
        Image(
            painter = painterResource(id = imagen),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(100.dp).align(Alignment.CenterHorizontally),
        )
        Text(
            text = texto,
            modifier = Modifier.padding(8.dp).align(Alignment.CenterHorizontally),
            color = White,
            fontSize = 19.sp
        )
    }
}
