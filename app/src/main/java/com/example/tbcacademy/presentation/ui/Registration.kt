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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.event.RegisterEvent
import com.example.tbcacademy.presentation.state.RegisterState
import com.example.tbcacademy.presentation.ui.components.StyledButton
import com.example.tbcacademy.presentation.ui.components.StyledOutlinedTextField
import com.example.tbcacademy.presentation.ui.themes.Black

@Composable
fun RegistrationScreen(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    navigateToLogin: () -> Unit
){
    val inputState = state as? RegisterState.Input ?: return

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
                onValueChange = {onEvent(RegisterEvent.EmailChanged(it)) },
                label = "Email",
            )
            StyledOutlinedTextField(
                value = inputState.username,
                onValueChange = {onEvent(RegisterEvent.UsernameChanged(it)) },
                label = "Username",
            )
            StyledOutlinedTextField(
                value = inputState.password,
                onValueChange = {onEvent(RegisterEvent.PasswordChanged(it)) },
                label = "Password",
            )
            StyledOutlinedTextField(
                value = inputState.repeatedPassword,
                onValueChange = {onEvent(RegisterEvent.RepeatedPasswordChanged(it)) },
                label = "Repeat password",
            )
            StyledButton(
                text = stringResource(R.string.btn1_text),
                modifier = Modifier.padding(top = 20.dp),
                onClick = navigateToLogin
            )
            Text(stringResource(R.string.already_have_an_account_log_in),
                style = TextStyle(
                    color = Black,
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(top = 15.dp)
                    .clickable { navigateToLogin() })
        }

    }
}

@Composable
@Preview
fun RegistrationScreenPreview(){
    RegistrationScreen(state = RegisterState.Input(),
        onEvent = {},
        navigateToLogin = {})
}