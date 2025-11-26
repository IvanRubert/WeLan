package com.example.welan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.welan.data.User
import com.example.welan.ui.theme.WeLanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeLanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    val user = User(
                        imageRes = R.drawable.welan_logo,
                        name = "Pepe Gil",
                        email = "pepe123@example.com",
                        descripcion = "Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción Esto es una prueba de descripción",
                        intereses = listOf("Construcción", "Reformas", "Bricolaje")
                    )

                    NavHost(navController = navController, startDestination = "SplashScreen") {
                        composable("SplashScreen") {
                            MySplashScreen(
                                navController,
                                Modifier.padding(innerPadding)
                            )
                        }
                        composable("MenuPrincipal") {
                            MenuPrincipal(
                                navController,
                                Modifier.padding(innerPadding)
                            )
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
                        composable("Login"){Login(navController, Modifier.padding(innerPadding))}
                        composable("register") { CrearCuentaScreen(navController, Modifier.padding(innerPadding))}

                    }
                }
            }
        }
    }
}

