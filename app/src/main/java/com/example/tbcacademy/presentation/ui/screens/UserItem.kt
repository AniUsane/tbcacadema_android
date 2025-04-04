package com.example.tbcacademy.presentation.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tbcacademy.data.remote.User

@Composable
fun UserItem(user: User){
    Row(
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = user.avatar,
            contentDescription = null,
            modifier = Modifier.size(64.dp)
                .clip(CircleShape)

        )
        Spacer(modifier = Modifier.width(12.dp))
        Text("${user.firstName} ${user.lastName}")
    }
}