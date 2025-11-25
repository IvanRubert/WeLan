package com.example.welan

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.welan.ui.theme.appBarGradient

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Comtenedor_Principal() {
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(appBarGradient),
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
        }
    ) { innerpading ->
        MenuPrincipal(Modifier.padding(innerpading))
    }
}

@Composable
fun MyNaviagationBar (navController: NavHostController) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val selectedIcons = listOf(Icons.Filled.Home, Icons.Filled.Forum, Icons.)
    val items = listOf("MenuPrincipal", )
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = selectedIcons[index],
                        contentDescription = item,
                    )
                },
                selected = selectedItem == index,
                onClick = { selectedItem = index
                    navController.navigate(item)  },
            )
        }
    }
}