package com.example.tbcacademy.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.presentation.ui.screens.LoginScreen
import com.example.tbcacademy.presentation.ui.screens.MainScreen
import com.example.tbcacademy.presentation.ui.screens.RegistrationScreen

@Composable
fun AppNavGraph(navController: NavHostController
){
    NavHost(navController = navController, startDestination = Screen.Login.route){
        composable(Screen.Login.route) {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                navigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegistrationScreen(
                navigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            MainScreen(
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}