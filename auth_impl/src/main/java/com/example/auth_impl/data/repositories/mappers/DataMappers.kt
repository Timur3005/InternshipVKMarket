package com.example.auth_impl.data.repositories.mappers

import com.example.auth_api.domain.entities.UserEntity
import com.example.auth_impl.data.repositories.network.models.UserDto

internal fun UserDto.toUserEntity(): UserEntity =
    UserEntity(
        name = name,
        email = email,
        password = password,
    )

internal fun UserEntity.toUserDto(): UserDto =
    UserDto(
        name = name,
        email = email,
        password = password,
    )