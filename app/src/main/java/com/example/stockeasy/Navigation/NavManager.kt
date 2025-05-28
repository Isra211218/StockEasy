package com.example.stockeasy.Navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.stockeasy.screens.*

@Composable
fun NavManager(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "inicio_sesion"
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
            MenuPrincipalPantalla(
                onNavigateToListas = { navController.navigate("menu_listas") },
                onNavigateToHistorialVentas = { navController.navigate("historial_ventas") },
                onNavigateToEditarPerfil = { navController.navigate("editar_perfil") },
                onLogout = {
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
                    navController.navigate("lista_seleccionada/${lista.id}/$nombreCodificado")
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
                onGuardarLista = {
                    navController.navigate("menu_listas") {
                        popUpTo("menu_listas") { inclusive = true }
                    }
                    true
                }
            )
        }

        composable("lista_seleccionada/{listaId}/{nombreLista}") { backStackEntry ->
            val listaId = backStackEntry.arguments?.getString("listaId")?.toIntOrNull() ?: 0
            val nombreLista = backStackEntry.arguments?.getString("nombreLista") ?: ""
            ListaSeleccionadaPantalla(
                listaId = listaId,
                nombreLista = nombreLista,
                descripcionLista = "Descripción de $nombreLista",
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
