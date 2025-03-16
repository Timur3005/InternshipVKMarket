package com.example.auth_impl.data.repositories.network.models

import com.google.gson.annotations.SerializedName

internal data class UserDto(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
)