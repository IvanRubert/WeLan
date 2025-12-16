package com.example.welan

import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.welan.data.Envio
import com.example.welan.data.ServOProd
import com.example.welan.data.ServicioOProductos
import com.example.welan.data.Unidad
import com.example.welan.data.User
import com.example.welan.ui.theme.WeLanTheme
import java.io.IOException

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            WeLanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "SplashScreen") {
                        composable("SplashScreen") {
                            MySplashScreen(navController, Modifier.padding(innerPadding))
                        }
                        composable("Login"){
                            Login(navController, Modifier.padding(innerPadding))
                        }
                        composable("Register") {
                            CrearCuentaScreen(navController)
                        }
                        composable(
                            route = "Contenedor_Principal/{Photo}/{Type}/{Name}/{Email}/{Descripcion}/{Intereses}",
                            arguments = listOf(
                                navArgument("Photo") { type = NavType.IntType },
                                navArgument("Type") { type = NavType.StringType },
                                navArgument("Name") { type = NavType.StringType },
                                navArgument("Email") { type = NavType.StringType },
                                navArgument("Descripcion") { type = NavType.StringType },
                                navArgument("Intereses") { type = NavType.StringType },
                            )
                        ) { backStackEntry ->
                            val photo = backStackEntry.arguments?.getInt("Photo")!!
                            val type = Uri.decode(backStackEntry.arguments?.getString("Type")!!)
                            val name = Uri.decode(backStackEntry.arguments?.getString("Name")!!)
                            val email = Uri.decode(backStackEntry.arguments?.getString("Email")!!)
                            val descripcionRaw = Uri.decode(backStackEntry.arguments?.getString("Descripcion") ?: "")
                            val descripcion = when (descripcionRaw) {
                                "_NULL_" -> null
                                "_EMPTY_" -> ""
                                else -> descripcionRaw
                            }

                            val interesesString = backStackEntry.arguments?.getString("Intereses") ?: ""
                            val intereses = if (interesesString.isNotEmpty()) {
                                Uri.decode(interesesString).split("→←")
                            } else {
                                emptyList()
                            }

                            val user = User(
                                imageRes = photo,
                                type = type,
                                name = name,
                                email = email,
                                descripcion = descripcion,
                                intereses = intereses
                            )
                            Contenedor_Principal(navController, user)
                        }
                    }
                }
            }
        }
    }
}

