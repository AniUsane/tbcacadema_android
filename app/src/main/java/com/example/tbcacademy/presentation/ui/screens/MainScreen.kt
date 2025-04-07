package com.example.tbcacademy.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tbcacademy.presentation.ui.navigation.BottomNavigationBar
import com.example.tbcacademy.presentation.ui.navigation.Screen


@Composable
fun MainScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Home.route) {
                HomeScreen(
                    navigateToProfile = {
                        selectedIndex.intValue = 1
                        navController.navigate(Screen.Profile.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedIndex = selectedIndex
                )
            }

            composable(route = Screen.Profile.route) {
                ProfileScreen(
                    navigateToLogin = {
                        onLogout()
                    }
                )
            }
        }
    }
}