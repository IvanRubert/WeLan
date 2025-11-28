package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.welan.data.ServOProd
import com.example.welan.data.ServicioOProductos
import com.example.welan.data.User
import com.example.welan.data.getServicioProductos


@Composable
fun ItemServicioProducto(servicioproducto: ServicioOProductos, navController: NavHostController) {

    Card ( modifier = Modifier.padding(20.dp).clickable { navController.navigate("ServicioProducto") } ) {
        Image(
            painter = painterResource(id = servicioproducto.Photo),
            contentDescription = "Contactos",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = servicioproducto.Titulo),
            fontSize = 25.sp,
            maxLines = 3,
            lineHeight = 32.sp,
            modifier = Modifier.padding(15.dp),
            color = White
        )
        Row (verticalAlignment = Alignment.CenterVertically ) {
            if (servicioproducto.Servoprod == ServOProd.PRODUCTO) {
                if (servicioproducto.Envio == false) {
                    Text(
                        text = "Posibilidad de envio",
                        fontSize = 13.sp,
                        maxLines = 2,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(15.dp),
                        color = White
                    )
                } else if (servicioproducto.Envio == true) {
                    Text(
                        text = "Envio obligatorio",
                        fontSize = 13.sp,
                        maxLines = 2,
                        lineHeight = 22.sp,
                        modifier = Modifier.padding(15.dp),
                        color = White
                    )
                }
            }
            Text(
                text = "${servicioproducto.Precio}€/${servicioproducto.Unidad}",
                fontSize = 25.sp,
                maxLines = 3,
                lineHeight = 32.sp,
                modifier = Modifier.padding(15.dp),
                color = White
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarServicioProducto(modifier: Modifier, navController: NavHostController, user: User) {
    val textFieldState = remember { TextFieldState() }
    var searchResults by remember { mutableStateOf(emptyList<ServicioOProductos>()) }

    val filtroComentarios = if (searchResults.isEmpty() && textFieldState.text.isEmpty()) {
        getServicioProductos()
    } else {
        filtro(textFieldState)
    }



    Box (modifier = modifier.fillMaxWidth()) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                SimpleSearchBar(
                    textFieldState = textFieldState,
                    onSearch = { query ->
                        searchResults = if (query.isNotBlank()) {
                            filtro(textFieldState)
                        } else emptyList()
                    },
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth().weight(0.17f)
                )
                Icon(
                    imageVector = Icons.Filled.FilterAlt,
                    contentDescription = "Filtrado",
                    tint = Black
                )
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(filtroComentarios) { elemento ->
                    ItemServicioProducto(elemento, navController)
                }
            }
        }
        if (user.type == "User") {
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
    onSearch: @Composable (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
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