package com.example.welan.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.welan.model.Foro
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.viewmodels.ForoUiState
import com.example.welan.ui.viewmodels.ForoViewModel
import kotlinx.coroutines.launch

@Composable
fun SearchForum_Screen(
    foroViewModel: ForoViewModel,
    navController: NavHostController,
    retryAction: () -> Unit,
    modifier: Modifier,
) {
    when (foroViewModel.foroUiState) {
        is ForoUiState.Success -> {
            SearchForum(
                navController,
                modifier,
                (foroViewModel.foroUiState as ForoUiState.Success).data,
                foroViewModel
            )
        }
        is ForoUiState.Error -> {
            ErrorScreen(retryAction)
        }

        is ForoUiState.Loading -> {
            LoadingScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchForum(navController: NavHostController, modifier: Modifier, forochat: List<Foro>, foroViewModel: ForoViewModel){
    var query by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var currentMessage by remember { mutableStateOf<String?>(null) }
    val errorMessage by foroViewModel.errorMessage.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(errorMessage) {
        errorMessage?.let { message ->
            coroutineScope.launch {
                currentMessage = message
                showError = true
            }
        }
    }
    if (showError) {
        Dialog(
            content = { Text(currentMessage!!) },
            onDismissRequest = { showError = false }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier) {
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
                                foroViewModel.getForoFiltro(query)
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
            LazyColumn {
                items(forochat) { foro ->
                    Card(
                        modifier = Modifier.padding(8.dp).fillMaxWidth()
                            .clickable{
                                foroViewModel.setForoSelected(foro)
                                navController.navigate("ForoChat/${foro.titulo}")
                            },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
                            Image(
                                bitmap = base64ToImageBitmap(foro.pathImage)!!,
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
                                    Spacer(modifier = Modifier.weight(0.95f))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.Message,
                                        contentDescription = ""
                                    )
                                    Spacer(modifier = Modifier.weight(0.05f))
                                    Text(
                                        "${foro.total_mensajes}",
                                        modifier = Modifier.padding(end = 5.dp)
                                    )

                                }
                                Text(foro.titulo)
                                if (!foro.intereses.isNullOrEmpty()) {
                                    val filas = foro.intereses.chunked(2)
                                    Column(verticalArrangement = Arrangement.spacedBy(8.dp),
                                        modifier = Modifier.padding(vertical = 8.dp)) {
                                        filas.forEach {pareja ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                                            ) {
                                                pareja.forEach { interes ->
                                                    Box(modifier = Modifier.weight(1f)) {
                                                        TextButton(
                                                            onClick = {},
                                                            shape = RoundedCornerShape(20.dp),
                                                            colors = ButtonDefaults.textButtonColors(
                                                                containerColor = Color(interes.color)
                                                            ),
                                                            modifier = Modifier.fillMaxWidth(0.9f)
                                                        ) {
                                                            Text(
                                                                text = "#${interes.nombre}",
                                                                color = White,
                                                                textAlign = TextAlign.Center
                                                            )
                                                        }
                                                    }
                                                }
                                                if (pareja.size == 1) {
                                                    Spacer(modifier = Modifier.weight(1f))
                                                }
                                            }
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