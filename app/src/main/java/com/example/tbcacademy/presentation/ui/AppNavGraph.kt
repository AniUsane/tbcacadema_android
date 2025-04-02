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

@Composable
fun AppNavGraph(navController: NavHostController,
                loginState: LoginState,
                loginEvent: (LoginEvent) -> Unit,
                registrationState: RegisterState,
                registrationEvent: (RegisterEvent) -> Unit,
                loginEffectFlow: Flow<LoginEffect>
){
    NavHost(navController = navController, startDestination = LoginDestination){
        composable<LoginDestination> {
            val effect = loginEffectFlow.collectAsState(initial = null)
            LaunchedEffect(effect.value) {
                if (effect.value is LoginEffect.NavigateToProfile) {
                    navController.navigate(ProfileDestination)
                }
            }


            LoginScreen(
                state = loginState,
                onEvent = loginEvent,
                navigateToRegister = {
                    navController.navigate(RegistrationDestination)
                }
            )
        }
        composable<RegistrationDestination>{
            RegistrationScreen(
                state = registrationState,
                onEvent = registrationEvent,
                navigateToLogin = {
                    navController.navigate(LoginDestination)
                }
            )
        }
        composable<ProfileDestination> {
            AndroidFragment(clazz = ProfileFragment::class.java)
        }
    }

}