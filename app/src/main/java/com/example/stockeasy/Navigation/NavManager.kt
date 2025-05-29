package com.example.stockeasy.Navigation

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockeasy.screens.*

@Composable
fun NavManager(navController: NavHostController, isLoggedIn: Boolean) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) "menu_principal" else "inicio_sesion"
    ) {
        composable("inicio_sesion") {
            InicioSesionPantalla(
                onLoginSuccess = {
                    navController.navigate("menu_principal") {
                        popUpTo("inicio_sesion") { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate("registro")
                }
            )
        }

        composable("registro") {
            RegistroPantalla(
                onRegisterSuccess = {
                    navController.navigate("menu_principal") {
                        popUpTo("inicio_sesion") { inclusive = true }
                    }
                },
                onNavigateToLogin = { navController.popBackStack() }
            )
        }

        composable("menu_principal") {
            val context = LocalContext.current

            MenuPrincipalPantalla(
                onNavigateToListas = { navController.navigate("menu_listas") },
                onNavigateToHistorialVentas = { navController.navigate("historial_ventas") },
                onNavigateToEditarPerfil = { navController.navigate("editar_perfil") },
                onLogout = {
                    // Borrar sesión al cerrar sesión
                    val sharedPref = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putBoolean("is_logged_in", false)
                        apply()
                    }

                    // Volver a pantalla de inicio de sesión
                    navController.navigate("inicio_sesion") {
                        popUpTo("inicio_sesion") { inclusive = true }
                    }
                }
            )
        }


        composable("editar_perfil") {
            EditarPerfilPantalla(
                onGuardarCambios = { navController.popBackStack() },
                onVolverAlMenu = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                }
            )
        }

        composable("menu_listas") {
            MenuListasPantalla(
                onSeleccionarLista = { lista ->
                    val nombreCodificado = Uri.encode(lista.nombre)
                    val descripcionCodificada = Uri.encode(lista.descripcion)
                    navController.navigate("lista_seleccionada/${lista.id}/$nombreCodificado/$descripcionCodificada")
                },
                onAgregarLista = {
                    navController.navigate("agregar_lista")
                },
                onVolverAlMenu = {
                    navController.popBackStack()
                }
            )
        }

        composable("agregar_lista") {
            AgregarListaPantalla(
                onVolver = {
                    navController.popBackStack()
                },
                onIrAlInicio = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                },
                onGuardarLista = { listaId, nombre, descripcion ->
                    val nombreCodificado = Uri.encode(nombre)
                    val descripcionCodificada = Uri.encode(descripcion)
                    navController.navigate("lista_seleccionada/$listaId/$nombreCodificado/$descripcionCodificada")
                }
            )
        }

        composable("lista_seleccionada/{listaId}/{nombreLista}/{descripcionLista}") { backStackEntry ->
            val listaId = backStackEntry.arguments?.getString("listaId")?.toIntOrNull() ?: 0
            val nombreLista = backStackEntry.arguments?.getString("nombreLista") ?: ""
            val descripcionLista = backStackEntry.arguments?.getString("descripcionLista") ?: ""
            ListaSeleccionadaPantalla(
                listaId = listaId,
                nombreLista = nombreLista,
                descripcionLista = descripcionLista,
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                },
                onIrAlMenuListas = {
                    navController.navigate("menu_listas") {
                        popUpTo("menu_listas") { inclusive = true }
                    }
                },
                onAgregarProducto = {
                    navController.navigate("nuevo_producto/$listaId")
                },
                onEditarProducto = { productoId ->
                    navController.navigate("editar_producto/$productoId/$listaId")
                }

            )
        }

        composable("nuevo_producto/{listaId}") { backStackEntry ->
            val listaId = backStackEntry.arguments?.getString("listaId")?.toIntOrNull() ?: 0
            NuevoProductoPantalla(
                listaId = listaId,
                onGuardarProducto = { _, _ -> navController.popBackStack() },
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                }
            )
        }

        composable("editar_producto/{productoId}/{listaId}") { backStackEntry ->
            val productoId = backStackEntry.arguments?.getString("productoId")?.toIntOrNull() ?: 0
            val listaId = backStackEntry.arguments?.getString("listaId")?.toIntOrNull() ?: 0
            EditarProductoPantalla(
                productoId = productoId,
                productoInicial = "",
                cantidadInicial = "",
                listaId = listaId,
                onGuardarCambios = { _, _ -> navController.popBackStack() },
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                }
            )
        }

        composable("historial_ventas") {
            HistorialVentasPantalla(
                onAgregarVenta = {
                    navController.navigate("agregar_venta/0")
                },
                onVolverAlMenu = {
                    navController.popBackStack()
                },
                listaId = 0
            )
        }

        composable("historial_ventas/{listaId}") { backStackEntry ->
            val listaId = backStackEntry.arguments?.getString("listaId")?.toIntOrNull() ?: 0
            HistorialVentasPantalla(
                onAgregarVenta = {
                    navController.navigate("agregar_venta/$listaId")
                },
                onVolverAlMenu = {
                    navController.popBackStack()
                },
                listaId = listaId
            )
        }

        composable("agregar_venta/{listaId}") { backStackEntry ->
            val listaId = backStackEntry.arguments?.getString("listaId")?.toIntOrNull() ?: 0
            AgregarVentaPantalla(
                listaId = listaId,
                onGuardarVenta = { _, _, _, _, _ ->
                    navController.navigate("historial_ventas/$listaId") {
                        popUpTo("historial_ventas/$listaId") { inclusive = true }
                    }
                },
                onVolverAHistorial = {
                    navController.navigate("historial_ventas/$listaId") {
                        popUpTo("historial_ventas/$listaId") { inclusive = true }
                    }
                },
                onVolverAlMenu = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                }
            )
        }
    }
}
