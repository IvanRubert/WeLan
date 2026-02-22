package com.example.welan

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.welan.ui.Contenedor_Principal
import com.example.welan.ui.Contenedor_Screen
import com.example.welan.ui.componentes.UserExist
import com.example.welan.ui.componentes.UserType
import com.example.welan.ui.componentes.UsuarioJson
import com.example.welan.ui.screens.CrearCuentaScreen
import com.example.welan.ui.screens.Login
import com.example.welan.ui.screens.MySplashScreen
import com.example.welan.ui.theme.WeLanTheme
import com.example.welan.ui.viewmodels.BusinessUserUiState
import com.example.welan.ui.viewmodels.CommonUserUiState
import com.example.welan.ui.viewmodels.UserViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            WeLanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    val userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory)

                    NavHost(navController = navController, startDestination = "SplashScreen") {
                        composable("SplashScreen") {
                            MySplashScreen(navController, Modifier.padding(innerPadding))
                        }
                        composable("Login"){
                            Login(navController, Modifier.padding(innerPadding), userViewModel)
                        }
                        composable("Register") {
                            CrearCuentaScreen(navController)
                        }
                        composable(
                            route = "Contenedor_Principal",
                        ) {
                            val context = LocalContext.current
                            if (UserExist(context)) {
                                val email = UsuarioJson(context)

                                if (UserType(context) == "User") {
                                    userViewModel.getCommonUser(email)
                                    Contenedor_Screen(
                                        navController,
                                        userViewModel,
                                        retryAction = { userViewModel.getCommonUser(email) },
                                    )
                                } else {
                                    userViewModel.getBusinessUser(email)
                                    Contenedor_Screen(
                                        navController,
                                        userViewModel,
                                        retryAction = { userViewModel.getBusinessUser(email) },
                                    )
                                }
                            } else {
                                Login(navController, Modifier.padding(innerPadding))
                            }
                        }
                    }
                }
            }
        }
    }
}