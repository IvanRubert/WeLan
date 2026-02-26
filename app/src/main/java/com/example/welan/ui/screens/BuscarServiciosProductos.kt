package com.example.welan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.welan.R
import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.Envio
import com.example.welan.model.Producto
import com.example.welan.model.SerOProd
import com.example.welan.model.Servicio
import com.example.welan.model.Unidad
import com.example.welan.model.UserWelan
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.viewmodels.SerOProdUiState
import com.example.welan.ui.viewmodels.SerOProdViewModel


@Composable
fun SevicioProducto_Screen(
    navControllerPrincipal: NavHostController,
    serOProdViewModel: SerOProdViewModel,
    retryAction: () -> Unit,
    modifier: Modifier,
    user: UserWelan
) {
    when (serOProdViewModel.serOProdUiState) {
        is SerOProdUiState.Success -> BuscarServicioProducto(
            (serOProdViewModel.serOProdUiState as SerOProdUiState.Success).data,
            serOProdViewModel,
            modifier,
            navControllerPrincipal,
            user
        )
        is SerOProdUiState.Error -> ErrorScreen(retryAction)
        is SerOProdUiState.Loading -> LoadingScreen()
    }
}

@Composable
fun ItemServicioProducto(servicioproducto: SerOProd, servoprodViewModal: SerOProdViewModel, navController: NavHostController) {

    Card ( modifier = Modifier.padding(5.dp)
        .height(210.dp)
        .clickable {
            servoprodViewModal.setSerOProdSelected(servicioproducto)
            navController.navigate("ServicioProducto")
        }
    ) {
        Box (modifier = Modifier.fillMaxWidth().fillMaxSize()) {
            Column {
                if (servicioproducto.pathImage != null) {
                    Image(
                        bitmap = base64ToImageBitmap(servicioproducto.pathImage!!)!!,
                        contentDescription = "ServicioOProducto",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth().height(110.dp)
                    )
                } else {
                    val imagendef = if (servicioproducto is Producto) painterResource(R.drawable.ic_producto) else painterResource(R.drawable.ic_servicio)
                    Image(
                        painter = imagendef,
                        contentDescription = "ServicioOProducto",
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth().height(110.dp)
                    )
                }
                Text(
                    text = servicioproducto.nombre,
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
                if (servicioproducto is Producto) {
                    if (servicioproducto.envio == Envio.POSSIBILIDADENVIO) {
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
                    } else if (servicioproducto.envio == Envio.OBLIGACIONENVIO) {
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
                    text = if (servicioproducto is Servicio) {
                        if (servicioproducto.tipo_precio == Unidad.UNIDADES) {
                            "${servicioproducto.precio}€/Ser"
                        } else {
                            "${servicioproducto.precio}€/${servicioproducto.tipo_precio.unidad}"
                        }
                    } else {
                        "${servicioproducto.precio}€/${servicioproducto.tipo_precio.unidad}"
                    },
                    fontSize = 16.sp,
                    maxLines = 1,
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
fun BuscarServicioProducto(servoprod: List<SerOProd>, servoprodViewModal: SerOProdViewModel, modifier: Modifier, navController: NavHostController, user: UserWelan) {
    var query by remember { mutableStateOf("") }

    Box (modifier = modifier.fillMaxSize()) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SearchBar(
                    modifier = Modifier
                        .weight(8f)
                        .padding(start = 10.dp)
                        .offset(y = (-15).dp),
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = query,
                            onSearch = {
                                servoprodViewModal.getSerOProdFiltro(query)
                            },
                            onQueryChange = {query = it},
                            expanded = false,
                            onExpandedChange = {},
                            placeholder = { Text("Buscar por Nombre") }
                        )
                    },
                    expanded = false,
                    onExpandedChange = {}
                ) {}
                Icon(
                    imageVector = Icons.Outlined.FilterAlt,
                    contentDescription = "Filtrado",
                    tint = Black,
                    modifier = Modifier
                        .padding(top = 25.dp, end = 10.dp, bottom = 10.dp)
                        .weight(2f)
                        .height(50.dp)
                        .offset(y = (-7).dp)
                )
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.padding(5.dp)
            ) {
                items(servoprod) { elemento ->
                    ItemServicioProducto(elemento, servoprodViewModal, navController)
                }
            }
        }
        if (user is BusinessUserWeLan) {
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
