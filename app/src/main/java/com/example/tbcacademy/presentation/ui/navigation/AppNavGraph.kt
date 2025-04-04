package com.example.tbcacademy.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.presentation.ui.screens.HomeScreen
import com.example.tbcacademy.presentation.ui.screens.LoginScreen
import com.example.tbcacademy.presentation.ui.screens.ProfileScreen
import com.example.tbcacademy.presentation.ui.screens.RegistrationScreen
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

@Serializable
data object RegistrationDestination

@Serializable
data object ProfileDestination

@Serializable
data object HomeDestination

@Composable
fun AppNavGraph(navController: NavHostController
){
    NavHost(navController = navController, startDestination = LoginDestination){
        composable<LoginDestination> {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(HomeDestination)
                },
                navigateToRegister = {
                    navController.navigate(RegistrationDestination)
                }
            )
        }
        composable<RegistrationDestination>{
            RegistrationScreen(
                navigateToLogin = {
                    navController.navigate(LoginDestination)
                }
            )
        }
        composable<ProfileDestination> {
            ProfileScreen(
                navigateToLogin = {
                    navController.navigate(LoginDestination)
            })
        }

        composable<HomeDestination> {
            HomeScreen(
                navigateToProfile = {
                    navController.navigate(ProfileDestination)
                }
            )
        }
    }
}