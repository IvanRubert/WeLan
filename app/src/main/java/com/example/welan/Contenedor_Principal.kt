package com.example.welan

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.welan.data.Envio
import com.example.welan.data.ServOProd
import com.example.welan.data.ServicioOProductos
import com.example.welan.data.Unidad
import com.example.welan.data.User
import com.example.welan.model.MessagesViewModel
import com.example.welan.ui.theme.appbootomBarGradient
import com.example.welan.ui.theme.apptopBarGradient
import com.example.welan.ui.theme.secondaryColor

@Composable
fun Contenedor_Principal() {

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var topBarTitle by remember { mutableStateOf("") }
    var topBarImg by remember { mutableIntStateOf(R.drawable.welan_logo) }

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = { MyTopAppBar(currentRoute, navController, topBarImg, topBarTitle) },
        bottomBar = { MyNaviagationBar(navController) }
    ) { innerPadding ->
        val vm: MessagesViewModel = viewModel()
        var showDialogChanges by remember { mutableStateOf(false) }

        val user = User(
            imageRes = R.drawable.welan_logo,
            name = "Pepe Gil",
            email = "pepe123@example.com",
            descripcion = "Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción",
            intereses = listOf("Construcción", "Reformas", "Bricolaje"),
            type = "Bissness"
        )

        NavHost(navController = navController, startDestination = "MenuPrincipal"){
            composable("MenuPrincipal") {
                MenuPrincipal(Modifier.padding(innerPadding))
            }
            composable("Perfil") {
                if (user.type == "User") {
                    PerfilUser(user, navController, Modifier.padding(innerPadding))
                } else PerfilEmpresa(
                    user,
                    navController,
                    Modifier.padding(innerPadding)
                )
            }
            composable("UserEdit") {
                BackHandler {
                    showDialogChanges = true
                }
                UserEdit(user, navController, Modifier.padding(innerPadding))
            }
            composable("BusinessEdit") {
                BackHandler {
                    showDialogChanges = true
                }
                BusinessEdit(user, navController, Modifier.padding(innerPadding))
            }
            composable("BuscarServicioProducto") { BuscarServicioProducto(Modifier.padding(innerPadding), navController, user) }
            composable("ServicioProducto") {  }
            composable("Mensajes") { Mensajes(navController, Modifier.padding(innerPadding), vm) }
            composable("Chat/{destinationImgRef}/{destinationName}", arguments = listOf(
                navArgument("destinationImgRef") { type = NavType.IntType},
                navArgument("destinationName") { type = NavType.StringType}
            )) { backStackEntry ->
                val img = backStackEntry.arguments?.getInt("destinationImgRef")
                val nameDestination = backStackEntry.arguments?.getString("destinationName")

                topBarTitle = nameDestination ?: ""
                topBarImg = img!!

                Chat(user, img, nameDestination?:"", navController, Modifier.padding(innerPadding))
            }
            composable(
                route = "ServicioProducto/{ServoProdut}/{Titulo}/{Photo}/{Descripcion}/{Envio}/{Precio}/{Unidad}",
                arguments = listOf(
                    navArgument("ServoProdut") { type = NavType.StringType },
                    navArgument("Titulo") { type = NavType.IntType },
                    navArgument("Photo") { type = NavType.IntType },
                    navArgument("Descripcion") { type = NavType.IntType },
                    navArgument("Envio") { type = NavType.StringType },
                    navArgument("Precio") { type = NavType.FloatType },
                    navArgument("Unidad") { type = NavType.StringType },
                )
            ) { backStackEntry ->
                val servoProdut = if (backStackEntry.arguments?.getString("ServoProdut")!! == "SERVICIO") {
                    ServOProd.SERVICIO
                } else {
                    ServOProd.PRODUCTO
                }
                val titulo = backStackEntry.arguments?.getInt("Titulo")!!
                val photo = backStackEntry.arguments?.getInt("Photo")!!
                val descripcion = backStackEntry.arguments?.getInt("Descripcion")!!
                val envio: Envio = if (backStackEntry.arguments?.getString("Envio") == "NOENVIO") {
                    Envio.NOENVIO
                } else if (backStackEntry.arguments?.getString("Envio") == "POSSIBILIDADENVIO") {
                    Envio.POSSIBILIDADENVIO
                } else {
                    Envio.OBLIGACIONENVIO
                }
                val precio = (backStackEntry.arguments?.getFloat("Precio")!!)
                val unidad = if (backStackEntry.arguments?.getString("Unidad")!! == "h") {
                    Unidad.HORAS
                } else if (backStackEntry.arguments?.getString("Unidad")!! == "Kg") {
                    Unidad.KILOGRAMOS
                } else {
                    Unidad.UNIDADES
                }
                val servoprod = ServicioOProductos(
                    Servoprod = servoProdut,
                    Titulo = titulo,
                    Photo = photo,
                    Descripcion = descripcion,
                    Envio = envio,
                    Precio = precio.toDouble(),
                    Unidad = unidad
                )
                SevicioProducto(servoprod, Modifier.padding(innerPadding), navController)
            }
            composable ("CrearServicioProducto") { CrearServicioProducto(Modifier.padding(innerPadding), navController) }
        }
        if (showDialogChanges) {
            DialogDiscardChanges(
                onDismiss = { showDialogChanges = false },
                onConfirm = {
                    showDialogChanges = false
                    navController.popBackStack()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    currentRoute: String?,
    navController: NavHostController,
    image: Int,
    title: String
) {
    when (currentRoute) {
        "Chat/{destinationImgRef}/{destinationName}" -> {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(apptopBarGradient),
                title = {
                    Row (verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center){
                        Image(
                            painter = painterResource(image),
                            contentDescription = title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(Modifier.padding(8.dp))
                        if (title.length > 20) {
                            Text(text = "${title.take(15)}...")
                        } else {
                            Text(text = title)
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    Image(
                        Icons.Filled.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable{
                                navController.popBackStack()
                            }
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.MoreHoriz,
                            contentDescription = "More options",
                            tint = Black
                        )
                    }
                },
            )
        }
        "ServicioProducto/{ServoProdut}/{Titulo}/{Photo}/{Descripcion}/{Envio}/{Precio}/{Unidad}" -> {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(apptopBarGradient),
                title = { Text(text = "") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    Image(
                        Icons.Filled.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable{
                                navController.popBackStack()
                            }
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Localized description",
                            tint = Black
                        )
                    }
                },
            )
        }
        "CrearServicioProducto" -> {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(apptopBarGradient),
                title = { Text(text = "") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    Image(
                        Icons.Filled.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clickable{
                                navController.popBackStack()
                            }
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Localized description",
                            tint = Black
                        )
                    }
                },
            )
        }
        else -> {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(apptopBarGradient),
                title = { Text(text = "") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.welan_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(40.dp)
                            .offset(x = 20.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Localized description",
                            tint = Black
                        )
                    }
                },
            )
        }
    }
}

@Composable
fun MyNaviagationBar (navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val selectedIcons: List<ImageVector> = listOf(Icons.Filled.Home, Icons.Filled.People, ImageVector.vectorResource(R.drawable.producto_servicio), Icons.AutoMirrored.Filled.Chat, Icons.Filled.AccountCircle)
    val items = listOf("MenuPrincipal", "Foros", "BuscarServicioProducto", "Mensajes", "Perfil")

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    LaunchedEffect(currentDestination) {
        currentDestination?.route?.let { route ->
            val index = items.indexOf(route)
            if (index != -1) selectedItem = index
        }
    }

    NavigationBar(
        modifier = Modifier.background(appbootomBarGradient),
        containerColor = Color.Transparent,
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(indicatorColor = secondaryColor),
                icon = {
                    Icon(
                        imageVector = selectedIcons[index],
                        contentDescription = item,
                        tint = Black
                    )
                },
                selected = selectedItem == index,
                onClick = {
                    if (selectedItem != index) {
                        selectedItem = index
                        navController.navigate(item) {
                            if (item == "MenuPrincipal") {
                                popUpTo(0)
                                launchSingleTop = true
                            }
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun DialogDiscardChanges(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
){
    val openAlertDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = context as Activity
    AlertDialog(
        text = {
            Text(text = "¿Quieres descartar los cambios?")
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = onConfirm
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancelar")
            }
        }
    )
}