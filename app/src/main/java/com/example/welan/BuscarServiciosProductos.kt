package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.welan.data.ServOProd
import com.example.welan.data.ServicioOProductos
import com.example.welan.data.User
import com.example.welan.data.getServicioProductos


@Composable
fun ItemServicioProducto(servicioproducto: ServicioOProductos, navController: NavHostController) {

    Card ( modifier = Modifier.padding(5.dp)
        .height(210.dp)
        .clickable {
            navController.navigate("ServicioProducto" +
                    "/${servicioproducto.Servoprod}" +
                    "/${servicioproducto.Titulo}" +
                    "/${servicioproducto.Photo}" +
                    "/${servicioproducto.Descripcion}" +
                    "/${servicioproducto.Envio}" +
                    "/${servicioproducto.Precio.toFloat()}" +
                    "/${servicioproducto.Unidad.unidad}"
            )
        }
    ) {
        Box (modifier = Modifier.fillMaxWidth().fillMaxSize()) {
            Column {
                Image(
                    painter = painterResource(id = servicioproducto.Photo),
                    contentDescription = "Contactos",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth().height(110.dp)
                )
                Text(
                    text = stringResource(id = servicioproducto.Titulo),
                    fontSize = 13.sp,
                    maxLines = 2,
                    lineHeight = 15.sp,
                    modifier = Modifier.padding(5.dp).fillMaxWidth(),
                    color = White,
                    textAlign = TextAlign.Center
                )

            }
            Row (verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.BottomEnd)
                    .padding(bottom = 20.dp)
            ) {
                if (servicioproducto.Servoprod == ServOProd.PRODUCTO) {
                    if (servicioproducto.Envio == false) {
                        Text(
                            text = "Posibilidad de envio",
                            fontSize = 10.sp,
                            maxLines = 2,
                            lineHeight = 13.sp,
                            modifier = Modifier.fillMaxWidth()
                                .padding(5.dp)
                                .weight(0.5f),
                            color = White,
                            textAlign = TextAlign.Center
                        )
                    } else if (servicioproducto.Envio == true) {
                        Text(
                            text = "Envio obligatorio",
                            fontSize = 10.sp,
                            maxLines = 2,
                            lineHeight = 13.sp,
                            modifier = Modifier.fillMaxWidth()
                                .padding(start = 13.dp, top = 5.dp, bottom = 5.dp)
                                .weight(0.5f),
                            color = White,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                } else {
                    Spacer(modifier = Modifier.weight(0.5f))
                }
                Text(
                    text = "${servicioproducto.Precio}€/${servicioproducto.Unidad.unidad}",
                    fontSize = 18.sp,
                    maxLines = 3,
                    lineHeight = 32.sp,
                    modifier = Modifier.padding(5.dp).weight(0.5f),
                    color = White,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarServicioProducto(modifier: Modifier, navController: NavHostController, user: User) {
    val textFieldState = remember { TextFieldState() }
    var searchResults by remember { mutableStateOf(emptyList<ServicioOProductos>()) }

    var filtroComentarios: List<ServicioOProductos> = if (searchResults.isEmpty() && textFieldState.text.isEmpty()) {
        getServicioProductos()
    } else {
       filtro(textFieldState)
    }



    Box (modifier = modifier.fillMaxWidth().fillMaxSize()) {
        Column {
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(5.dp)
            ) {
                items(filtroComentarios) { elemento ->
                    ItemServicioProducto(elemento, navController)
                }
            }
        }
        if (user.type != "User") {
            FABS(modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp), navController)
        }
    }
}

@Composable
fun FABS(modifier: Modifier, navController: NavHostController) {

    FloatingActionButton(
        onClick = {
            navController.navigate("CrearServicioProducto")
        },
        modifier,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        Icon(Icons.Filled.Add, "Crear servicio o producto")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleSearchBar(
    textFieldState: TextFieldState,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier.semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = 0f },
            inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = { textFieldState.edit { replace(0, length, it) } },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                    },
                    expanded = false,
                    onExpandedChange = { false },
                    placeholder = { Text("Search") }
                )
            },
            expanded = false,
            onExpandedChange = { },
        ) {
        }
    }
}

@Composable
fun filtro(textFieldState: TextFieldState): List<ServicioOProductos>{
    return getServicioProductos().filter { stringResource(it.Titulo).contains(textFieldState.text.toString(), ignoreCase = true) }
}