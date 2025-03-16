package com.example.tbcacademy.presentation.event

sealed class ProfileEvent {
    data object FetchUserEmail: ProfileEvent()
    data object Logout: ProfileEvent()
}