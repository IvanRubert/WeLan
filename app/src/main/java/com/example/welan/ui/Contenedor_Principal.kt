package com.example.welan.ui

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.material3.TextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.welan.R
import com.example.welan.model.BusinessUserWeLan
import com.example.welan.model.CommonUserWeLan
import com.example.welan.model.UserWelan
import com.example.welan.ui.componentes.base64ToImageBitmap
import com.example.welan.ui.screens.BusinessEdit
import com.example.welan.ui.screens.Chat
import com.example.welan.ui.screens.CrearServicioProducto
import com.example.welan.ui.screens.CreateForo
import com.example.welan.ui.screens.ErrorScreen
import com.example.welan.ui.screens.ForoChat
import com.example.welan.ui.screens.LoadingScreen
import com.example.welan.ui.screens.MensajesScreen
import com.example.welan.ui.screens.MenuPrincipalScreen
import com.example.welan.ui.screens.PerfilEmpresa
import com.example.welan.ui.screens.PerfilUser
import com.example.welan.ui.screens.SearchForum_Screen
import com.example.welan.ui.screens.SevicioProducto
import com.example.welan.ui.screens.SevicioProducto_Screen
import com.example.welan.ui.screens.UserEdit
import com.example.welan.ui.theme.appbootomBarGradient
import com.example.welan.ui.theme.apptopBarGradient
import com.example.welan.ui.theme.secondaryColor
import com.example.welan.ui.viewmodels.BusinessUserUiState
import com.example.welan.ui.viewmodels.ChatViewModel
import com.example.welan.ui.viewmodels.CommonUserUiState
import com.example.welan.ui.viewmodels.ForoChatViewModel
import com.example.welan.ui.viewmodels.ForoViewModel
import com.example.welan.ui.viewmodels.MessagesViewModel
import com.example.welan.ui.viewmodels.NoticiasViewModel
import com.example.welan.ui.viewmodels.SerOProdViewModel
import com.example.welan.ui.viewmodels.UserViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Contenedor_Screen(
    navControllerPrincipal: NavHostController,
    userViewModel: UserViewModel,
    retryAction: () -> Unit,
    type: String
) {
    if (type == "User") {
        when (userViewModel.commonUserUiState) {
            is CommonUserUiState.Success -> Contenedor_Principal(
                navControllerPrincipal,
                userViewModel,
                userWelan = (userViewModel.commonUserUiState as CommonUserUiState.Success).data,
            )
            is CommonUserUiState.Error -> ErrorScreen(retryAction)
            is CommonUserUiState.Loading -> LoadingScreen()
        }
    } else {
        when (userViewModel.businessUserUiState) {
            is BusinessUserUiState.Success -> Contenedor_Principal(
                navControllerPrincipal,
                userViewModel,
                userWelan = (userViewModel.businessUserUiState as BusinessUserUiState.Success).data,
            )
            is BusinessUserUiState.Error -> ErrorScreen(retryAction)
            is BusinessUserUiState.Loading -> LoadingScreen()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Contenedor_Principal(navControllerPrincipal: NavHostController, userViewModel: UserViewModel, userWelan: UserWelan) {

    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val noticiaViewModel : NoticiasViewModel = viewModel(factory = NoticiasViewModel.Factory)
    val mensajesViewModel : MessagesViewModel = viewModel(factory = MessagesViewModel.provideFactory(userWelan.correo))
    val foroViewModel : ForoViewModel = viewModel(factory = ForoViewModel.Factory)
    var topBarTitle by remember { mutableStateOf("") }
    var topBarImg by remember { mutableStateOf<String?>(null) }
    val serOProdViewModel: SerOProdViewModel = viewModel(factory = SerOProdViewModel.Factory)
    val foroChatVm: ForoChatViewModel = viewModel()
    val chatVm: ChatViewModel = viewModel(factory = ChatViewModel.provideFactory())

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = { MyTopAppBar(currentRoute, navController, topBarImg , topBarTitle) },
        bottomBar = { MyNaviagationBar(navController, currentRoute, userWelan.nombre, chatVm, foroChatVm) }
    ) { innerPadding ->
        var showDialogChanges by remember { mutableStateOf(false) }

        NavHost(navController = navController, startDestination = "MenuPrincipal") {
            composable("MenuPrincipal") {
                MenuPrincipalScreen(noticiaViewModel,retryAction = { noticiaViewModel.getNoticias() },Modifier.padding(innerPadding))
            }
            composable("Perfil") {
                when (userWelan) {
                    is CommonUserWeLan -> {
                        PerfilUser(
                            userWelan,
                            navController,
                            Modifier.padding(innerPadding),
                            navControllerPrincipal
                        )
                    }
                    is BusinessUserWeLan -> {
                        PerfilEmpresa(
                            userWelan,
                            navController,
                            Modifier.padding(innerPadding),
                            navControllerPrincipal
                        )

                    }
                }
            }
            composable("UserWeLanEdit") {
                BackHandler {
                    showDialogChanges = true
                }
                when (userWelan) {
                    is CommonUserWeLan -> {
                        UserEdit(userViewModel, userWelan, navController, Modifier.padding(innerPadding))
                    }
                    is BusinessUserWeLan -> {
                        BusinessEdit(userViewModel, userWelan, navController, Modifier.padding(innerPadding))
                    }
                }
            }
            composable("BuscarServicioProducto") {
                SevicioProducto_Screen(
                    navController,
                    serOProdViewModel,
                    retryAction = { serOProdViewModel.getSerOProd() },
                    Modifier.padding(innerPadding),
                    userWelan
                )
            }
            composable("CrearForo") {
                CreateForo(
                    Modifier.padding(innerPadding),
                    navController,
                    foroViewModel,
                    userWelan,
                    mensajesViewModel
                )
            }
            composable("Mensajes") {
                MensajesScreen(
                    mensajesViewModel,
                    retryAction = { mensajesViewModel.getMensajes(userWelan.correo)},
                    navController = navController,
                    modifier =  Modifier . padding (innerPadding)
                )
            }
            composable("Foros") {
                BackHandler {
                    showDialogChanges = true
                }
                SearchForum_Screen(
                    foroViewModel,
                    navController,
                    modifier = Modifier.padding(innerPadding),
                    retryAction = { foroViewModel.getForoFiltro("") }
                )
            }
            composable(
                "ForoChat/{title}", arguments = listOf(
                navArgument("title") { type = NavType.StringType }
            )) { backStackEntry ->
                val title = backStackEntry.arguments?.getString("title")
                topBarTitle = title ?: ""

                ForoChat(
                    userWelan, Modifier.padding(innerPadding),
                    foroChatVm
                )
            }
            composable("Chat") {
                val selected = mensajesViewModel.mensajeSelected
                topBarTitle = selected.nombre
                topBarImg = selected.img_conversation
                chatVm.correo1 = userWelan.correo
                chatVm.correo2 = selected.correo
                LaunchedEffect(selected.correo) { chatVm.getChat() }
                Chat(
                    userWelan,
                    selected.img_conversation,
                    selected.nombre,
                    Modifier.padding(innerPadding),
                    chatVm
                )
            }
            composable(
                route = "ServicioProducto"
            ) {
                SevicioProducto(
                    serOProdViewModel,
                    Modifier.padding(innerPadding),
                    navController,
                )
            }
            composable("CrearServicioProducto") {
                CrearServicioProducto(
                    Modifier.padding(
                        innerPadding
                    ),
                    navController,
                    userWelan,
                    serOProdViewModel
                )
            }
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
    image: String?,
    title: String
) {
    when (currentRoute) {
        "ForoChat/{title}" -> {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(apptopBarGradient),
                title = {
                    if (title.length > 30) {
                        Text(text = "${title.take(25)}...")
                    } else {
                        Text(text = title)
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
                            .size(24.dp)
                            .clickable {
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

        "Chat" -> {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(apptopBarGradient),
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (image != null) {
                            Image(
                                bitmap = base64ToImageBitmap(image)!!,
                                contentDescription = title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                        }else{
                            Image(
                                painter = painterResource(R.drawable.welan_logo),
                                contentDescription = title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                            )
                        }
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
                            .size(24.dp)
                            .clickable {
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
                            .size(24.dp)
                            .clickable {
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
                title = { Text(text = "Crear Servicio o Producto") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    Image(
                        Icons.Filled.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
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

        "CrearForo" -> {
            CenterAlignedTopAppBar(
                modifier = Modifier.background(apptopBarGradient),
                title = { Text(text = "Crear Foro") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    Image(
                        Icons.Filled.ArrowBackIosNew,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNaviagationBar(
    navController: NavHostController,
    currentRoute: String?,
    usuarionombre: String,
    chatVm: ChatViewModel,
    foroChatVm: ForoChatViewModel

    ) {
    when (currentRoute) {
        "ForoChat/{title}" -> {
            var textField by remember { mutableStateOf("") }

            BottomAppBar(
                modifier = Modifier.background(appbootomBarGradient),
                containerColor = Color.Transparent
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = textField,
                        onValueChange = { textField = it },
                        placeholder = { Text("Escribe un mensaje") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (textField.isNotBlank()) {
                                    foroChatVm.addMessage(textField, usuarionombre)
                                }
                                textField = ""
                            }
                        ),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    IconButton(
                        onClick = {
                            if (textField.isNotBlank()) {
                                foroChatVm.addMessage(textField, usuarionombre)
                            }
                            textField = ""
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send"
                        )
                    }
                }
            }
        }
        "Chat" -> {
            var textField by remember { mutableStateOf("") }

            BottomAppBar(
                modifier = Modifier.background(appbootomBarGradient),
                containerColor = Color.Transparent
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = textField,
                        onValueChange = { textField = it },
                        placeholder = { Text("Escribe un mensaje") },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Send
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (textField.isNotBlank()) {
                                    chatVm.postChat(textField)
                                }
                                textField = ""
                            }
                        ),
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    IconButton(
                        onClick = {
                            if (textField.isNotBlank()) {
                                chatVm.postChat(textField)
                            }
                            textField = ""
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send"
                        )
                    }
                }
            }
        }

        else -> {
            var selectedItem by remember { mutableIntStateOf(0) }
            val selectedIcons: List<ImageVector> = listOf(
                Icons.Filled.Home,
                Icons.Filled.People,
                ImageVector.vectorResource(R.drawable.producto_servicio),
                Icons.AutoMirrored.Filled.Chat,
                Icons.Filled.AccountCircle
            )
            val items =
                listOf("MenuPrincipal", "Foros", "BuscarServicioProducto", "Mensajes", "Perfil")

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
    }
}

@Composable
fun DialogDiscardChanges(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
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