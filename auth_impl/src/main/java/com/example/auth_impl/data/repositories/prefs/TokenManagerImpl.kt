package com.example.auth_impl.data.repositories.prefs

import android.content.Context
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.auth_api.domain.entities.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import java.util.Base64
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class TokenManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : TokenManager {

    companion object {
        private const val PREF_NAME = "secure_prefs"
        private const val TOKEN_KEY = "jwt_token"
    }

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPreferences = EncryptedSharedPreferences.create(
        PREF_NAME,
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    override fun saveToken(token: String) {
        sharedPreferences.edit { putString(TOKEN_KEY, token) }
    }

    override fun getToken(): String? {
        return sharedPreferences.getString(TOKEN_KEY, null)
    }

    override fun clearToken() {
        sharedPreferences.edit { remove(TOKEN_KEY) }
    }

    override fun parseToken(): Pair<String, String> {
        return parseJwt(getToken().toString())
    }

    private fun parseJwt(jwt: String): Pair<String, String> {
        val parts = jwt.split(".")
        if (parts.size != 3) return Pair("", "")

        val payloadJson = String(Base64.getUrlDecoder().decode(parts[1]))
        val payload = JSONObject(payloadJson)

        val name = payload.optString("name", "")
        val email = payload.optString("email", "")

        return Pair(name, email)
    }
}
