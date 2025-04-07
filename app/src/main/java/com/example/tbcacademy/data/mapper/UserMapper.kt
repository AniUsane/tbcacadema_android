package com.example.tbcacademy.data.mapper

import com.example.tbcacademy.data.model.UserDto
import com.example.tbcacademy.domain.model.User

fun UserDto.toDomain() = User(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar
)