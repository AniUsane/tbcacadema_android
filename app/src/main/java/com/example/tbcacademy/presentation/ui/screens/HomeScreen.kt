package com.example.tbcacademy.presentation.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.tbcacademy.R
import com.example.tbcacademy.presentation.ui.UserItem
import com.example.tbcacademy.presentation.ui.themes.White
import com.example.tbcacademy.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navigateToProfile: () -> Unit,
    selectedIndex: MutableIntState
) {

    val viewModel: HomeViewModel = hiltViewModel()
    val users = viewModel.userList.collectAsLazyPagingItems()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(top = 40.dp),

        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.home),
            style = TextStyle(
                color = Color.Black,
                fontSize = 48.sp,
                fontFamily = FontFamily(Font(R.font.roboto_bold))
            )
        )

        Button(onClick = {
            selectedIndex.intValue = 1
            navigateToProfile()
        },
            modifier = Modifier
                .align(Alignment.End)
                .padding(20.dp)) {
            Text(text = stringResource(R.string.profile))
        }

        Text(
            text = "User list: ",
            style = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium))
            )
        )

        LazyColumn {
            items(users.itemCount) { index ->
                val user = users[index]
                user?.let {
                    UserItem(user = it)
                }
            }

            when {
                users.loadState.refresh is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }

                users.loadState.append is LoadState.Loading -> {
                    item {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    }
                }

                users.loadState.refresh is LoadState.Error -> {
                    item {
                        Text(text = "Failed to load items")
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    val selectedIndex = rememberSaveable { mutableIntStateOf(0) }
    HomeScreen(
        navigateToProfile = {},
        selectedIndex = selectedIndex
    )
}