package com.example.tbcacademy.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.effect.ProfileEffect
import com.example.tbcacademy.presentation.event.ProfileEvent
import com.example.tbcacademy.presentation.state.ProfileState
import com.example.tbcacademy.presentation.ui.components.StyledButton
import com.example.tbcacademy.presentation.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    navigateToLogin: () -> Unit
){

    val viewModel: ProfileViewModel = hiltViewModel()
    val lifecycle = LocalLifecycleOwner.current
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.obtainEvent(ProfileEvent.FetchUserEmail)
    }

    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effects.collect{ effect ->
                when(effect) {
                    ProfileEffect.NavigateToLogin -> navigateToLogin()
                }
            }
        }
    }

    val email = when(val currentState = state){
        is ProfileState.Success -> currentState.email
        is ProfileState.Error -> currentState.message
        else -> "Loading..."
    }

    Column(modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = stringResource(R.string.profile),
            style = TextStyle(
                color = Color.Black,
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold))
            )
        )
        Text(text = email)
        StyledButton(text = stringResource(R.string.log_out),
            modifier = Modifier.padding(top = 20.dp),
            onClick = { viewModel.obtainEvent(ProfileEvent.Logout) })
    }
}

@Composable
@Preview
fun ProfileScreenPreview(){
    ProfileScreen(navigateToLogin = {})
}