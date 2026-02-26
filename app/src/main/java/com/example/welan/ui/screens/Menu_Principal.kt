package com.example.welan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.welan.model.NoticiDTO
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.viewmodels.NoticiasUiState
import com.example.welan.ui.viewmodels.NoticiasViewModel

@Composable
fun MenuPrincipalScreen(
    noticiasViewModel: NoticiasViewModel,
    retryAction: () -> Unit,
    modifier: Modifier
){
    when(noticiasViewModel.noticiaUiState){
        is NoticiasUiState.Success -> {MenuPrincipal(modifier, (noticiasViewModel.noticiaUiState as NoticiasUiState.Success).data) }
        is NoticiasUiState.Error -> {
            ErrorScreen(retryAction)
        }
        is NoticiasUiState.Loading -> {LoadingScreen()}
    }
}
@Composable
fun ItemNoticia(noticia: NoticiDTO) {
    var visibility by remember { mutableStateOf(true) }

    Card (colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.padding(20.dp)
                        .clickable { visibility = !visibility },
    ){
        Box (
            modifier = Modifier.fillMaxWidth()
        ){
            Image(
                bitmap = base64ToImageBitmap(noticia.photo)!!,
                contentDescription = "Contactos",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (visibility) {
                Box(
                    Modifier.fillMaxWidth().align(Alignment.TopEnd).background(Color(0x55000000))
                ) {
                    Text(
                        text = noticia.titulo,
                        fontSize = 25.sp,
                        maxLines = 3,
                        lineHeight = 32.sp,
                        modifier = Modifier.padding(15.dp),
                        color = White
                    )
                }
            }
        }
        if (!visibility) {
            Text(
                text = noticia.titulo,
                fontSize = 25.sp,
                modifier = Modifier.padding(15.dp),
                maxLines = 3,
                lineHeight = 32.sp,
                )

            Text(
                text = noticia.descripcion,
                fontSize = 15.sp,
                modifier = Modifier.padding(15.dp),
            )
        }
    }
}

@Composable
fun MenuPrincipal(modifier: Modifier, noticias :  List<NoticiDTO>) {
    LazyColumn (
        modifier = modifier.fillMaxWidth()
    ) {
        items(items = noticias) { noticia ->
            ItemNoticia(noticia)
        }
    }
}