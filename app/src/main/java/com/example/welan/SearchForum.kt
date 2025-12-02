package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController

data class Foro(val image : Int,val rate : Int,val titulo : String, val categorias : List<Categori>, val mountmessage : Int)
data class Categori(val content : String,val color : Color)
fun getListProvisianlList() : List<Foro>{
    return listOf<Foro>(
        Foro(R.drawable.foroayuntamientocastellon,3,"Recomendación de Restaurantes",listOf(Categori("#Gastronomía",Color(0xFF92F3CA)),Categori("#Ocio",Color(0xFFC09AF8))),50),
        Foro(R.drawable.forotortillapatata,4,"Sítios para tomar el Bermu",listOf(Categori("#Gastronomía",Color(0xFF92F3CA)),Categori("#Ocio",Color(0xFFC09AF8))),150),
        Foro(R.drawable.forologocastellon,5,"Que debría hacer el FC Castellón",listOf(Categori("#Deporte",Color(0xFF9295F3)),Categori("#Orelluts",Color(0xFFD3F89A))),400),
        Foro(R.drawable.fororafalafena,5,"Reformas en el Rafalafena",listOf(Categori("#Reformas",Color(0xFFEEF392)),Categori("#Ayuntamiento",Color(0xFFF3BC6E))),20)
    )
}

@Composable
fun searchForum(navController: NavHostController, modifier: Modifier){
    val textFieldState = remember { TextFieldState() }
    var searchResults by remember { mutableStateOf(emptyList<Foro>()) }
    var filtroComentarios: List<Foro> = if (searchResults.isEmpty() && textFieldState.text.isEmpty()) {
        getListProvisianlList()
    } else {
        filtroForo(textFieldState)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SimpleSearchBar(
                    textFieldState = textFieldState,
                    onSearch = { query ->
                        searchResults = if (query.isNotBlank()) {
                            filtroComentarios
                        } else emptyList()
                    },
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 10.dp)
                        .weight(0.85f)
                )
                Icon(
                    imageVector = Icons.Outlined.FilterAlt,
                    contentDescription = "Filtrado",
                    tint = Black,
                    modifier = Modifier
                        .padding(top = 25.dp, end = 10.dp, bottom = 10.dp)
                        .weight(0.15f)
                        .height(50.dp)
                )
            }
            LazyColumn {
                items(filtroComentarios) { foro ->
                    Card(
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                            Image(
                                painter = painterResource(foro.image),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(100.dp)
                                    .heightIn(max = 120.dp)
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop

                            )
                            Spacer(modifier = Modifier.width(20.dp))
                            Column(modifier = Modifier.padding(start = 10.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    StarRate(foro.rate)
                                    Spacer(modifier = Modifier.weight(0.95f))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Message,
                                        contentDescription = ""
                                    )
                                    Spacer(modifier = Modifier.weight(0.05f))
                                    Text(
                                        "${foro.mountmessage}+",
                                        modifier = Modifier.padding(end = 5.dp)
                                    )

                                }
                                Text(foro.titulo)
                                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                    foro.categorias.forEach { categori ->
                                        TextButton(
                                            onClick = {},
                                            shape = RoundedCornerShape(20.dp),
                                            colors = ButtonDefaults.textButtonColors(containerColor = categori.color)
                                        ) {
                                            Text(categori.content)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        FABSForo(modifier = modifier.align(Alignment.BottomEnd).padding(16.dp),navController)
    }
}
@Composable
fun FABSForo(modifier: Modifier, navController: NavHostController) {

    FloatingActionButton(
        onClick = {
            navController.navigate("CrearForo")
        },
        modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Icon(Icons.Filled.Add, "Crear Foro")
    }
}
@Composable
fun StarRate(rate : Int){
    for (i in 1 .. 5){
        if (i <= rate){
            Icon(imageVector = Icons.Filled.StarRate, contentDescription = "",
                tint = Color.Black)
        }else{
            Icon(imageVector = Icons.Filled.StarBorder, contentDescription = "",
                tint = Color.Black
                )
        }
    }
}
@Composable
fun filtroForo(textFieldState: TextFieldState): List<Foro>{
    return getListProvisianlList().filter { it.titulo.contains(textFieldState.text.toString(), ignoreCase = true) }
}