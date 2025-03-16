package com.example.auth_impl.data.repositories.prefs

import android.content.Context

internal interface TokenManager {

    fun saveToken(context: Context, token: String)

    fun getToken(context: Context): String?

    fun clearToken(context: Context)
}