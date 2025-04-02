package com.example.tbcacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.presentation.ui.AppNavGraph
import com.example.tbcacademy.presentation.viewmodel.LoginViewModel
import com.example.tbcacademy.presentation.viewmodel.RegistrationViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val loginViewModel: LoginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
            val loginState = loginViewModel.viewState.collectAsState()
            val registrationViewModel = ViewModelProvider(this)[RegistrationViewModel::class.java]
            val registerState = registrationViewModel.viewState.collectAsState()


            AppNavGraph(navController = navController,
                loginState = loginState.value,
                loginEvent = loginViewModel::obtainEvent,
                registrationEvent = registrationViewModel::obtainEvent,
                registrationState = registerState.value,
                loginEffectFlow = loginViewModel.effects)
        }

    }
}