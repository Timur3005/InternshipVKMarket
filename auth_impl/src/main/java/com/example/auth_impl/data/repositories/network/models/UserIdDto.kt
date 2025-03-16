package com.example.auth_impl.data.repositories.network.models

import com.google.gson.annotations.SerializedName

internal data class UserIdDto(
    @SerializedName("id") val id: Int,
)