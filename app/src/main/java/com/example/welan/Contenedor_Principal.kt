package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.welan.data.User
import com.example.welan.ui.theme.appbootomBarGradient
import com.example.welan.ui.theme.apptopBarGradient
import com.example.welan.ui.theme.secondaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Contenedor_Principal() {

    val navController = rememberNavController()

    Scaffold (
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                modifier = Modifier.background(apptopBarGradient),
                title = { Text(text = "") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                ),
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.welan_logo),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp).offset(x = 20.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Localized description",
                            tint = Black
                        )
                    }
                },
            )
        },
        bottomBar = { MyNaviagationBar(navController) }
    ) { innerPadding ->

        val user = User(
            imageRes = R.drawable.welan_logo,
            name = "Pepe Gil",
            email = "pepe123@example.com",
            descripcion = "Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción",
            intereses = listOf("Construcción", "Reformas", "Bricolaje")
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
        }
    }
}

@Composable
fun MyNaviagationBar (navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val selectedIcons: List<ImageVector> = listOf(Icons.Filled.Home, Icons.Filled.People, ImageVector.vectorResource(R.drawable.producto_servicio), Icons.AutoMirrored.Filled.Chat, Icons.Filled.AccountCircle)
    val items = listOf("MenuPrincipal", "Foros", "ServiciosProductos", "Chat", "Perfil")
    NavigationBar (
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
                onClick = { selectedItem = index
                    navController.navigate(item)  },
            )
        }
    }
}