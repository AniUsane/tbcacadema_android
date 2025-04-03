package com.example.tbcacademy.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
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
import com.example.tbcacademy.presentation.effect.LoginEffect
import com.example.tbcacademy.presentation.event.LoginEvent
import com.example.tbcacademy.presentation.state.LoginState
import com.example.tbcacademy.presentation.ui.components.StyledButton
import com.example.tbcacademy.presentation.ui.components.StyledOutlinedTextField
import com.example.tbcacademy.presentation.ui.themes.Black
import com.example.tbcacademy.presentation.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    navigateToHome: () -> Unit,
    navigateToRegister: () -> Unit
){

    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current
    val snackBarHostState = remember {SnackbarHostState()}

    LaunchedEffect(Unit) {
        viewModel.checkRememberMe()
    }

    LaunchedEffect(lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.effects.collect{ effect ->
                when(effect){
                    is LoginEffect.NavigateToProfile -> navigateToHome()
                    is LoginEffect.NavigateToRegister -> navigateToRegister()
                    is LoginEffect.ShowSnackBar -> {
                        snackBarHostState.showSnackbar(effect.message)
                    }
                }
            }
        }
    }

    if(state is LoginState.Loading){
        CircularProgressIndicator(modifier = Modifier.padding(top = 24.dp))
    }
    when (val currentState = state) {
        is LoginState.Input -> {
        }

        is LoginState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
        }

        is LoginState.Error -> {
            LaunchedEffect(currentState.message) {
                snackBarHostState.showSnackbar(currentState.message)
                viewModel.resetToInputState()
            }
        }

        is LoginState.Success -> {
        }
    }

    val inputState = state as? LoginState.Input ?: LoginState.Input()

    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
        ){
            Image(
                painter = painterResource(R.drawable.top_round_design),
                contentDescription = "top design image"
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = stringResource(R.string.login_page_title),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 48.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_bold))
                )
            )
            Image(
                painter = painterResource(R.drawable.standing),
                contentDescription = "Login page image",
                Modifier.padding(20.dp)
            )

            StyledOutlinedTextField(
                value = inputState.email,
                onValueChange = { viewModel.obtainEvent(LoginEvent.EmailChanged(it))},
                label = "Email",
            )

            StyledOutlinedTextField(
                value = inputState.password,
                onValueChange = { viewModel.obtainEvent(LoginEvent.PasswordChanged(it))},
                label = "Password",
                modifier = Modifier.padding(top = 20.dp)
            )
            Row(modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 30.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically){
                Checkbox(
                    checked = inputState.isRememberMeChecked,
                    onCheckedChange = { viewModel.obtainEvent(LoginEvent.RememberMeChanged(it)) }
                )
                Text(text = "Remember me",
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }
            StyledButton(
                text = stringResource(R.string.btn2_text),
                modifier = Modifier.padding(top = 20.dp),
                onClick = {
                    viewModel.obtainEvent(LoginEvent.SubmitLogin(inputState.email, inputState.password, inputState.isRememberMeChecked))
                }
            )
            Text(
                text = stringResource(R.string.don_t_have_an_account_register_now),
                style = TextStyle(
                    color = Black,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(top = 15.dp)
                    .clickable { navigateToRegister() }
            )
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
fun FirstComposableScreenPreview(){
    LoginScreen(navigateToHome = {},
        navigateToRegister = {})
}