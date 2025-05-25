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
                nombreUsuario = "",
                correoUsuario = "",
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
                listas = listOf(
                    Lista("Inventario General"),
                    Lista("Cocina"),
                    Lista("Oficina")
                ),
                onSeleccionarLista = { lista ->
                    val nombreCodificado = Uri.encode(lista.nombre)
                    navController.navigate("lista_seleccionada/$nombreCodificado")
                },
                onAgregarLista = {
                    navController.navigate("agregar_lista")
                },
                onVolverAlMenu = {
                    navController.popBackStack()
                }
            )
        }

        composable("lista_seleccionada/{nombreLista}") { backStackEntry ->
            val nombreLista = backStackEntry.arguments?.getString("nombreLista") ?: ""
            ListaSeleccionadaPantalla(
                nombreLista = nombreLista,
                descripcionLista = "Descripción de $nombreLista",
                productos = listOf(),
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                },
                onAgregarProducto = {
                    navController.navigate("nuevo_producto")
                },
                onEditarProducto = {
                    navController.navigate("editar_producto")
                }
            )
        }

        composable("editar_producto") {
            EditarProductoPantalla(
                productoInicial = "",
                cantidadInicial = "",
                onGuardarCambios = { _, _ ->
                    navController.popBackStack()
                },
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                }
            )
        }

        composable("nuevo_producto") {
            NuevoProductoPantalla(
                onGuardarProducto = { _, _ ->
                    navController.popBackStack()
                },
                onVolver = { navController.popBackStack() },
                onIrAlInicio = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                }
            )
        }

        composable("agregar_venta") {
            AgregarVentaPantalla(
                onGuardarVenta = { _, _, _, _ ->
                    navController.popBackStack()
                },
                onVolverAHistorial = {
                    navController.popBackStack()
                },
                onVolverAlMenu = {
                    navController.navigate("menu_principal") {
                        popUpTo("menu_principal") { inclusive = false }
                    }
                }
            )
        }

        composable("agregar_lista") {
            AgregarListaPantalla(
                onGuardarLista = { _, _ -> navController.popBackStack() },
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
                ventas = listOf(),
                onAgregarVenta = {
                    navController.navigate("agregar_venta")
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
