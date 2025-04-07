package com.example.tbcacademy.presentation.ui.navigation


sealed class Screen(val route: String){
    data object Home: Screen("home_screen")
    data object Profile: Screen("profile_screen")
    data object Login : Screen("login_screen")
    data object Register : Screen("register_screen")
    data object Main : Screen("main_screen")
}
