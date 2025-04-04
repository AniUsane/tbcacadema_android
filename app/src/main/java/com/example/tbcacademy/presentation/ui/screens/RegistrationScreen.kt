package com.example.tbcacademy.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
import com.example.tbcacademy.presentation.effect.RegisterEffect
import com.example.tbcacademy.presentation.event.RegisterEvent
import com.example.tbcacademy.presentation.state.RegisterState
import com.example.tbcacademy.presentation.ui.components.StyledButton
import com.example.tbcacademy.presentation.ui.components.StyledOutlinedTextField
import com.example.tbcacademy.presentation.ui.themes.Black
import com.example.tbcacademy.presentation.viewmodel.RegistrationViewModel

@Composable
fun RegistrationScreen(
    navigateToLogin: () -> Unit
){
    val viewModel: RegistrationViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val snackBarHostState = remember {SnackbarHostState()}
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.effects.collect{ effect ->
                when(effect) {
                    is RegisterEffect.NavigateToLogin -> navigateToLogin()
                    is RegisterEffect.NavigateToLoginPage -> navigateToLogin()
                    is RegisterEffect.ShowSnackBar -> {
                        snackBarHostState.showSnackbar(effect.message)
                    }
                }
            }
        }
    }

    if (state is RegisterState.Loading) {
        CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
    }

    when (val currentState = state) {
        is RegisterState.Input -> {

        }

        is RegisterState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
        }

        is RegisterState.Error -> {
            LaunchedEffect(currentState.message) {
                snackBarHostState.showSnackbar(currentState.message)
                viewModel.resetToInputState()
            }
        }

        is RegisterState.Success -> {
            // No UI — navigation is already handled via effect
        }
    }

    val inputState = state as? RegisterState.Input ?: RegisterState.Input()


    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        verticalArrangement = Arrangement.Top
    ){
        Row {
            Image(painter = painterResource(R.drawable.top_round_design),
                contentDescription = "top design image")
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.register_page_title),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 48.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
            )
            Image(
                painter = painterResource(R.drawable.register_image),
                contentDescription = "Login page image",
                Modifier.padding(20.dp)
            )
            StyledOutlinedTextField(
                value = inputState.email,
                onValueChange = {viewModel.obtainEvent(RegisterEvent.EmailChanged(it)) },
                label = "Email",
            )
            StyledOutlinedTextField(
                value = inputState.username,
                onValueChange = {viewModel.obtainEvent(RegisterEvent.UsernameChanged(it)) },
                label = "Username",
            )
            StyledOutlinedTextField(
                value = inputState.password,
                onValueChange = {viewModel.obtainEvent(RegisterEvent.PasswordChanged(it)) },
                label = "Password",
            )
            StyledOutlinedTextField(
                value = inputState.repeatedPassword,
                onValueChange = {viewModel.obtainEvent(RegisterEvent.RepeatedPasswordChanged(it)) },
                label = "Repeat password",
            )
            StyledButton(
                text = stringResource(R.string.btn1_text),
                modifier = Modifier.padding(top = 20.dp),
                onClick = {
                    viewModel.obtainEvent(
                        RegisterEvent.SubmitRegistration(
                            email = inputState.email,
                            password = inputState.password,
                            repeatedPassword = inputState.repeatedPassword
                        )
                    )
                }
            )
            Text(stringResource(R.string.already_have_an_account_log_in),
                style = TextStyle(
                    color = Black,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(top = 15.dp)
                    .clickable { navigateToLogin() })
        }

        SnackbarHost(
            hostState = snackBarHostState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

    }
}

@Composable
@Preview
fun RegistrationScreenPreview(){
    RegistrationScreen(navigateToLogin = {})
}