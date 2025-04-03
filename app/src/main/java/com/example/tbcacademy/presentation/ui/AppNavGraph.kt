package com.example.tbcacademy.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.fragment.compose.AndroidFragment
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tbcacademy.presentation.effect.LoginEffect
import com.example.tbcacademy.presentation.event.LoginEvent
import com.example.tbcacademy.presentation.event.RegisterEvent
import com.example.tbcacademy.presentation.state.LoginState
import com.example.tbcacademy.presentation.state.RegisterState
import kotlinx.coroutines.flow.Flow
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