package com.example.tbcacademy.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tbcacademy.presentation.ui.themes.DarkPurple
import com.example.tbcacademy.presentation.ui.themes.LightPurple

@Composable
fun StyledOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(30.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = LightPurple,
            focusedBorderColor = DarkPurple,
            unfocusedBorderColor = LightPurple,
            textColor = Color.Black
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
}